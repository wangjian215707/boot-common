package com.github.edu.mobile.interfice;

import com.github.edu.mobile.driver.Device;

import javax.servlet.http.HttpServletRequest;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-8-22
 */
public interface DeviceResolver {

    /**
     * Resolve the device that originated the web request.
     */
    Device resolveDevice(HttpServletRequest request);
}
