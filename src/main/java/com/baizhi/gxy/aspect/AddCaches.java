package com.baizhi.gxy.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.util.Set;

@Configuration
@Aspect
public class AddCaches {

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    //@Around("@annotation(com.baizhi.gxy.annotation.AddCache)")
    public Object addCahe(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        System.out.println("环绕通知");

        //序列化解决乱码
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        StringBuilder sb = new StringBuilder();

        //key,value   类型
        //key 类的全限定名+方法名+参数名（实参）
        //value :缓存的数据  String

        //KEY  Hash<Key,value>
        // 类
        //    1方法   数据
        //    2方法   数据

        //获取类的全限定名
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        sb.append(className);
        //获取方法名
        String methodName = proceedingJoinPoint.getSignature().getName();
        sb.append(methodName);
        //获取参数
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            sb.append(arg);
        }

        //拼接key
        String key = sb.toString();

        //取出key
        Boolean aBoolean = stringRedisTemplate.hasKey(key);

        ValueOperations valueOperations = redisTemplate.opsForValue();

        Object result = null;

        //去Redis判断key是否存在
        if (aBoolean) {
            //存在  缓存中有数据  取出数据返回结果
            result = valueOperations.get(key);
        } else {
            //不存在   缓存中没有   放行方法得到结果
            result = proceedingJoinPoint.proceed();

            //拿到返回结果  加入缓存
            valueOperations.set(key, result);
        }
        return result;
    }

    //@After("@annotation(com.baizhi.gxy.annotation.DelCahe)")
    public void delCache(JoinPoint joinPoint) {

        //清空缓存
        //com.baizhi.zcn.serviceImpl.UserServiceImpl     queryByPage110

        //获取类的全限定名
        String className = joinPoint.getTarget().getClass().getName();

        //获取所有的key
        Set<String> keys = stringRedisTemplate.keys("*");

        for (String key : keys) {
            //判断key是以className 全部删掉
            if (key.contains(className)) {
                //删除key
                stringRedisTemplate.delete(key);
            }
        }
    }
}
