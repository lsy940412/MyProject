package com.itany.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itany.mapper.ManagerUserMapper;
import com.itany.pojo.ManagerUser;
import com.itany.pojo.Role;
import com.itany.service.ManagerUserService;
import com.itany.vo.TreeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.security.PrivateKey;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
public class ManagerUserServiceImpl implements ManagerUserService {

    @Autowired
    private ManagerUserMapper managerUserMapper;

    /**
     * findAllByName
     * @param username
     * @param page
     * @param rows
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @Override
    public PageInfo<ManagerUser> findAllByName(String username, Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        List<ManagerUser> userList = managerUserMapper.selectByName(username);
        PageInfo<ManagerUser> pageInfo = new PageInfo<>(userList);
        return pageInfo;
    }

    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @Override
    public List<TreeVo> findAllRoleName() {
        List<TreeVo> treeVos = managerUserMapper.selectAllRoleName();
        return treeVos;
    }

    @Override
    public void addManagerUser(String username,String password,String roleIds) {
        ManagerUser managerUser = new ManagerUser();
        managerUser.setUserName(username);
        managerUser.setPassword(password);
        managerUserMapper.insertManagerUser(managerUser);
        for(String roleId:roleIds.split(",")){
            managerUserMapper.insertRoleByUserId(managerUser.getId(),Integer.parseInt(roleId));
        }
    }




}
