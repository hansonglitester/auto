package com.hsl.cn.dataobject.respority.dev;

import com.hsl.cn.dataobject.pojo.dev.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserDao extends JpaRepository<User,Integer> {
    List<User> findByMobile( String mobile);
    List<User> findByMobileAndStatus(String mobile, int id);

    @Transactional
    Long deleteByMobile(String mobile);
}
