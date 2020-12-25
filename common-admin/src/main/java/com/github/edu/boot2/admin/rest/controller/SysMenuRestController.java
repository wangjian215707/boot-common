package com.github.edu.boot2.admin.rest.controller;

import com.github.edu.boot2.admin.entity.TSysMenu;
import com.github.edu.boot2.admin.service.ISysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * 系统菜单管理
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/3/2
 */
@RestController
@RequestMapping("/rest/api/security/menu")
@Slf4j
public class SysMenuRestController {

    @Autowired
    private ISysMenuService service;

    /**
     * 系统菜单管理
     * @param tSysMenu
     * @param current
     * @param rows
     * @param order
     * @return
     */
    @GetMapping("/query/all")
    public Map<String,Object> queryAll(@ModelAttribute TSysMenu tSysMenu,
                                       @RequestParam(name = "current",defaultValue = "1",required = false)Integer current,
                                       @RequestParam(name = "rows",defaultValue = "10",required = false)Integer rows,
                                       @RequestParam(name = "order",required = false)String order){
        return service.getAllMapByPage(tSysMenu,current,rows,order);
    }
    /**
     * 查询详情
     * @param id
     * @return
     */
    @GetMapping("/query/entity")
    public TSysMenu queryEntity(@RequestParam(name = "id")Long id){
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
     * @param tSysMenu
     * @return
     */
    @PostMapping("/save/entity")
    public TSysMenu saveOrUpdate(@RequestBody TSysMenu tSysMenu){
        return service.saveOrUpdate(tSysMenu);
    }
}
