package com.hsl.cn.respority.resultrespority;

import com.hsl.cn.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User,Integer> {
}
