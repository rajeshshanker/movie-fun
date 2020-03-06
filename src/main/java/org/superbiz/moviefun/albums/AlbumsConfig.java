package org.superbiz.moviefun.albums;

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
public class AlbumsConfig {
    @Bean
    public DataSource albumsDataSource(DatabaseServiceCredentials serviceCredentials) {
       // MysqlDataSource dataSource = new MysqlDataSource();
        HikariConfig config = new HikariConfig();
        //dataSource.setURL(serviceCredentials.jdbcUrl("albums-mysql"));
        config.setJdbcUrl(serviceCredentials.jdbcUrl("albums-mysql"));
        HikariDataSource hikariDataSource = new HikariDataSource(config);
        return hikariDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean albumsContainerEntityManagerFactoryBean(DataSource albumsDataSource, HibernateJpaVendorAdapter vendorAdapter){

        LocalContainerEntityManagerFactoryBean lceManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        lceManagerFactoryBean.setDataSource(albumsDataSource);
        lceManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        lceManagerFactoryBean.setPackagesToScan(this.getClass().getPackage().getName());
        lceManagerFactoryBean.setPersistenceUnitName("albums");
        return lceManagerFactoryBean;
    }
    @Bean
    public PlatformTransactionManager albumsPlatformTransactionManager(EntityManagerFactory albumsContainerEntityManagerFactoryBean) {
        return  new JpaTransactionManager(albumsContainerEntityManagerFactoryBean);
    }


}
