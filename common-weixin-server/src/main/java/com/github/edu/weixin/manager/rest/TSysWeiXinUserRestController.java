package com.github.edu.weixin.manager.rest;


import com.github.admin.edu.assembly.common.entity.JsonEntity;
import com.github.admin.edu.assembly.common.util.JsonUtils;
import com.github.edu.weixin.manager.entity.TSysWxUser;
import com.github.edu.weixin.manager.service.TSysWeiXinUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/api/weixin/user")
public class TSysWeiXinUserRestController {

    @Autowired
    private TSysWeiXinUserService service;

    @GetMapping("/info")
    public String getWeiXinUser(@RequestParam("openId")String openId){

        return service.getWeiXinUser(openId);
    }
    @GetMapping("/delete")
    public String deleteWeiXinUser(@RequestParam("openId")String openId){
        return service.deleteWeiXinUser(openId);
    }


    @GetMapping("/uid")
    public String getWeiXinUserByUserId(@RequestParam("userId")String userId){
        return service.getWeixinUserByUserId(userId);
    }

    @PostMapping("/save")
    public String saveWxUser(@RequestBody String jsonEntity){

        return service.saveWxUser(jsonEntity);
    }
    @GetMapping("/openid")
    public TSysWxUser getWxUser(@RequestParam("openId")String openId){

        return service.findById(openId);
    }

    @GetMapping("/userid")
    public TSysWxUser getWxByUserid(@RequestParam("userId")String userId){
        return service.findByUserId(userId);
    }

    @GetMapping("/cookie")
    public TSysWxUser getWxUserByCookie(@RequestParam("cookie")String cookie){

        return service.findByCookie(cookie);
    }

    @GetMapping("/cookie/info")
    public String getWxUserByCookieString(@RequestParam("cookie")String cookie){
        TSysWxUser tSysWxUser=service.findByCookie(cookie);
        JsonEntity<TSysWxUser> jsonEntity=new JsonEntity<>();
        jsonEntity.setData(tSysWxUser);
        return JsonUtils.toJson(jsonEntity);
    }

    @PostMapping("/saveWx")
    public TSysWxUser save(TSysWxUser sysWxUser){

        return service.save(sysWxUser);
    }
}
