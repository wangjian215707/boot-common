package com.github.edu.client.common.controller;

import com.github.admin.edu.assembly.common.entity.JsonArray;
import com.github.admin.edu.assembly.common.util.ObjectUtil;
import com.github.admin.edu.assembly.date.util.DateFormatUtils;
import com.github.admin.edu.assembly.page.entity.Pager;
import com.github.admin.edu.assembly.string.util.StringUtils;
import com.github.edu.mobile.driver.Device;
import com.github.edu.mobile.interfice.impl.LiteDeviceResolver;
import com.github.edu.client.common.component.BaseContextHandler;
import com.github.edu.client.common.component.SystemInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-7-25
 */
public abstract class BaseController<T,ID> extends BaseContextHandler  {

    @Autowired
    private SystemInformation systemInformation;

    @Value("${server.custom.system.pager.rows}")
    private int rows;

    public String getCurrentUserName(){
        return BaseContextHandler.getUsername();
    }

    public String getCurrentUserId(){
        return BaseContextHandler.getUserID();
    }

    public String getCurrentToken(){
        return BaseContextHandler.getToken();
    }

    @ModelAttribute("webConfig")
    private SystemInformation getWebInformation(){
        return systemInformation;
    }
    /**
     * form表单提交 Date类型数据绑定
     * <功能详细描述>
     * @param binder
     * @see [类、类#方法、类#成员]
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateFormat dateFormat = DateFormatUtils.getDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @Autowired
    private LiteDeviceResolver resolver;

    public String index(HttpServletRequest request, String url){
        Device device=resolver.resolveDevice(request);
        if(device.isMobile()&& StringUtils.isNotBlank(url)){
            url=url.replaceFirst("index/","");
            return "mobile/"+url;
        }
        return url;
    }

    public boolean mobile(HttpServletRequest request){
        Device device=resolver.resolveDevice(request);
        if(device.isMobile()){
            return true;
        }
        return false;
    }

    /**
     * List 页面
     * @param modelMap
     * @param t 对象
     * @param path //
     * @param tableHeader//表格对象头信息
     * @return
     */
    public String index(ModelMap modelMap, T t, HttpServletRequest request, JsonArray<T> jsonArray, String path, String title, String... tableHeader) {
        List<String> headers=new ArrayList<>();
        List<String> keys=new ArrayList<>();
        if(null!=tableHeader){
            for (String s:tableHeader){
                String[] strings=s.split(":");
                headers.add(strings[0]);
                keys.add(strings[1]);
            }
        }
        List<T> list=null;
        Pager pager=null;
        if(null!=jsonArray){
            list=jsonArray.getDataList();
            pager=jsonArray.getPager();
        }
        List<List<Object>> listList=new ArrayList<>();
        if(null!=list&&list.size()>0){
            for (T t1:list){
                listList.add(ObjectUtil.getListObject(t1,keys));
            }
        }
        modelMap.put("listEntity",listList);
        modelMap.put("HeaderList",headers);
        modelMap.put("title",title);
        modelMap.put("url","/uc/manager/"+path+"/all");
        if(null==pager){
            pager=new Pager();
            pager.setCurrent(1);
            pager.setRows(rows);
        }
        modelMap.put("page",pager);
        modelMap.put("entity",t);

        return index(request,"index/"+path+"/index") ;
    }
    public Pager getPager( Pager pager){
        if(null==pager.getRows()){
            pager.setRows(rows);
            pager.setCurrent(1);
        }
        return pager;
    }

    /**
     * 新增页面初始化
     * @param modelMap
     * @param t
     * @param path //路径地址
     */
    public String newIndex(ModelMap modelMap,HttpServletRequest request,T t,String path,String title){
        modelMap.put("entity",t);
        modelMap.put("button",true);
        modelMap.put("msg",null);//界面返回值
        modelMap.put("url","/uc/manager/"+path+"/save");
        modelMap.put("title",title);
        return index(request,"index/"+path+"/entity");
    }
    /**
     * 保存后，返回值设置
     * @param modelMap
     * @param t
     * @param path //路径地址
     * @param msg
     */
    public String saveObject(ModelMap modelMap,HttpServletRequest request,T t,String msg,String path,String title){
        modelMap.put("entity",t);
        modelMap.put("button",false);
        modelMap.put("msg",msg);
        modelMap.put("title",title);
        modelMap.put("url","/uc/manager/"+path+"/save");
        return index(request,"index/"+path+"/entity");
    }
    /**
     * 查看页面
     * @param modelMap
     * @param t
     * @param path //路径地址
     */
    public String openEntity(ModelMap modelMap,HttpServletRequest request,T t,String path,String title){
        modelMap.put("entity",t);
        modelMap.put("button",false);
        modelMap.put("msg",null);
        modelMap.put("title",title);
        modelMap.put("url","/uc/manager/"+path+"/save");
        return index(request,"index/"+path+"/entity");
    }

}
