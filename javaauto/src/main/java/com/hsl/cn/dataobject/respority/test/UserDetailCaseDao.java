package com.hsl.cn.dataobject.respority.test;

import com.hsl.cn.dataobject.pojo.test.UserDetailCase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailCaseDao extends JpaRepository<UserDetailCase,Integer> {
}
