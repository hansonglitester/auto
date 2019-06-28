package com.hsl.cn.respority.dev;

import com.hsl.cn.pojo.dev.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User,Integer> {

}
