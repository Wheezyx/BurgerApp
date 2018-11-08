package burgerapp;

import burgerapp.config.SecurityConfig;
import burgerapp.config.WebConfig;
import org.h2.tools.Server;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import java.sql.SQLException;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{
    
    @Override
    protected Class<?>[] getRootConfigClasses()
    {
        return new Class[]{SecurityConfig.class};
    }
    
    @Override
    protected Class<?>[] getServletConfigClasses()
    {
        try
        {
            Server.createWebServer().start();
            Server.createTcpServer().start();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return new Class[]{WebConfig.class};
    }
    
    @Override
    protected String[] getServletMappings()
    {
        return new String[]{"/"};
    }
    
    @Override
    protected Filter[] getServletFilters()
    {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return new Filter[]{filter};
    }
}
