package burgerapp;

import burgerapp.config.WebConfig;
import org.h2.tools.Server;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import java.sql.SQLException;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{
    
    @Override
    protected Class<?>[] getRootConfigClasses()
    {
        return new Class[0];
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
}
