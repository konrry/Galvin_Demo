package net.galvin.canal;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.List;

/**
 * Created by Administrator on 2017/4/26.
 */
public class Demo {

    private volatile static boolean isRunning = true;

    public static void main(String[] args) throws InvalidProtocolBufferException {

        String zks = "10.112.4.177:2190,10.112.4.177:2191,10.112.4.177:2192";
        String destination = "test";

        CanalConnector connector = CanalConnectors.newClusterConnector(zks,destination,"","");
        connector.connect();
        connector.subscribe(".*\\..*");

        while (isRunning){
            Message message = connector.getWithoutAck(1000);
            long batchId = message.getId();
            int size = message.getEntries().size();
            if(batchId < 0 || size <= 0){
                try {
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            for(CanalEntry.Entry entry:message.getEntries()){
                if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                    System.out.println("The entry_type is "+entry.getEntryType().name());
                    continue;
                }else if(entry.getEntryType()== CanalEntry.EntryType.ROWDATA){
                    CanalEntry.RowChange row = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
                    CanalEntry.EventType rowEventType = row.getEventType();
                    System.out.println(String.format("================> binlog[%s:%s] , name[%s,%s] , eventType : %s",
                            entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                            entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                            rowEventType));
                    for(CanalEntry.RowData rowData : row.getRowDatasList()){
                        List<CanalEntry.Column> befColumns = rowData.getBeforeColumnsList();
                        System.out.println("============getBeforeColumnsList================");
                        for(CanalEntry.Column col:befColumns){
                            System.out.println("name="+col.getName()+",value="+col.getValue());
                        }
                        System.out.println("============getAfterColumnsList================");
                        List<CanalEntry.Column> aftColumns = rowData.getAfterColumnsList();
                        for(CanalEntry.Column col:aftColumns){
                            System.out.println("name="+col.getName()+",value="+col.getValue());
                        }
                    }
                }
            }
            connector.ack(batchId); // 提交确认
//            connector.rollback(batchId); // 处理失败, 回滚数据
        }

    }

}
