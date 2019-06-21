package com.hsl.cn.cases;

import com.hsl.cn.config.TestConfig;
import com.hsl.cn.pojo.User;
import com.hsl.cn.respority.casesrespority.UserDetailCaseDao;
import com.hsl.cn.pojo.UserDetailCase;
import com.hsl.cn.respority.resultrespority.UserDao;
import com.hsl.cn.utils.HttpClientUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestUserDetail extends  BaseTest {
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

    @Test(dataProvider = "data", groups = {"login"})
    public void testUser(UserDetailCase userDetailCase){


        //组参数
        Map <String, String> params = new HashMap <>();
        params.put("user_id", userDetailCase.getUserId() + "");

        //发请求
        Header[] headers=new Header[]{
                new BasicHeader("Cookie",TestConfig.cookieValue)
        };
        String s = HttpClientUtils.sendGetRequest(TestConfig.getUrl("user.userDetail"), params);
        //检验结果，回写数据
        System.out.println(s);
        System.out.println(HttpClientUtils.statusCode);

        //查看开发数据库，和接口返回数据对比
        User user=userDao.getOne(userDetailCase.getUserId());
        System.out.println(user);

//         new Gson().toJson(s,Map.class);
//        Assert.assertEquals(userDetailCase.getExcept(),);




    }

}
