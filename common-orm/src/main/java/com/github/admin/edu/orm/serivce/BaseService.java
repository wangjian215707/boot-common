package com.github.admin.edu.orm.serivce;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-7-30
 */
public interface BaseService {

    String getAllGetEntity(String title, Integer current, Integer rows, String order, String fileId);

    String getAllEntity(String title, String order, String fileId);

    String saveOrUpdate(String jsonEntity);

    String delete(String ids);

    String getALLPostByPager(String jsonEntity);
}
