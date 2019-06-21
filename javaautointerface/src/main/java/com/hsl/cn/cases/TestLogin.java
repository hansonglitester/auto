package com.hsl.cn.cases;

import com.google.gson.Gson;
import com.hsl.cn.config.TestConfig;
import com.hsl.cn.respority.casesrespority.LoginCaseDao;
import com.hsl.cn.pojo.LoginCase;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestLogin extends BaseTest{
    public Logger logger= LoggerFactory.getLogger(TestLogin.class);
    public String loginUrl= TestConfig.getUrl("user.login");

    @Autowired
    LoginCaseDao loginCaseDao;

    @DataProvider(name = "data")
    public Object[][] getData(){
        List<LoginCase> loginCasesList=loginCaseDao.findAll();
        logger.info(loginCasesList.toString());

        //定义二位数字，返回测试数据
        Object[][] data=new Object[loginCasesList.size()][];
        for(int i=0;i<loginCasesList.size();i++){

            data[i]=new Object[]{loginCasesList.get(i)};
        }
        return data;
    }


    @Test(groups = "login",dataProvider = "data")
    public void  testLogin(LoginCase loginCase) throws IOException {
        logger.info(loginCase.toString());
        //组参数
        Map<Object,Object> params=new HashMap <>();
        params.put("name",loginCase.getName());
        params.put("pwd",loginCase.getPwd());

        //发请求
        String result =getResult(loginUrl,params);
        logger.info(result);

        //校验测试结果
        Gson gson=new Gson();
        Map<String ,String> resultMap=gson.fromJson(result,Map.class);
        Assert.assertEquals(loginCase.getExcept(),resultMap.get("rsp_code"));
        try{

            //测试完成回写测试结果到库中
            loginCase.setActual(result);
            loginCase.setTestResult("成功");
            loginCaseDao.save(loginCase);
        }catch (Exception e){
            loginCase.setActual(result);
            loginCase.setTestResult("失败");
            loginCaseDao.save(loginCase);
        }


    }






    public String getResult(String url,Map<Object,Object> params) throws IOException {

        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost=new HttpPost(loginUrl);

        //设置请求的参数,设置请求的实体
        //添加参数，添加参数到请求队形中
        List<BasicNameValuePair> list=new ArrayList<>();
        if(params!=null){
            for(Map.Entry<Object,Object> entry:params.entrySet()){
                BasicNameValuePair bnvs=new BasicNameValuePair(entry.getKey().toString(),entry.getValue().toString());
                list.add(bnvs);
            }
        }
        UrlEncodedFormEntity entity=new UrlEncodedFormEntity(list,"utf-8");
        httpPost.setEntity(entity);
        logger.info("-----------getURI-"+httpPost.getURI().toString());

        //发送请求,获得响应实体
        HttpResponse response=httpclient.execute(httpPost);
        String result=EntityUtils.toString(response.getEntity(),"utf-8");
        //设置cookie信息，
        CookieStore cookieStore=httpclient.getCookieStore();



        List<Cookie> cookies = httpclient.getCookieStore().getCookies();
        StringBuffer tmpcookies = new StringBuffer();

        for (Cookie c : cookies) {
            System.out.println(c.getName());;
            System.out.println( c.getValue());;
            tmpcookies.append(c.getName()+"="+c.getValue() +";");
            System.out.println("cookies = "+tmpcookies);
        }

         TestConfig.cookieValue=tmpcookies.toString();

        System.out.println(cookieStore.toString()); ;
        return result;
    }


}
