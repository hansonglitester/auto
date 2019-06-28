package com.hsl.cn.cases;


import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.hsl.cn.config.TestConfig;
import com.hsl.cn.respority.test.SaveUserCaseDao;
import com.hsl.cn.pojo.test.SaveUserCase;
import com.hsl.cn.utils.HttpClientUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestSaveUser extends BaseTest  {
    public Logger logger= LoggerFactory.getLogger(TestSaveUser.class);

    @Autowired
    SaveUserCaseDao saveUserCaseDao;

    @DataProvider(name = "data")
    public Object[][] getData(){
        List<SaveUserCase> loginCasesList=saveUserCaseDao.findAll();
        logger.info(loginCasesList.toString());

        //定义二位数字，返回测试数据
        Object[][] data=new Object[loginCasesList.size()][];
        for(int i=0;i<loginCasesList.size();i++){

            data[i]=new Object[]{loginCasesList.get(i)};
        }
        return data;
    }

    @Test(dataProvider = "data",dependsOnGroups = {"login"})
    public void testSaveUser(SaveUserCase userCase) throws IOException {
        //组参数
        Gson gson=new Gson ();
        Map<String,Object> map=new HashMap <>();
        map.put("name",userCase.getName());
        map.put("age",userCase.getAge());
        map.put("pwd",userCase.getPwd());
        map.put("sex",userCase.getSex());
        map.put("status",userCase.getStatus());
        String param=JSON.toJSONString(map);


        //发请求
        Header[] headers={
                new BasicHeader("Content-Type","application/json"),
                new BasicHeader("Cookie",TestConfig.cookieValue)
        };
        String url= TestConfig.getUrl("user.saveUser");
        String result= HttpClientUtils.sendPostRequest(headers,url,param);
        logger.info(result);

        //解析结果，校验
        Map<String ,String > jsonObject =gson.fromJson(result,Map.class);
//        try {
//            Assert.assertEquals(jsonObject.get("rsp_code"),userCase.getExcept());
//            //校验完成，回写测试结果
//            userCase.setActual(result);
//            userCase.setTestResult("成功");
//            saveUserCaseDao.save(userCase);
//        }catch (Exception e){
//            userCase.setActual(result);
//            userCase.setTestResult("失败");
//            saveUserCaseDao.save(userCase);
//        }
    }

}
