package com.itany.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itany.initutil.PermissionInitUtil;
import com.itany.mapper.PermissionMapper;
import com.itany.mapper.RoleMapper;
import com.itany.pojo.Role;
import com.itany.vo.TreeVo;
import com.itany.service.RoleSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleSevice {
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @Override
    public PageInfo<Role> findByName(String name,Integer page,Integer rows) {
        PageHelper.startPage(page,rows);
        List<Role> roles = roleMapper.selectByName(name);
        PageInfo<Role> pageInfo = new PageInfo<>(roles);
        return pageInfo;
    }

    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @Override
    public List<TreeVo> findById(Integer id) {
        List<TreeVo> permissionList = new ArrayList<>();
            if (id == null){
                permissionList = permissionMapper.selectAll();

            }else {
                List<Integer> ids = roleMapper.selectById(id);
                PermissionInitUtil.permissionIds.clear();
                for (Integer permissionId :ids){
                    System.out.println(permissionId+"-----id----");
                    PermissionInitUtil.permissionIds.add(permissionId);
            }
            permissionList = permissionMapper.selectAll();
            }
        return permissionList;
    }

    @Override
    public void modifyById(Integer id, String permissionids, String name) {
        System.out.println("----删除中间表roleId对应的permissionId------");
        //删除中间表roleId对应的permissionId
        roleMapper.deletePermissionByRoleId(id);
        System.out.println("----插入修改后的permission------");
        //插入修改后的permission
        for (String permissionId : permissionids.split(",")  ){
            roleMapper.insertPermissionByRoleId(id,Integer.parseInt(permissionId));
        }
        System.out.println("----修改roleName------");
        //修改roleName
        roleMapper.updateRoleNameByRoleId(id,name);

    }

    @Override
    public void removeRoleById(Integer id) {
        //先删除中间表的permission
        roleMapper.deletePermissionByRoleId(id);
        //删出role表的
        roleMapper.deleteRoleByRoleId(id);
    }


}
