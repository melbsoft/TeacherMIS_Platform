package com.melbsoft.teacherplatform.config.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@MapperScan(value = "com.melbsoft.teacherplatform.dao.admin", sqlSessionFactoryRef = "adminSqlSessionFactory")
@Slf4j
public class AdminDBconfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "jdbc.admin")
    public DataSource datasourceAdmin() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "adminTransactionManager")
    public DataSourceTransactionManager transactionManager() throws SQLException {
        return new DataSourceTransactionManager(datasourceAdmin());
    }

    @Primary
    @Bean
    public SqlSessionFactory adminSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(datasourceAdmin());
        factoryBean.setVfs(SpringBootVFS.class);
        factoryBean.setTypeAliasesPackage("com.melbsoft.teacherplatform.model.admin");
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:**/dao/admin/*Mapper.xml"));
        return factoryBean.getObject();
    }
}


