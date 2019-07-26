package com.hsl.cn.testscript;

import com.hsl.cn.config.TestConfig;
import com.hsl.cn.dataobject.pojo.dev.User;
import com.hsl.cn.dataobject.pojo.test.UserDetailCase;
import com.hsl.cn.dataobject.respority.dev.UserDao;
import com.hsl.cn.dataobject.respority.test.UserDetailCaseDao;
import com.hsl.cn.utils.DataObjectUtils;
import com.hsl.cn.utils.HttpClientUtils;
import com.jayway.jsonpath.JsonPath;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestUserDetail extends  BaseTest {
    Logger logger= LoggerFactory.getLogger(TestUserDetail.class);

    String url= TestConfig.cofigMap.get("user.detail").toString();
    UserDetailCaseDao caseDao=TestConfig.applicationContext.getBean(UserDetailCaseDao.class);
    UserDao userDao =TestConfig.applicationContext.getBean(UserDao.class);


    @DataProvider(name = "data")
    public Object[][] getData(){
        List<UserDetailCase> caseList=caseDao.findAll();
        logger.info(caseList.toString());
        //定义二位数字，返回测试数据
        Object[][] data= DataObjectUtils.getData(caseList);
        return data;
    }



    @Test(dataProvider = "data")
    public void testUserDetail( UserDetailCase userDetailCase) throws IOException {

        //组参数,发请求,获得响应
        Map<String,Object> params =new HashMap <>();
        params.put("user_id",userDetailCase.getUserId());
        Header[] header={
                new BasicHeader("cookie",TestConfig.cookieValue)
        };
        HttpResponse response=HttpClientUtils.sendGetRequest(header,url,params);

        String responseBody=  EntityUtils.toString(response.getEntity());
        logger.info("实际响应body:"+responseBody);
        //断言
        Assert.assertEquals(response.getStatusLine().getStatusCode(),200);

        if (userDetailCase.getExcept().contains("0000")){
            //期望的数据，从库中查找
            User exeuser= userDao.getOne(Integer.valueOf(userDetailCase.getUserId()));
            logger.info("期望预期用户信息："+exeuser);
            //解析实际响应的数据
             Map <String ,Object> actuser= JsonPath.read(responseBody,"$.data");
            //断言实际返回的用户和库中返回的是否一致
            Assert.assertEquals(actuser.get("id"),exeuser.getId());

        }
        logger.info(userDetailCase.getExcept()+"测试执行成功");

    }

}
