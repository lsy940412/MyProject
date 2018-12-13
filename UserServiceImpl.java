package com.itany.userservice.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itany.Exception.ImgCodeError;
import com.itany.Exception.PasswordError;
import com.itany.Exception.UserNotExistException;
import com.itany.pojo.User;
import com.itany.userapi.service.UserService;
import com.itany.userservice.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.*;

@Service
@com.alibaba.dubbo.config.annotation.Service
@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public void addUser(User user) throws ImgCodeError, PasswordError {
        user.setCreateDate(new Date());
        System.out.println(user.getPhone());
        user.setUsername(user.getPhone());
        user.setStatus("1");
        String password = user.getPassword();
        String rePassword = user.getRePassword();

        if(!password.equals(rePassword)){
            throw new PasswordError("俩次密码输入不同");
        }
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));

        userDao.addUser(user);
        System.out.println("----service-----");
    }

    @Override
    public PageInfo<User> findUsers(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<User> list =  userDao.findUserAll();
        PageInfo<User> pageInfo = new PageInfo<User>(list);
        return pageInfo;
    }

    @Override
    public User login(User user) throws UserNotExistException {
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        User login = userDao.login(user);
        if(login == null){
            throw new UserNotExistException("用户名或密码错误");
        }
        return login;

    }
}
