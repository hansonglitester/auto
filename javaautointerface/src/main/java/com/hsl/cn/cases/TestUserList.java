package com.hsl.cn.cases;

import com.google.gson.Gson;
import com.hsl.cn.config.TestConfig;
import com.hsl.cn.respority.casesrespority.UserListDao;
import com.hsl.cn.pojo.UserListCase;
import com.hsl.cn.utils.HttpClientUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestUserList extends  BaseTest {
    @Autowired
    UserListDao userListDao;

    @DataProvider(name = "data")
    public Object[][] getData(){
        List<UserListCase> caseList=userListDao.findAll();
        logger.info(caseList.toString());

        //定义二位数字，返回测试数据
        Object[][] data=new Object[caseList.size()][];
        for(int i=0;i<caseList.size();i++){

            data[i]=new Object[]{caseList.get(i)};
        }
        return data;
    }



    @Test(dataProvider = "data", groups = {"login"})
    public void testUser(UserListCase userListCase){

        //组参数
        Map <String, String> params = new HashMap <>();
        params.put("name", userListCase.getName() + "");

        //发请求
        Header[] headers={
                new BasicHeader("Content-Type","application/json"),
                new BasicHeader("Cookie",TestConfig.cookieValue)
        };
        String s = HttpClientUtils.sendPostRequest(headers,TestConfig.getUrl("user.userList"), new Gson().toJson(params));
        //检验结果，回写数据
        System.out.println(s);
        System.out.println(HttpClientUtils.statusCode);

//         new Gson().toJson(s,Map.class);
//        Assert.assertEquals(userDetailCase.getExcept(),);




    }

}
