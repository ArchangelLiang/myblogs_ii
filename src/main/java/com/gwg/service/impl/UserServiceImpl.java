package com.gwg.service.impl;

import com.gwg.mapper.UserMapper;
import com.gwg.pojo.User;
import com.gwg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User checkUser(String userName, String password) {
        if (userName == null || password == null || userName.trim().equals("") || password.trim().equals("")) {
            return null;
        }
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        return this.userMapper.findUserByUserNameAndPassword(userName, password);
    }


}
