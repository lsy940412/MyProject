package com.itany.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itany.Exception.IdIsNotExistException;
import com.itany.Exception.NameIsNotException;
import com.itany.mapper.PermissionMapper;
import com.itany.pojo.Permission;
import com.itany.service.PermissionService;
import com.itany.vo.PermissionVo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

 /*   @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @Override
    public PageInfo<Permission> findAll(Integer page,Integer rows) {
        PageHelper.startPage(page,rows);
        List<Permission> permissions = permissionMapper.selectAll();
        PageInfo<Permission> pageInfo = new PageInfo<>(permissions);
        return pageInfo;
    }*/
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @Override
    public PageInfo<Permission> findByName(Integer page, Integer rows, String name, String parentName){

        PermissionVo vo = new PermissionVo();
        vo.setName(name);
        vo.setParentName(parentName);
        PageHelper.startPage(page,rows);
        List<Permission> permissions = permissionMapper.selectByName(vo);
        PageInfo<Permission> pageInfo = new PageInfo<>(permissions);
        return pageInfo;
    }
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @Override
    public PermissionVo findById(Integer id) throws IdIsNotExistException {
        if(id == null ){
            throw new IdIsNotExistException("id为空");
        }
        return permissionMapper.selectById(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @Override
    public List<Permission> findParent() {

        List<Permission> parents = permissionMapper.selectParent();
        return parents;
    }

    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @Override
    public void modifyById(PermissionVo vo) {
        permissionMapper.updateById(vo);
    }



}
