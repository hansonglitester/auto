package com.hsl.cn.dataobject.respority.test;

import com.hsl.cn.dataobject.pojo.test.UserListCase;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserListDao extends JpaRepository<UserListCase, Integer> {

}

