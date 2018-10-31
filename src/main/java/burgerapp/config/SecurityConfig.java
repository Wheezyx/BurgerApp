package burgerapp.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.inMemoryAuthentication().withUser("user").password("{noop}pass").roles("USER").and().withUser("admin").password("{noop}admin").roles("USER", "ADMIN");
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests().anyRequest().hasAnyRole("USER")
            .and()
            .authorizeRequests().antMatchers("/**").permitAll()
            .and()
            .formLogin()
            .and()
            .csrf().disable();
    }
}
