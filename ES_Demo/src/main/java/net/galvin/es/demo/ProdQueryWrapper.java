package net.galvin.es.demo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.galvin.comm.ExceptionFormatUtil;
import net.galvin.comm.StringUtils;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by qchu on 17-1-16.
 */
public class ProdQueryWrapper {

    private final Logger LOGGER = LoggerFactory.getLogger(ProdQueryWrapper.class);

    private static ProdQueryWrapper prodQueryWrapper = null;

    public static ProdQueryWrapper get(){
        if(prodQueryWrapper == null){
            synchronized (EsClusterClient.class){
                if (prodQueryWrapper == null){
                    prodQueryWrapper = new ProdQueryWrapper();
                }
            }
        }
        return prodQueryWrapper;
    }

    public void init(){
        try {
            EsClusterClient esClusterClient = EsClusterClient.get();
            String indexName = esClusterClient.getStrProp("cluster.es.index.name");
            Integer replicaNum = StringUtils.str2Integer(esClusterClient.getStrProp("cluster.es.index.number.replicas"));
            Integer shardNum = StringUtils.str2Integer(esClusterClient.getStrProp("cluster.es.number.shards"));
            esClusterClient.createIndex(indexName,shardNum,replicaNum);
            StringBuffer mapping = new StringBuffer();
            mapping.append("{");
            mapping.append("    \"prodProduct\": ");
            mapping.append("    {");
            mapping.append("      \"properties\": ");
            mapping.append("      {");
            mapping.append("        \"productId\": { \"type\": \"long\" },");
            mapping.append("        \"categoryId\": { \"type\": \"long\" },");
            mapping.append("        \"productName\": { \"type\": \"string\", \"index\": \"not_analyzed\" },");
            mapping.append("        \"suppGoods\": {");
            mapping.append("            \"type\": \"nested\",");
            mapping.append("            \"properties\": {");
            mapping.append("              \"suppGoodsId\": { \"type\": \"long\" },");
            mapping.append("              \"suppGoodsName\": { \"type\": \"string\", \"index\": \"not_analyzed\" }");
            mapping.append("            }");
            mapping.append("         }");
            mapping.append("      }");
            mapping.append("    }");
            mapping.append("}");
            esClusterClient.createType(indexName,"prodProduct",mapping.toString());
        }catch (Exception e){
            LOGGER.error(ExceptionFormatUtil.getTrace(e));
        }
    }


    public void save(long productId, long categoryId, long suppGoodsId){
        JSONObject prodJson = new JSONObject();
        JSONArray goodsArrJson = new JSONArray();
        prodJson.put("productId",productId);
        prodJson.put("categoryId",categoryId);
        prodJson.put("productName","测试产品"+productId);
        prodJson.put("suppGoods",goodsArrJson);
        JSONObject goodsJson1 = new JSONObject();
        goodsArrJson.add(goodsJson1);
        goodsJson1.put("suppGoodsId",suppGoodsId);
        goodsJson1.put("suppGoodsName","测试1商品"+suppGoodsId);

        JSONObject goodsJson2 = new JSONObject();
        goodsArrJson.add(goodsJson2);
        goodsJson2.put("suppGoodsId",suppGoodsId);
        goodsJson2.put("suppGoodsName","测试2商品"+suppGoodsId);
        String docuId = productId+"_"+categoryId;
        IndexResponse indexResponse = EsClusterClient.get().index("prod_query","prodProduct",docuId,prodJson.toJSONString());
        StringBuilder builder = new StringBuilder();
        builder.append("IndexResponse[");
        builder.append("index=").append(indexResponse.getIndex());
        builder.append(",type=").append(indexResponse.getType());
        builder.append(",id=").append(indexResponse.getId());
        builder.append(",version=").append(indexResponse.getVersion());
        builder.append(",created=").append(indexResponse.isCreated());
        builder.append("]");
        LOGGER.info(builder.toString());
    }

    public void query(long productId, long categoryId){
        GetResponse getResponse = EsClusterClient.get().query("prod_query","prodProduct",productId+"_"+categoryId);
        System.out.println(getResponse.getSourceAsMap());
    }

}
