package com.github.admin.edu.orm.configuration;

import com.github.admin.edu.assembly.string.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 动态数据源注册
 * Created by 王建 on 2017/6/22.
 */
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private static final Logger logger= LoggerFactory.getLogger(DynamicDataSourceRegister.class);
    //如配置文件中未指定数据源类型，使用该默认值

    @Value("${spring.datasource.druid.type}")
    private static Object DATASOURCE_TYPE_DEFAULT ;

    // 默认数据源
    private DataSource defaultDataSource;

    private Map<String, DataSource> customDataSources = new HashMap<String, DataSource>();

    /**
     * 加载多数据源配置
     */

    @Override
    public void setEnvironment(Environment environment) {
        logger.info("Begin init more DataSource !!! ");
        initDefaultDataSource(environment);
        initCustomDataSources(environment);
    }

    /**
     * 加载主数据源配置.
     * @param env
     */

    private void initDefaultDataSource(Environment env){
        // 读取主数据源
        Map<String, Object> dsMap = new HashMap<String, Object>();
        dsMap.put("type", env.getProperty("spring.datasource.druid.type"));
        dsMap.put("driver-class-name", env.getProperty("spring.datasource.druid.driver-class-name"));
        dsMap.put("url", env.getProperty("spring.datasource.druid.url"));
        dsMap.put("username", env.getProperty("spring.datasource.druid.username"));
        dsMap.put("password", env.getProperty("spring.datasource.druid.password"));
        //创建数据源;
        defaultDataSource = buildDataSource(dsMap);
    }

    /**
     * 初始化更多数据源
     */
    private void initCustomDataSources(Environment env) {
        logger.info("init custom datasource !! ");
        // 读取配置文件获取更多数据源，也可以通过defaultDataSource读取数据库获取更多数据源
        String dsPrefixs = env.getProperty("spring.dynamic.names");
        if(StringUtils.isNotBlank(dsPrefixs)){
            for (String dsPrefix : dsPrefixs.split(",")) {// 多个数据源
                Map<String, Object> dsMap = new HashMap<>();
                dsMap.put("driver-class-name",env.getProperty("spring.dynamic."+dsPrefix+".driver-class-name"));
                dsMap.put("url",env.getProperty("spring.dynamic."+dsPrefix+".url"));
                dsMap.put("username",env.getProperty("spring.dynamic."+dsPrefix+".data-username"));
                dsMap.put("password",env.getProperty("spring.dynamic."+dsPrefix+".data-password"));
                dsMap.put("type",env.getProperty("spring.datasource.druid.type"));
                DataSource ds = buildDataSource(dsMap);
                customDataSources.put(dsPrefix, ds);
            }
        }
    }

    /**
     * 创建datasource.
     * @param dsMap
     * @return
     */
    @SuppressWarnings("unchecked")
    public DataSource buildDataSource(Map<String, Object> dsMap) {
        Object type = dsMap.get("type");
        if (type == null){
            type = DATASOURCE_TYPE_DEFAULT;// 默认DataSource
        }
        Class<? extends DataSource> dataSourceType;
        try {
            dataSourceType = (Class<? extends DataSource>) Class.forName((String) type);
            String driverClassName = dsMap.get("driver-class-name").toString();
            String url = dsMap.get("url").toString();
            String username = dsMap.get("username").toString();
            String password = dsMap.get("password").toString();
            DataSourceBuilder factory =  DataSourceBuilder.create().driverClassName(driverClassName).url(url).username(username).password(password).type(dataSourceType);
            return factory.build();
        } catch (ClassNotFoundException e) {
            logger.error(" not find datasource type :"+e.getLocalizedMessage());
        }
        return null;
    }


    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        logger.info("DynamicDataSourceRegister.registerBeanDefinitions()");
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        // 将主数据源添加到更多数据源中
        targetDataSources.put("dataSource", defaultDataSource);
        DynamicDataSourceContextHolder.dataSourceIds.add("dataSource");
        // 添加更多数据源
        targetDataSources.putAll(customDataSources);
        for (String key : customDataSources.keySet()) {
            DynamicDataSourceContextHolder.dataSourceIds.add(key);
        }
        // 创建DynamicDataSource
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DynamicDataSource.class);
        beanDefinition.setSynthetic(true);
        MutablePropertyValues mpv = beanDefinition.getPropertyValues();
        //添加属性：AbstractRoutingDataSource.defaultTargetDataSource
        mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);
        mpv.addPropertyValue("targetDataSources", targetDataSources);
        registry.registerBeanDefinition("dataSource", beanDefinition);
    }
}
