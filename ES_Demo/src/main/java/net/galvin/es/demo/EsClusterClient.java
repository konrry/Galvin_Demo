package net.galvin.es.demo;

import net.galvin.comm.ExceptionFormatUtil;
import net.galvin.comm.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class EsClusterClient {

    private final Logger LOGGER = LoggerFactory.getLogger(EsClusterClient.class);

    private static EsClusterClient esClusterClient = null;
    private boolean esEnable = false;
    private TransportClient transportClient;
    private final static int BULK_SIZE = 4000;

    private Properties properties;
    private String hostAndPorts;
    private Integer shardNum = 3;
    private Integer replicaNum = 2;

    /**
     * 单例模式
     * @return
     */
    public static EsClusterClient get(){
        if(esClusterClient == null){
            synchronized (EsClusterClient.class){
                if (esClusterClient == null){
                    esClusterClient = new EsClusterClient();
                }
            }
        }
        return esClusterClient;
    }

    /**
     * 私有的构造函数
     */
    private EsClusterClient(){
        this.init();
        this.checkEs();
    }

    /**
     * 初始化
     */
    private void init(){
        LOGGER.info(" EsClusterClient init start .... ");
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("elasticsearch.properties");
            this.properties = new Properties();
            this.properties.load(inputStream);

            this.hostAndPorts = properties.getProperty("cluster.es.server");
            String tempEnable = properties.getProperty("cluster.es.enable");
            String clusterName = properties.getProperty("cluster.es.name");

            if(!"true".equalsIgnoreCase(tempEnable)
                    && !"ok".equalsIgnoreCase(tempEnable)
                    && !"y".equalsIgnoreCase(tempEnable)){
                LOGGER.error(" The cluster.es.enable is not enable ... ");
                return;
            }
            if(StringUtils.isEmpty(clusterName)){
                LOGGER.error(" The cluster.es.name is not enable ... ");
                return;
            }

            List<InetSocketTransportAddress> transportAddressList = this.getTransportAddresses();
            if(CollectionUtils.isEmpty(transportAddressList)){
                LOGGER.error(" transportAddressList is null ... ");
                return;
            }

            Settings settings = Settings.settingsBuilder()
                    .put("cluster.name", clusterName)
                    .put("client.transport.sniff", true)
                    .build();
            transportClient = TransportClient.builder().settings(settings).build();
            for(InetSocketTransportAddress transportAddress : transportAddressList){
                transportClient.addTransportAddress(transportAddress);
            }
        } catch (Exception e) {
            LOGGER.error(" EsClusterClient init failed ... ");
            LOGGER.error(ExceptionFormatUtil.getTrace(e));
        }
        LOGGER.info(" EsClusterClient init end .... ");
    }

    /**
     * 拼接端口
     */
    private List<InetSocketTransportAddress> getTransportAddresses(){
        List<InetSocketTransportAddress> transportAddressList = new ArrayList<InetSocketTransportAddress>();
        if(StringUtils.isEmpty(this.hostAndPorts)){
            LOGGER.error(" The hostAndPorts must not be empty ... ");
            return transportAddressList;
        }
        String[] hostAndPortArr = this.hostAndPorts.split(",");
        if(hostAndPortArr != null && hostAndPortArr.length > 0){
            for(String hostAndPort : hostAndPortArr){
                if(StringUtils.isEmpty(hostAndPort)){
                    continue;
                }
                String[] hostAndPortPair = hostAndPort.split(":");
                if(hostAndPortPair == null || hostAndPortPair.length != 2){
                    continue;
                }
                String host = hostAndPortPair[0];
                Integer port = Integer.valueOf(hostAndPortPair[1]);
                if(StringUtils.isEmpty(host) || port == null || port <= 0){
                    continue;
                }
                try {
                    transportAddressList.add(new InetSocketTransportAddress(InetAddress.getByName(host), port));
                } catch (UnknownHostException e) {
                    LOGGER.error(ExceptionFormatUtil.getTrace(e));
                }
            }
        }
        return transportAddressList;
    }

    /**
     * 检查es客户端是否初始化成功,初始化一些参数.
     */
    private void checkEs(){
        try {
            List<DiscoveryNode> discoveryNodeList = this.transportClient.listedNodes();
            if(CollectionUtils.isEmpty(discoveryNodeList)){
                LOGGER.error(" no Node discovered ... ");
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            for(DiscoveryNode discoveryNode : discoveryNodeList){
                stringBuilder.append("{ nodeId: " + discoveryNode.getId());
                stringBuilder.append(", nodeName: " + discoveryNode.getName());
                stringBuilder.append(", hostName: " + discoveryNode.getHostName());
                stringBuilder.append(", hostAddress: " + discoveryNode.getHostAddress());
                stringBuilder.append(", version: " + discoveryNode.getVersion());
                stringBuilder.append(" }      ");
            }
            LOGGER.info("============  checkEs info start   ============");
            LOGGER.info(" discoveryNodeList ===> : " + stringBuilder.toString());
            LOGGER.info("============  checkEs info end   ============");
            this.esEnable = true;
        }catch (Exception e){
            LOGGER.error(ExceptionFormatUtil.getTrace(e));
        }
    }

    public String getStrProp(String key){
        if(StringUtils.isEmpty(key)){
            return null;
        }
        return this.properties.getProperty(key);
    }

    /**
     * 创建  index  setting mapping
     */
    public void createIndex(String indexName, Integer shardNum, Integer replicaNum) throws IOException {

        /** 参数校验 */
        if(StringUtils.isEmpty(indexName)){
            LOGGER.error(" The pams indexName | shardNum | replicaNum are empty ！！！");
            return;
        }

        LOGGER.info("indexName: " + indexName + ",shardNum: " + shardNum + ",replicaNum: " + replicaNum);

        /** 获取所有的索引 */
        IndicesAdminClient indicesAdminClient = transportClient.admin().indices();

        /** 检查index是否存在 */
        GetIndexRequestBuilder getIndexRequestBuilder = indicesAdminClient.prepareGetIndex();
        GetIndexResponse getIndexResponse = getIndexRequestBuilder.get();
        String[] indices = getIndexResponse.getIndices();
        Set<String> indexSet = new HashSet<String>();
        if(indices != null && indices.length > 0){
            for(String index : indices){
                indexSet.add(index);
            }
        }
        if(indexSet.contains(indexName)){
            LOGGER.info(" The index: " + indexName + " is already exist!!!");
            return;
        }

        /** 如果不存在index，就创建一个 */
        indicesAdminClient.prepareCreate(indexName)
                .setSettings(Settings.builder()
                        .put("index.number_of_replicas", (replicaNum == null || replicaNum < 0) ? this.replicaNum : replicaNum)
                        .put("number_of_shards", (shardNum == null || shardNum <= 0) ? this.shardNum : shardNum)
                        .put("index.max_result_window", 100000000))
                .get();

    }

    /**
     * 创建type
     */
    public void createType(String indexName, String typeName, String mapping){
        /** 参数校验 */
        if(StringUtils.isEmpty(indexName) || StringUtils.isEmpty(mapping)){
            LOGGER.error(" The pams indexName | typeName | mapping are empty ！！！");
            return;
        }

        LOGGER.info("indexName: " + indexName + ",typeName: " + typeName + ",mapping: " + mapping);

        /** 获取所有的索引 */
        IndicesAdminClient indicesAdminClient = transportClient.admin().indices();
        TypesExistsRequest typesExistsRequest = new TypesExistsRequest(new String[]{ indexName }, typeName);
        ActionFuture<TypesExistsResponse> actionFuture = indicesAdminClient.typesExists(typesExistsRequest);
        try {
            boolean isExists = actionFuture.get().isExists();
            if(isExists){
                LOGGER.info(" The type " + typeName + " is existed ...");
            }
        }catch (Exception e){
            LOGGER.error(ExceptionFormatUtil.getTrace(e));
        }
        indicesAdminClient.preparePutMapping(indexName).setType(typeName).setSource(mapping).get();
    }

    public IndexResponse index(String indexName, String typeName,String docId, String document){
        if(!this.esEnable){
            return null;
        }
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.index(indexName);
        indexRequest.type(typeName);
        indexRequest.source(document);
        indexRequest.id(docId);
        IndexResponse indexResponse = this.transportClient.index(indexRequest).actionGet();
        return indexResponse;
    }

    public GetResponse query(String indexName, String typeName, String docId){
        if(!this.esEnable){
            return null;
        }
        GetResponse getResponse = this.transportClient.prepareGet(indexName,typeName,docId).get();
        return getResponse;
    }

}
