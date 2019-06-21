package com.hsl.cn.respority.casesrespority;

import com.hsl.cn.pojo.UserListCase;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserListDao extends JpaRepository<UserListCase, Integer> {

}

