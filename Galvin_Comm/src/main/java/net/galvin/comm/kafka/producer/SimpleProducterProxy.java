package net.galvin.comm.kafka.producer;

import net.galvin.comm.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 简单的实现
 */
public class SimpleProducterProxy {

    protected String topic;
    protected MsgProducer msgProducer;

    public void setTopic(String topic) {
        this.topic = topic;
    }
    public void setMsgProducer(MsgProducer msgProducer) {
        this.msgProducer = msgProducer;
    }

    public void handOut(Object msg) {
        if(StringUtils.isEmpty(this.topic)){
            System.out.println(" The topic can not be empty ... ");
            return;
        }
        this.msgProducer.send(topic,msg);
    }

    public void handOut(Object key, Object msg) {
        if(StringUtils.isEmpty(this.topic)){
            System.out.println(" The topic can not be empty ... ");
            return;
        }
        this.msgProducer.send(topic,key,msg);
    }

    public void handOut(Object key, Object partKey, Object msg) {
        if(StringUtils.isEmpty(this.topic)){
            System.out.println(" The topic can not be empty ... ");
            return;
        }
        this.msgProducer.send(topic,key,msg);
    }

}
