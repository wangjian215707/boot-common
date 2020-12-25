package com.github.edu.boot2.admin.rest.controller;

import com.github.edu.boot2.admin.entity.TSysOrganization;
import com.github.edu.boot2.admin.entity.TSysUser;
import com.github.edu.boot2.admin.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户管理
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/6/9
 */
@RestController
@RequestMapping("/rest/api/security/user")
@Slf4j
public class UserManagerRestController {

    @Autowired
    private ISysUserService service;

    /**
     * 用户查询
     * @param tSysUser
     * @param current
     * @param rows
     * @param order
     * @return
     */
    @GetMapping("/query/all")
    public Map<String,Object> queryAll(@ModelAttribute TSysUser tSysUser,
                                       @RequestParam(name = "current",defaultValue = "1",required = false)Integer current,
                                       @RequestParam(name = "rows",defaultValue = "10",required = false)Integer rows,
                                       @RequestParam(name = "order",required = false)String order){
        return service.getAllMapByPage(tSysUser,current,rows,order);
    }
    /**
     * 查询详情
     * @param id
     * @return
     */
    @GetMapping("/query/entity")
    public TSysUser queryEntity(@RequestParam(name = "id")Long id){
        return service.getEntity(id);
    }

    /**
     * 删除对象
     * @param ids
     * @return
     */
    @GetMapping("/del/entity")
    public String delEntity(@RequestParam(name="ids")String ids){
        return service.delete(ids);
    }

    /**
     * 保存或者更新
     * @param tSysRole
     * @return
     */
    @PostMapping("/save/entity")
    public TSysUser saveOrUpdate(@RequestBody TSysUser tSysUser){
        return service.saveOrUpdate(tSysUser);
    }
}
