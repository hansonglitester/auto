package com.hsl.cn.cases;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hsl.cn.config.TestConfig;
import com.hsl.cn.pojo.dev.User;
import com.hsl.cn.respority.test.UserDetailCaseDao;
import com.hsl.cn.pojo.test.UserDetailCase;
import com.hsl.cn.respority.dev.UserDao;
import com.hsl.cn.utils.HttpClientUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestUserDetail extends  BaseTest {
    public Logger logger= LoggerFactory.getLogger(TestUserDetail.class);

    @Autowired
    UserDetailCaseDao userDetailCaseDao;
    @Autowired
    UserDao userDao;

    @DataProvider(name = "data")
    public Object[][] getData(){
        List<UserDetailCase> caseList=userDetailCaseDao.findAll();
        logger.info(caseList.toString());

        //定义二位数字，返回测试数据
        Object[][] data=new Object[caseList.size()][];
        for(int i=0;i<caseList.size();i++){

            data[i]=new Object[]{caseList.get(i)};
        }
        return data;
    }

    @Test(dataProvider = "data",dependsOnGroups = {"login"})
    public void testUserDeatail(UserDetailCase userDetailCase){


        //组参数
        Map <String, String> params = new HashMap <>();
        params.put("user_id", userDetailCase.getUserId() + "");

        //发请求
        Header[] headers=new Header[]{
                new BasicHeader("Cookie",TestConfig.cookieValue)
        };
        String result = HttpClientUtils.sendGetRequest(TestConfig.getUrl("user.userDetail"), params);
        logger.info("返回接口数据{}",result);
        System.out.println(HttpClientUtils.statusCode);

        //检验结果，回写数据
        //查看开发数据库，和接口返回数据对比
        // {"data":
        //          {"id":2,"name":"sherry","pwd":"123456","age":15,"sex":1,"status":0},
        //  "rsp_code":"0000",
        //  "rsp_msg":"成功"
        // }
        User user=userDao.getOne(userDetailCase.getUserId());
        logger.info("查询数据库数据",user);

        //解析返回数据
        Gson gson =new Gson();
        JsonParser parser = new JsonParser();
        JsonObject jsonObject=parser.parse(result).getAsJsonObject().getAsJsonObject("data");
        User actUser=gson.fromJson(jsonObject.toString(),User.class);

        Assert.assertEquals(user.toString(),actUser.toString());




        //JsonArray Jarray = parser.parse(jstring).getAsJsonArray();
//         new Gson().toJson(s,Map.class);
//        Assert.assertEquals(userDetailCase.getExcept(),);




    }

}
