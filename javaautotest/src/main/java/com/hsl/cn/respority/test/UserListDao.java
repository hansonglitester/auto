package com.hsl.cn.respority.test;

import com.hsl.cn.pojo.test.UserListCase;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserListDao extends JpaRepository<UserListCase, Integer> {

}

