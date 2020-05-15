package com.gaoxiaobang.community.service.UserPassword;

import com.gaoxiaobang.community.entity.Focustopic;
import com.gaoxiaobang.community.entity.UserPassword;

import java.util.List;

public interface UserPasswordService {
    int deleteByPrimaryKey(Integer id);

    int insert(UserPassword record);

    UserPassword selectByPrimaryKey(Integer id);
    UserPassword selectByUserId(Integer userid);


    List<UserPassword> selectAll();

    int updateByPrimaryKey(UserPassword record);
}
