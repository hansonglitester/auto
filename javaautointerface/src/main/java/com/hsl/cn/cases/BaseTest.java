package com.hsl.cn.cases;

import com.hsl.cn.Application;
import com.hsl.cn.dao.LoginCaseDao;
import com.hsl.cn.pojo.LoginCase;
import org.apache.http.client.CookieStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = Application.class)
public class BaseTest extends AbstractTestNGSpringContextTests {
    @Autowired
    LoginCaseDao loginCaseDao;

    public void init(){
        LoginCase loginCase=new LoginCase();
        loginCase.setName("sherry");
        loginCase.setPwd("123456");
        loginCaseDao.save(loginCase);
    }

}
