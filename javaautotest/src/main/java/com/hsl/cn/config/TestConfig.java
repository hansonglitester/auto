package com.hsl.cn.config;

import com.hsl.cn.Application;
import com.hsl.cn.pojo.test.Config;
import com.hsl.cn.respority.test.ConfigDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestConfig {
    public static Logger logger=LoggerFactory.getLogger(TestConfig.class);

    //获取Spring的上下文
    public static ApplicationContext applicationContext=  SpringApplication.run(Application.class);
    //从数据库读取到所有的配置信息：接口名---url
    public static Map<String,String> cofigMap=new HashMap <>();
    //存取cookie 信息
    public static String cookieValue;


    static {
        ConfigDao configDao=applicationContext.getBean(ConfigDao.class);
        List<Config> configs=configDao.findAll();
        Config testurl=configDao.getOne(0);
        for (Config config:configs) {
            if (!config.getInterfacename().equals("testurl")){
                cofigMap.put(config.getInterfacename(),testurl.getUrl()+config.getUrl());
            }
            else{
                cofigMap.put(config.getInterfacename(),config.getUrl());
            }
        }

        logger.info(cofigMap.toString());
    }

    public static String  getUrl(String key){

        return cofigMap.get(key);
    }

    public static void main(String [] args){
        getUrl("user.login");
    }
}
