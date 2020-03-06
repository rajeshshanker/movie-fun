package org.superbiz.moviefun.movies;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.superbiz.moviefun.DatabaseServiceCredentials;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
public class MoviesConfig {
    @Bean
    public DataSource moviesDataSource(DatabaseServiceCredentials serviceCredentials) {
       // MysqlDataSource dataSource = new MysqlDataSource();
        HikariConfig config = new HikariConfig();
        //dataSource.setURL(serviceCredentials.jdbcUrl("movies-mysql"));
        config.setJdbcUrl(serviceCredentials.jdbcUrl("movies-mysql"));
        return new HikariDataSource(config);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean moviesContainerEntityManagerFactoryBean(DataSource moviesDataSource, HibernateJpaVendorAdapter vendorAdapter){

        LocalContainerEntityManagerFactoryBean lceManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        lceManagerFactoryBean.setDataSource(moviesDataSource);
        lceManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        lceManagerFactoryBean.setPackagesToScan(this.getClass().getPackage().getName());
        lceManagerFactoryBean.setPersistenceUnitName("movies");
        return lceManagerFactoryBean;
    }
    @Bean
    public PlatformTransactionManager moviesPlatformTransactionManager(EntityManagerFactory moviesContainerEntityManagerFactoryBean) {
        return  new JpaTransactionManager(moviesContainerEntityManagerFactoryBean);
    }
}
