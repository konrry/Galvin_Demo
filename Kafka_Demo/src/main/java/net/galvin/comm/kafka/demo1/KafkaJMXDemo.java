package net.galvin.comm.kafka.demo1;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 * Created by Administrator on 2016/9/25.
 */
public class KafkaJMXDemo {

    private static final String MESSAGE_IN_PER_SEC = "kafka.server:type=BrokerTopicMetrics,name=MessagesInPerSec";
    private static final String BYTES_IN_PER_SEC = "kafka.server:type=BrokerTopicMetrics,name=BytesInPerSec";
    private static final String BYTES_OUT_PER_SEC = "kafka.server:type=BrokerTopicMetrics,name=BytesOutPerSec";
    private static final String PRODUCE_REQUEST_PER_SEC = "kafka.network:type=RequestMetrics,name=RequestsPerSec,request=Produce";
    private static final String CONSUMER_REQUEST_PER_SEC = "kafka.network:type=RequestMetrics,name=RequestsPerSec,request=FetchConsumer";
    private static final String FLOWER_REQUEST_PER_SEC = "kafka.network:type=RequestMetrics,name=RequestsPerSec,request=FetchFollower";
    private static final String ACTIVE_CONTROLLER_COUNT = "kafka.controller:type=KafkaController,name=ActiveControllerCount";
    private static final String PART_COUNT = "kafka.server:type=ReplicaManager,name=PartitionCount";
    public void extractMonitorData() {

        String jmxURL = "service:jmx:rmi:///jndi/rmi://10.112.4.95:9001/jmxrmi";

        try {
            JMXServiceURL url = new JMXServiceURL(jmxURL);
            JMXConnector jmxc = JMXConnectorFactory.connect(url);
            MBeanServerConnection jmxConnection = jmxc.getMBeanServerConnection();
            ObjectName messageCountObj = new ObjectName(MESSAGE_IN_PER_SEC);
            ObjectName bytesInPerSecObj = new ObjectName(BYTES_IN_PER_SEC);
            ObjectName bytesOutPerSecObj = new ObjectName(BYTES_OUT_PER_SEC);
            ObjectName produceRequestsPerSecObj = new ObjectName(PRODUCE_REQUEST_PER_SEC);
            ObjectName consumerRequestsPerSecObj = new ObjectName(CONSUMER_REQUEST_PER_SEC);
            ObjectName flowerRequestsPerSecObj = new ObjectName(FLOWER_REQUEST_PER_SEC);
            ObjectName activeControllerCountObj = new ObjectName(ACTIVE_CONTROLLER_COUNT);
            ObjectName partCountObj = new ObjectName(PART_COUNT);
            Long messagesInPerSec = (Long) jmxConnection.getAttribute(messageCountObj, "Count");
            Long bytesInPerSec = (Long) jmxConnection.getAttribute(bytesInPerSecObj, "Count");
            Long bytesOutPerSec = (Long) jmxConnection.getAttribute(bytesOutPerSecObj, "Count");
            Long produceRequestCountPerSec = (Long) jmxConnection.getAttribute(produceRequestsPerSecObj, "Count");
            Long consumerRequestCountPerSec = (Long) jmxConnection.getAttribute(consumerRequestsPerSecObj, "Count");
            Long flowerRequestCountPerSec = (Long) jmxConnection.getAttribute(flowerRequestsPerSecObj, "Count");
            Integer activeControllerCount = (Integer) jmxConnection.getAttribute(activeControllerCountObj, "Value");
            Integer partCount = (Integer) jmxConnection.getAttribute(partCountObj, "Value");
            System.out.println("messagesInPerSec=" + messagesInPerSec);
            System.out.println("bytesInPerSec=" + bytesInPerSec);
            System.out.println("bytesOutPerSec=" + bytesOutPerSec);
            System.out.println("produceRequestCountPerSec=" + produceRequestCountPerSec);
            System.out.println("consumerRequestCountPerSec=" + consumerRequestCountPerSec);
            System.out.println("flowerRequestCountPerSec=" + flowerRequestCountPerSec);
            System.out.println("activeControllerCount=" + activeControllerCount);
            System.out.println("partCount=" + partCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new KafkaJMXDemo().extractMonitorData();
    }

}
