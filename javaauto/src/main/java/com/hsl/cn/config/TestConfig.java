package com.hsl.cn.config;

import com.hsl.cn.Application;
import com.hsl.cn.dataobject.pojo.test.ProjectConfig;
import com.hsl.cn.dataobject.pojo.test.UrlConfig;
import com.hsl.cn.dataobject.respority.test.ProjectConfigDao;
import com.hsl.cn.dataobject.respority.test.UrlConfigDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestConfig {
    public static Logger logger=LoggerFactory.getLogger(TestConfig.class);

    //获取Spring容器的上下文
    public static ApplicationContext applicationContext= SpringApplication.run(Application.class);
    //从数据库读取到所有的配置信息
    public static Map<String,String> cofigMap=new HashMap <>();
    //存取cookie 信息
    public static String cookieValue;


    static {
        //获取数据访问对象
        UrlConfigDao urlconfigDao = applicationContext.getBean(UrlConfigDao.class);
        ProjectConfigDao projectConfigDao = applicationContext.getBean(ProjectConfigDao.class);

        //配置参数，存放到map中
        List<ProjectConfig> projectConfigs = projectConfigDao.findAll();

        for (ProjectConfig config : projectConfigs) {
            cofigMap.put(config.getConfigkey(),config.getConfigvalue());
        }

        //测试接口的路径存入map

        List<UrlConfig> configs = urlconfigDao.findAll();
        for (UrlConfig config : configs) {
            cofigMap.put(config.getInterfacename(),cofigMap.get("test.url")+ config.getUri());
        }

        logger.info(cofigMap.toString());
    }


}
