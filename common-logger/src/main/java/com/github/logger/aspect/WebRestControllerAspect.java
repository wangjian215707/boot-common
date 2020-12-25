package com.github.logger.aspect;

import com.github.logger.entity.AspectEntity;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * rest api AOP日志管理
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/12/18
 */
@Aspect
@Component
@Slf4j
public class WebRestControllerAspect {

    /**
     * 定义切点
     */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RestController)")
    public void webLog(){
    }

    /**
     * restController 调用前执行
     * @param joinPoint
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint){
        AspectEntity aspectEntity=new AspectEntity();
        Signature signature = joinPoint.getSignature();
        //代理的是哪一个方法
        aspectEntity.setFunctionName(signature.getName());
        //AOP代理类的名字
        aspectEntity.setPackageName(signature.getDeclaringTypeName());
        //AOP代理类的类（class）信息
        signature.getDeclaringType();
        MethodSignature methodSignature = (MethodSignature) signature;
        String[] strings = methodSignature.getParameterNames();
        //参数名称
        aspectEntity.setParameter(Arrays.toString(strings));
        //获取目标方法的参数信息
        aspectEntity.setParameterValue(Arrays.toString(joinPoint.getArgs()));
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = attributes.getRequest();
        // 记录下请求内容
        aspectEntity.setRequestUrl(req.getRequestURL().toString());//请求地址
        aspectEntity.setRequestMethod(req.getMethod());//请求类型
        aspectEntity.setRequestClientIp(req.getRemoteAddr());//请求地址
        aspectEntity.setClassMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info(aspectEntity.toString());
    }

    /**
     * 处理完请求返回内容
     * @param ret
     * @throws Throwable
     */
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        System.out.println("方法的返回值 : " + ret);
    }

    /**
     * 后置异常通知
     * @param jp
     */
    @AfterThrowing("webLog()")
    public void throwss(JoinPoint jp){
        System.out.println("方法异常时执行.....");
    }

    /**
     * 后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
     * @param jp
     */
    @After("webLog()")
    public void after(JoinPoint jp){

    }

    /**
     * 环绕通知,环绕增强，相当于MethodInterceptor
     * @param pjp
     * @return
     */
    @Around("webLog()")
    public Object arround(ProceedingJoinPoint pjp) {
        try {
            Object o =  pjp.proceed();
            return o;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

}
