package com.hsl.cn.cases;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hsl.cn.config.TestConfig;
import com.hsl.cn.dao.SaveUserCaseDao;
import com.hsl.cn.pojo.SaveUserCase;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestSaveUser extends BaseTest  {
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
        HttpPost httpPost=new HttpPost(TestConfig.getUrl("user.saveUser"));
        httpPost.setHeader("Content-Type","application/json");
        httpPost.setEntity(new StringEntity(param,"utf-8"));
        DefaultHttpClient httpClient=new DefaultHttpClient();
        httpClient.setCookieStore(TestConfig.cookieStore);
        HttpResponse httpResponse=httpClient.execute(httpPost);
        String result=EntityUtils.toString(httpResponse.getEntity(),"utf-8");
        System.out.println(result);

        //校验
        Map<String ,String > jsonObject =gson.fromJson(result,Map.class);
        Assert.assertEquals(jsonObject.get("rsp_code"),userCase.getExcept());

    }

}
