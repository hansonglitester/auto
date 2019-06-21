package com.hsl.cn.config;

import org.apache.http.client.CookieStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class TestConfig {
    public static Logger logger=LoggerFactory.getLogger(TestConfig.class);
    public static ResourceBundle bunble = ResourceBundle.getBundle("config");

    public static String testurl=bunble.getString("test.url");
    public static String cookieValue;


    //返回一个map,包含key,value

    public static Map<String,String > loadFile(){
        Map<String,String > map=new HashMap<String, String>();
        Enumeration<String> enumKeys=  bunble.getKeys();
        while (enumKeys.hasMoreElements()) {
            // 获取枚举中的键
            String key = (String) enumKeys.nextElement();
            String value="";
            if(!bunble.getString(key).equals(testurl)){
                value =testurl+ bunble.getString(key);
            }else{
                value=testurl;
            }

            logger.info("系统属性文件中的数据是：【" + key + "=" + value + "】");
            //放到map里面
            map.put(key,value);

        }
        return map;
    }



    public static String getUrl(String key){
        return loadFile().get(key);
    }

    public static void main(String[] args){
        loadFile();
    }
}
