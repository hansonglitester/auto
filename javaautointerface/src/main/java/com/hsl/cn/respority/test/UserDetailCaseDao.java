package com.hsl.cn.respority.test;

import com.hsl.cn.pojo.test.UserDetailCase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailCaseDao extends JpaRepository<UserDetailCase,Integer> {
}
