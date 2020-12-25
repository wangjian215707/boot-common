package com.github.edu.common.quartz.service;

import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;
import java.util.Map;

/**
 * 动态任务管理
 */
public interface IQuartzServerService {

    /**
     * 增加一个job
     *
     * @param jobClass
     *            任务实现类
     * @param jobName
     *            任务名称
     * @param jobGroupName
     *            任务组名
     * @param jobTime
     *            时间表达式 (这是每隔多少秒为一次任务)
     * @param jobTimes
     *            运行的次数 （<0:表示不限次数）
     */
    void addJob(Class<? extends QuartzJobBean> jobClass, String jobName, String jobGroupName, int jobTime,
                int jobTimes);

    /**
     * 增加一个job
     *
     * @param jobClass
     *            任务实现类
     * @param jobName
     *            任务名称
     * @param jobGroupName
     *            任务组名
     * @param jobTime
     *            时间表达式 （如：0/5 * * * * ? ）
     */
    void addJob(Class<? extends QuartzJobBean> jobClass, String jobName, String jobGroupName, String jobTime);

    /**
     * 修改 一个job的 时间表达式
     *
     * @param jobName
     *         任务名称
     * @param jobGroupName
     *         任务组名
     * @param jobTime
     *    时间表达式 （如：0/5 * * * * ? ）
     */
    void updateJob(String jobName, String jobGroupName, String jobTime);

    /**
     * 删除任务一个job
     *
     * @param jobName
     *            任务名称
     * @param jobGroupName
     *            任务组名
     */
    void deleteJob(String jobName, String jobGroupName);

    /**
     * 暂停一个job
     * @param jobName
     *            任务名称
     * @param jobGroupName
     *            任务组名
     */
    void pauseJob(String jobName, String jobGroupName);

    /**
     * 恢复一个job
     *
     * @param jobName
     *            任务名称
     * @param jobGroupName
     *            任务组名
     */
     void resumeJob(String jobName, String jobGroupName);

    /**
     * 立即执行一个job
     *
     * @param jobName
     *            任务名称
     * @param jobGroupName
     *            任务组名
     */
     void runAJobNow(String jobName, String jobGroupName);

    /**
     * 获取所有计划中的任务列表
     *
     * @return
     */
     List<Map<String, Object>> queryAllJob();

    /**
     * 获取所有正在运行的job
     *
     * @return
     */
     List<Map<String, Object>> queryRunJob();
}
