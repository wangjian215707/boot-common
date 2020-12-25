package com.github.edu.boot2.admin.rest.controller;
import com.github.edu.boot2.admin.entity.TSysRole;
import com.github.edu.boot2.admin.service.ISysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统角色管理
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/3/3
 */
@RestController
@RequestMapping("/rest/api/security/role")
@Slf4j
public class SysRoleRestController {

    @Autowired
    private ISysRoleService service;

    /**
     * 根据用户id查询用户角色信息
     * @param userId
     * @return
     */
    @GetMapping("/query/user/roles")
    public List<TSysRole> queryUserRoles(@RequestParam("userId")String userId){

        return service.queryAllByUserId(userId);
    }

    /**
     * 系统菜单管理
     * @param tSysRole
     * @param current
     * @param rows
     * @param order
     * @return
     */
    @GetMapping("/query/all")
    public Map<String,Object> queryAll(@ModelAttribute TSysRole tSysRole,
                                       @RequestParam(name = "current",defaultValue = "1",required = false)Integer current,
                                       @RequestParam(name = "rows",defaultValue = "10",required = false)Integer rows,
                                       @RequestParam(name = "order",required = false)String order){
        return service.getAllMapByPage(tSysRole,current,rows,order);
    }
    /**
     * 查询详情
     * @param id
     * @return
     */
    @GetMapping("/query/entity")
    public TSysRole queryEntity(@RequestParam(name = "id")Long id){
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
    public TSysRole saveOrUpdate(@RequestBody TSysRole tSysRole){
        return service.saveOrUpdate(tSysRole);
    }
}
