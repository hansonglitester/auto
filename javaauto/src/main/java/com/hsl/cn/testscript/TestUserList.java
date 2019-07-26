package com.hsl.cn.testscript;

import com.google.gson.Gson;
import com.hsl.cn.config.TestConfig;
import com.hsl.cn.dataobject.pojo.dev.User;
import com.hsl.cn.dataobject.pojo.test.LoginCase;
import com.hsl.cn.dataobject.pojo.test.UserListCase;
import com.hsl.cn.dataobject.respority.dev.UserDao;
import com.hsl.cn.dataobject.respority.test.UserListDao;
import com.hsl.cn.utils.DataObjectUtils;
import com.hsl.cn.utils.HttpClientUtils;
import com.jayway.jsonpath.JsonPath;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestUserList extends BaseTest {

    Logger logger= LoggerFactory.getLogger(TestLogin.class);
    UserListDao userListDao= TestConfig.applicationContext.getBean(UserListDao.class);

    @DataProvider(name = "data")
    public Object[][] getData(){
        List<UserListCase> caseList=userListDao.findAll();
        logger.info(caseList.toString());
        //定义二位数字，返回测试数据
        Object[][] data= DataObjectUtils.getData(caseList);
        return data;
    }

    @Test
    public void testUserList() throws IOException {

        Map<Object,String> params =new HashMap <>();


        UserListCase userListCase=userListDao.getOne(1);
        params.put("mobile",userListCase.getMobile());

        //发送请求
        Header [] headers={
                new BasicHeader("Content-type","application/json"),
                new BasicHeader("Cookie",TestConfig.cookieValue)
        };
        String url=TestConfig.cofigMap.get("user.list").toString();
        HttpResponse response= HttpClientUtils.sendPostRequest(headers,url,new Gson().toJson(params));

        //断言结果
        Assert.assertEquals(response.getStatusLine().getStatusCode(),200);

        String rsp_code= JsonPath.read(userListCase.getExcept(),"$.rsp_code");

        if ("0000".equals(rsp_code)){
            String responseBody=EntityUtils.toString(response.getEntity(),"UTF-8");
            //预期，查库
            UserDao userDao=TestConfig.applicationContext.getBean(UserDao.class);

            List<User> exeusers=userDao.findByMobileAndStatus(userListCase.getMobile(),0);
            System.out.println(exeusers);
            //实际，解析返回的json
            List<Map> actusers=JsonPath.read(responseBody,"$.data");
            //先判断返回的查询条数是否一致,
            Assert.assertEquals(actusers.size(),exeusers.size());
            //再判断里面的每一个对象是否一样
            for (int i=0;i<actusers.size();i++){
                System.out.println("actusers:"+actusers.get(i));
                System.out.println("exeusers:"+exeusers.get(i));
                Assert.assertEquals(actusers.get(i).get("id"),exeusers.get(i).getId());
                Assert.assertEquals(actusers.get(i).get("mobile"),exeusers.get(i).getMobile());
            }

        };



    }
}
