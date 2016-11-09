package com.lvmama.log.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.galvin.comm.DateUtils;
import net.galvin.comm.StringUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Administrator on 2016/11/8.
 */
public class SearchMain {

    private static String keyLog = "LVMAMA_LOG ===>> msg: ";

    public static void main(String[] args) throws IOException {

//        search4Parent(38072107l, "ORD_ORDER");
//        search4Parent(38068223l, "ORD_ORDER");

        search4Object(38071771l, "ORD_ORDER_ORDER");

//        Long objectId = 12247891l;
//        String objectType = "PASS_CODE";
//        search(objectId, objectType, "D:\\work_files\\catalina_194.out");
//        search(objectId, objectType, "D:\\work_files\\catalina_202.out");
//        search(objectId, objectType, "D:\\work_files\\catalina_209.out");
    }


    private static void search(Long objectId, String objectType,String filePath) throws IOException {

        if(objectId == null || StringUtils.isEmpty(objectType)){
            return;
        }

        //读数据的流
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        System.out.println(filePath);

        int count = 0;
        String log = null;
        while ((log = bufferedReader.readLine()) != null){
            count++;
            int index = log.indexOf(keyLog);
            if(index < 0){
                continue;
            }
            index += keyLog.length();
            JSONObject jsonObject = JSON.parseObject(log.substring(index));
            String tempStrId = (String) jsonObject.get("objectId");
            String tempType = (String) jsonObject.get("objectType");
            Long tempId = null;
            if(!StringUtils.isEmpty(tempStrId)){
                tempId = Long.valueOf(tempStrId);
            }
            if(objectId.equals(tempId) && objectType.equals(tempType)){
                System.out.println(jsonObject.toString());
            }
        }
        System.out.println(count);

        bufferedReader.close();
        fileReader.close();
    }


    /**
     * 根据 parentId 和 parentType 进行验证
     */
    private static void search4Parent(Long parentId, String parentType) throws IOException {

        if(parentId == null || StringUtils.isEmpty(parentType)){
            return;
        }

        //读数据的流
        FileReader fileReader = new FileReader("D:\\work_files\\LVMAMA_LOG_KEY.log");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String log = null;
        int count = 0;
        while ((log = bufferedReader.readLine()) != null){
            JSONObject jsonObject = JSON.parseObject(log);
            String tempStrId = (String) jsonObject.get("parentId");
            String tempType = (String) jsonObject.get("parentType");
            Long tempId = null;
            if(!StringUtils.isEmpty(tempStrId)){
                tempId = Long.valueOf(tempStrId);
            }
            if(parentId.equals(tempId) && parentType.equals(tempType)){
                System.out.println(jsonObject.toString());
            }
        }

        System.out.println(count);

        bufferedReader.close();
        fileReader.close();
    }

    /**
     * 根据 objectId 和 objectType 进行验证
     */
    private static void search4Object(Long objectId, String objectType) throws IOException{

        if(objectId == null || StringUtils.isEmpty(objectType)){
            return;
        }

        //读数据的流
        FileReader fileReader = new FileReader("D:\\work_files\\LVMAMA_LOG_KEY.log");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String log = null;
        int count = 0;
        while ((log = bufferedReader.readLine()) != null){
            JSONObject jsonObject = JSON.parseObject(log);
            String tempStrId = (String) jsonObject.get("objectId");
            String tempType = (String) jsonObject.get("objectType");
            Long tempId = null;
            if(!StringUtils.isEmpty(tempStrId)){
                tempId = Long.valueOf(tempStrId);
            }
            if(objectId.equals(tempId) && objectType.equals(tempType)){
                System.out.println(jsonObject.toString());
            }
        }

        System.out.println(count);

        bufferedReader.close();
        fileReader.close();
    }

}
