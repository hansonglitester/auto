package com.hsl.cn.testscript;

import com.hsl.cn.appmodules.LoginAction;
import com.hsl.cn.config.TestConfig;
import org.testng.annotations.BeforeClass;

import java.io.IOException;

public class BaseTest {
    @BeforeClass
    public  void  startUp() throws IOException {
        String mobile = TestConfig.cofigMap.get("mobile").toString();
        String pwd = TestConfig.cofigMap.get("pwd").toString();
        String url = TestConfig.cofigMap.get("user.login").toString();
        TestConfig.cookieValue = LoginAction.getLoginCookie(mobile, pwd, url);
    }
}
