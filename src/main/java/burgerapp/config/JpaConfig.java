package burgerapp.config;

import org.apache.commons.dbcp2.BasicDataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JpaConfig
{
    @Bean
    public LocalContainerEntityManagerFactoryBean createEMF(JpaVendorAdapter adapter, DataSource ds)
    {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        Map<String, String> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("spring.jpa.database-platform","org.hibernate.dialect.H2Dialect");
        emf.setPersistenceUnitName("spring-jpa-pu");
        emf.setJpaPropertyMap(properties);
        emf.setDataSource(ds);
        emf.setJpaVendorAdapter(adapter);
        emf.setPackagesToScan("burgerapp");
        return emf;
    }
    
    @Bean
    public JpaVendorAdapter createVendorAdapter()
    {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(true);
        adapter.setDatabase(Database.H2);
        return adapter;
    }
    
    @Bean
    public DataSource createDS()
    {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl("jdbc:h2:tcp://localhost:9092/~/test");
        ds.setUsername("sa");
        ds.setPassword("");
        ds.setDriverClassName("org.h2.Driver");
        ds.setInitialSize(5);
        return ds;
    }
    
    @Bean
    public PlatformTransactionManager createTransactionManager(EntityManagerFactory emf)
    {
        return new JpaTransactionManager(emf);
    }
}
