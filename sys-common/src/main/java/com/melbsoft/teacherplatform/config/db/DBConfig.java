package com.melbsoft.teacherplatform.config.db;

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
@MapperScan(value = "com.melbsoft.teacherplatform.dao", sqlSessionFactoryRef = "dbSqlSessionFactory")
@Slf4j
public class DBConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "jdbc")
    public DataSource datasourceDB() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dbTransactionManager")
    public DataSourceTransactionManager transactionManager() throws SQLException {
        return new DataSourceTransactionManager(datasourceDB());
    }

    @Primary
    @Bean
    public SqlSessionFactory dbSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(datasourceDB());
        factoryBean.setVfs(SpringBootVFS.class);
        factoryBean.setTypeAliasesPackage("com.melbsoft.teacherplatform.model.**");
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/melbsoft/teacherplatform/dao/**/*Mapper.xml"));
        return factoryBean.getObject();
    }
}


