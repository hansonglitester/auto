package com.hsl.cn.testscript;

import com.hsl.cn.appmodules.LoginAction;
import com.hsl.cn.config.TestConfig;
import com.hsl.cn.dataobject.pojo.dev.User;
import com.hsl.cn.dataobject.pojo.test.LoginCase;
import com.hsl.cn.dataobject.respority.dev.UserDao;
import com.hsl.cn.dataobject.respority.test.LoginCaseDao;
import com.hsl.cn.utils.DataObjectUtils;
import com.jayway.jsonpath.JsonPath;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TestLogin extends BaseTest {
    Logger logger= LoggerFactory.getLogger(TestLogin.class);

    String url= TestConfig.cofigMap.get("user.login").toString();
    LoginCaseDao caseDao=TestConfig.applicationContext.getBean(LoginCaseDao.class);
    UserDao userDao =TestConfig.applicationContext.getBean(UserDao.class);


    @DataProvider(name = "data")
    public Object[][] getData(){
        List<LoginCase> loginCasesList=caseDao.findAll();
        logger.info(loginCasesList.toString());
        //定义二位数字，返回测试数据
        Object[][] data= DataObjectUtils.getData(loginCasesList);
        return data;
    }


    @Test(dataProvider = "data")
    public void testLoginAll(LoginCase loginCase) throws IOException {

        HttpResponse response=LoginAction.excute(loginCase,url);
        String responseBody=  EntityUtils.toString(response.getEntity());

        Assert.assertEquals(response.getStatusLine().getStatusCode(),200);
        logger.info("实际响应body:"+responseBody);
        String rsp_code= JsonPath.read(loginCase.getExcept(),"$.rsp_code");

        if ("0000".equals(rsp_code)){
            //期望的数据，从库中查找
            List<User> exeuser=userDao.findByMobile(loginCase.getMobile());
            logger.info("期望预期用户信息："+exeuser);
            //解析实际响应的数据
            Map map = JsonPath.read(responseBody, "$.user");
            //断言实际返回的用户和库中返回的是否一致
            Assert.assertEquals(map.get("id"), exeuser.get(0).getId());

        }else{
            Assert.assertEquals(responseBody,loginCase.getExcept());
        }
        logger.info(loginCase.getTestTitle()+ "执行成功");

    }


}
