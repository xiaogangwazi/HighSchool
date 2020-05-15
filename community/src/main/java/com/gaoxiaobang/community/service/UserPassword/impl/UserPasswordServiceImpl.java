package com.gaoxiaobang.community.service.UserPassword.impl;

import com.gaoxiaobang.community.dao.UserPasswordMapper;
import com.gaoxiaobang.community.entity.Focustopic;
import com.gaoxiaobang.community.entity.UserPassword;
import com.gaoxiaobang.community.service.UserPassword.UserPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserPasswordServiceImpl implements UserPasswordService {
    @Autowired
    private UserPasswordMapper userPasswordMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(UserPassword record) {
        return userPasswordMapper.insert(record);
    }

    @Override
    public UserPassword selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public UserPassword selectByUserId(Integer userid) {
        return userPasswordMapper.selectByUserId(userid);
    }

    @Override
    public List<UserPassword> selectAll() {
        return null;
    }

    @Override
    public int updateByPrimaryKey(UserPassword record) {
        return 0;
    }
}
