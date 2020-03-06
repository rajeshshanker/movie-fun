package org.superbiz.moviefun;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class AppConfig {

    @Bean
    public DatabaseServiceCredentials databaseServiceCredentials(@Value("${VCAP_SERVICES}")  String vCapsServices){
        return new DatabaseServiceCredentials(vCapsServices);
    }
    @Bean
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapter(){
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.MYSQL);
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
        return  vendorAdapter;
    }


}
