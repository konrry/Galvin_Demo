package net.Galvin.demo.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by Administrator on 2016/9/28.
 */
public class EmplyeeUtils {

    public static String getEmplee(String name,String sex){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("sex", sex);
        return jsonObject.toJSONString();
    }


    public static void main(String[] args) {
        System.out.println(getEmplee("Galvin", "male"));
    }

}
