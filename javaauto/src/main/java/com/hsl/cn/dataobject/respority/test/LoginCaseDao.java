package com.hsl.cn.dataobject.respority.test;

import com.hsl.cn.dataobject.pojo.test.LoginCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoginCaseDao extends JpaRepository<LoginCase,Integer> {
    List<LoginCase> findByIdGreaterThan(Integer id);
}
