package com.lvmama.log.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.galvin.comm.StringUtils;
import net.galvin.comm.kafka.producer.DefaultMsgProducer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Administrator on 2016/11/8.
 */
public class Import2KafkaMain {

    public static void main(String[] args) throws IOException {
        sendLog();
    }


    private static void sendLog() {

        System.out.println("初始化生产者 ....");
        DefaultMsgProducer defaultMsgProducer = new DefaultMsgProducer();
        defaultMsgProducer.setBootstrapServers("10.112.4.95:9091,10.112.4.95:9092,10.112.4.95:9093");
        defaultMsgProducer.setSendEnable(true);
        defaultMsgProducer.init();
        System.out.println("生产者初始化成功 ...");


        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {

            System.out.println("开始初始化文件流 ...");
            fileReader = new FileReader("D:\\work_files\\LVMAMA_LOG_KEY.log");
            bufferedReader = new BufferedReader(fileReader);
            System.out.println("初始化文件流成功 ...");

            String msg = null;
            int failCount = 0;
            int sucessCount = 0;
            int count = 0;
            System.out.println("开始发消息 ...");
            while ((msg = bufferedReader.readLine()) != null){
                boolean status = defaultMsgProducer.send("VST_LOG", msg);
                count++;
                if(count%500 == 0){
                    System.out.println(" Now at " + count);
                }
                if(status){
                    sucessCount++;
                }else {
                    failCount++;
                    System.out.println("msg ===>> " + msg);
                }
                Thread.sleep(5);
            }
            System.out.println("发消息结束 ...");
            System.out.println("失败：" + failCount);
            System.out.println("成功：" + sucessCount);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bufferedReader != null){
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
