package com.baizhi.gxy.aspect;

import com.baizhi.gxy.annotation.AddLog;
import com.baizhi.gxy.dao.LogMapper;
import com.baizhi.gxy.entity.Admin;
import com.baizhi.gxy.entity.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Aspect
@Configuration
public class LogAspect {

    @Resource
    LogMapper logMapper;

    @Resource
    HttpSession session;

    @Around("@annotation(com.baizhi.gxy.annotation.AddLog)")
    public Object addLogs(ProceedingJoinPoint joinPoint) {

        //谁   时间   操作   是否成功
        Admin admin = (Admin) session.getAttribute("admin");

        //时间
        /*Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String formats = format.format(date);*/

        //操作   哪个方法
        String methodName = joinPoint.getSignature().getName();

        //获取方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        //获取方法上的注解
        AddLog addLog = method.getAnnotation(AddLog.class);

        //获取注解中属性的值   value
        String methodDes = addLog.value();


        //放行方法
        try {
            Object proceed = joinPoint.proceed();
            String message = "success";
            //System.out.println("管理员："+admin.getUsername()+"--时间："+new Date()+"--操作："+methodName+"--状态："+message);
            Log log = new Log(UUID.randomUUID().toString(), admin.getUsername(), new Date(), methodDes + "(" + methodName + ")", message);
            logMapper.insert(log);
            System.out.println("数据库插入" + log);

            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            String message = "error";
            return null;
        }
    }

    //@Around("execution(* com.baizhi.zcn.serviceImpl.*.*(..) ) && !execution(* com.baizhi.zcn.serviceImpl.*.query*(..))")
    public Object addLog(ProceedingJoinPoint joinPoint) {

        //谁   时间   操作   是否成功
        Admin admin = (Admin) session.getAttribute("admin");

        //时间
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String formats = format.format(date);

        //操作   哪个方法
        String methodName = joinPoint.getSignature().getName();

        //放行方法
        try {
            Object proceed = joinPoint.proceed();

            String message = "success";

            System.out.println("管理员：" + admin + "--时间：" + formats + "--操作：" + methodName + "--状态：" + message);

            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            String message = "error";
            return null;
        }
    }

}
