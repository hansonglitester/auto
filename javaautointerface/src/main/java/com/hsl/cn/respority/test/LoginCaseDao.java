package com.hsl.cn.respority.test;

import com.hsl.cn.pojo.test.LoginCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoginCaseDao extends JpaRepository<LoginCase,Integer> {
    public List<LoginCase> findByActive(int active);

}
