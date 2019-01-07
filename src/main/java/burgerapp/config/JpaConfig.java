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
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
public class JpaConfig
{
    //TODO EXPORT HIBERNATE CREDITS TO YML FILE, ADD PRODUCTION AND DEVELOPMENT PROFILES
    @Bean
    public LocalContainerEntityManagerFactoryBean createEMF(JpaVendorAdapter adapter, DataSource ds)
    {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        Map<String, String> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("spring.jpa.database-platform", "org.hibernate.dialect.H2Dialect");
        //properties.put("spring.jpa.database-platform", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.connection.CharSet", "utf-8");
        properties.put("hibernate.connection.characterEncoding", "utf-8");
        properties.put("hibernate.connection.useUnicode", "true");
        emf.setJpaPropertyMap(properties);
        emf.setDataSource(ds);
        emf.setJpaVendorAdapter(adapter);
        emf.setPackagesToScan("burgerapp.components");
        return emf;
    }
    
    @Bean
    public JpaVendorAdapter createVendorAdapter()
    {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(true);
        //adapter.setDatabase(Database.MYSQL);
        adapter.setDatabase(Database.H2);
        return adapter;
    }
    
    @Bean
    public DataSource dataSource()
    {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl("jdbc:h2:tcp://localhost:9092/~/test");
        //ds.setUrl("jdbc:mysql://docker-mysql:3306/burgerapp");
        ds.setUsername("sa");
        ds.setPassword("");
        //ds.setDriverClassName("com.mysql.jdbc.Driver");
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
