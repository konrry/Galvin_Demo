package net.Galvin.demo.utils;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Es客户端
 */
public class EsClusterClient {

    private static EsClusterClient esClusterClient = null;
    private static Lock lock = new ReentrantLock();

    private TransportClient transportClient;

    /**
     * 单例模式
     * @return
     */
    public static EsClusterClient get(){
        if(esClusterClient == null){
            lock.lock();
            if(esClusterClient == null){
                esClusterClient = new EsClusterClient();
            }
            lock.unlock();
        }
        return esClusterClient;
    }

    /**
     * 私有的构造函数
     */
    private EsClusterClient(){
        init();
    }

    /**
     * 初始化
     */
    private void init(){
        System.out.println(" init start .... ");
        try {
            Settings settings = Settings.settingsBuilder()
                    .put("client.transport.sniff", true)
                    .put("cluster.name", "esCluster").build();
            transportClient = TransportClient.builder()
                    .settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.112.4.95"), 7301));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println(" init end .... ");
    }

    /**
     * 销毁
     */
    public void distroy(){
        if(this.transportClient != null){
            this.transportClient.close();
            this.transportClient = null;
        }
        System.out.println(" distroy .... ");
    }


    public IndexResponse index(String indexName, String typeName, String document) throws IOException {
        transportClient.prepareIndex();
        IndexResponse response = transportClient.prepareIndex(indexName, typeName).setSource(document).get();
        IndexRequest indexRequest = new IndexRequest();
        return response;
    }

    public void test4Index(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "QChu");
        jsonObject.put("age", 20);
        jsonObject.put("create_time", new Date());
        try {
            this.index("lvmm_log", "test", jsonObject.toJSONString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void search(){
        GetResponse getResponse = transportClient.prepareGet("lvmm_log", "test","AVeoeu4JWyd_VaE3976x").get();
        Map<String,Object> tempMap = getResponse.getSource();
        System.out.println(tempMap);
    }

    public void test4Search(){

    }

    public static void main(String[] args) {
        EsClusterClient esClusterClient = EsClusterClient.get();
        esClusterClient.search();

        esClusterClient.distroy();
    }

}
