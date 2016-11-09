package com.lvmama.log.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import net.galvin.comm.DateUtils;
import net.galvin.comm.StringUtils;


/**
 * Created by Administrator on 2016/11/8.
 */
public class FormateMain {

    /**
     *  194 ===>> [2016-11-08 00:07:12, 2016-11-08 08:35:33]
     *  202 ===>> [2016-11-08 00:07:17, 2016-11-08 08:18:19]
     *  209 ===>> [2016-11-08 00:14:11, 2016-11-08 09:07:18]
     */
    private static String startTimeStr_194 = "2016-11-08 00:07:12";
    private static String endTimeStr_194 = "2016-11-08 08:35:33";

    private static String startTimeStr_202 = "2016-11-08 00:07:17";
    private static String endTimeStr_202 = "2016-11-08 08:18:19";

    private static String startTimeStr_209 = "2016-11-08 00:14:11";
    private static String endTimeStr_209 = "2016-11-08 09:07:18";

    private static String keyLog = "LVMAMA_LOG ===>> msg: ";

    public static void main(String[] args) throws IOException {

        //写数据的流
        FileWriter fileWriter = new FileWriter("D:\\work_files\\LVMAMA_LOG_KEY.log");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        /********************************************** 194 start ****************************************************/
        System.out.println("  --- 194 ---");
        //读数据的流
        FileReader fileReader = new FileReader("D:\\work_files\\catalina_194.out");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        Date startTime = DateUtils.str2Date(startTimeStr_194);
        Date endTime = DateUtils.str2Date(endTimeStr_194);

        int count = 0;
        int keyCount = 0;
        String log = null;
        while ((log = bufferedReader.readLine()) != null){
            count++;
            int index = log.indexOf(keyLog);
            if(index < 0){
                continue;
            }
            index += keyLog.length();
            JSONObject jsonObject = JSON.parseObject(log.substring(index));
            jsonObject.put("msgId", "Galvin_194_"+count);
            String createTimeStr = (String) jsonObject.get("createTime");
            Date createTime = DateUtils.str2Date(createTimeStr);
            if(createTime != null && createTime.compareTo(startTime) >= 0 && createTime.compareTo(endTime) <= 0){
                keyCount++;
                if(keyCount > 1) bufferedWriter.newLine();
                bufferedWriter.write(jsonObject.toJSONString());
            }
        }
        System.out.println(count);
        System.out.println(keyCount);

        bufferedReader.close();
        fileReader.close();
        /********************************************** 194 end ****************************************************/

        /********************************************** 202 start ****************************************************/
        System.out.println("  --- 202 ---");
        //读数据的流
        fileReader = new FileReader("D:\\work_files\\catalina_202.out");
        bufferedReader = new BufferedReader(fileReader);

        startTime = DateUtils.str2Date(startTimeStr_202);
        endTime = DateUtils.str2Date(endTimeStr_202);

        count = 0;
        keyCount = 0;
        log = null;
        while ((log = bufferedReader.readLine()) != null){
            count++;
            int index = log.indexOf(keyLog);
            if(index < 0){
                continue;
            }
            index += keyLog.length();
            JSONObject jsonObject = JSON.parseObject(log.substring(index));
            jsonObject.put("msgId", "Galvin_202_"+count);
            String createTimeStr = (String) jsonObject.get("createTime");
            Date createTime = DateUtils.str2Date(createTimeStr);
            if(createTime != null && createTime.compareTo(startTime) >= 0 && createTime.compareTo(endTime) <= 0){
                keyCount++;
                bufferedWriter.newLine();
                bufferedWriter.write(jsonObject.toJSONString());
            }
        }
        System.out.println(count);
        System.out.println(keyCount);

        bufferedReader.close();
        fileReader.close();
        /********************************************** 202 end ****************************************************/

        /********************************************** 209 start ****************************************************/
        System.out.println("  --- 209 ---");
        //读数据的流
        fileReader = new FileReader("D:\\work_files\\catalina_209.out");
        bufferedReader = new BufferedReader(fileReader);

        startTime = DateUtils.str2Date(startTimeStr_209);
        endTime = DateUtils.str2Date(endTimeStr_209);

        count = 0;
        keyCount = 0;
        log = null;
        while ((log = bufferedReader.readLine()) != null){
            count++;
            int index = log.indexOf(keyLog);
            if(index < 0){
                continue;
            }
            index += keyLog.length();
            JSONObject jsonObject = JSON.parseObject(log.substring(index));
            jsonObject.put("msgId", "Galvin_209_"+count);
            String createTimeStr = (String) jsonObject.get("createTime");
            Date createTime = DateUtils.str2Date(createTimeStr);
            if(createTime != null && createTime.compareTo(startTime) >= 0 && createTime.compareTo(endTime) <= 0){
                keyCount++;
                bufferedWriter.newLine();
                bufferedWriter.write(jsonObject.toJSONString());
            }
        }
        System.out.println(count);
        System.out.println(keyCount);

        bufferedReader.close();
        fileReader.close();
        /********************************************** 209 end ****************************************************/




        bufferedWriter.close();
        fileWriter.close();

    }

}



/**
    {
        "content":"将编号为[2019574501]的订单分给员工[cs15325]进行[凭证确认]任务",
            "createTime":"2016-11-08 09:50:03",
            "logName":"分配订单[CERTIFICATE_AUDIT]",
            "logType":"ORD_ORDER_DISTRIBUTION",
            "memo":"系统自动分单",
            "msgId":"Galvin_14018",
            "objectId":"2019574501",
            "objectType":"ORD_ORDER_ITEM",
            "operatorName":"SYSTEM",
            "parentId":"38073126",
            "parentType":"ORD_ORDER",
            "sysName":"VST"
    }
 */
