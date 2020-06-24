package com.spring.boot.datasource;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 业务代理类
 * 该类为在调用真实业务类方法之前进行数据源的切换
 */
@Aspect  // 使之成为切面类
@Component // 把切面类加入到IOC容器中
public class DataSourceAop {

    @Before("execution( * com.spring.boot.service.test.*.*(..))")
    public void setTestDataSource(){
        System.err.println("切换为 test 数据源");
        DataSourceType.setDataBaseType(DataBaseType.TEST);
    }

    @Before("execution( * com.spring.boot.service.goods.*.*(..))")
    public void setGoodsDataSource(){
        System.err.println("切换为 goods 数据源");
        DataSourceType.setDataBaseType(DataBaseType.GOODS);
    }

}
