package burgerapp.components;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Component
@Aspect
@Log4j
@AllArgsConstructor
public class LogAspect
{
    @Pointcut("execution(* (@org.springframework.stereotype.Repository *).*(..))")
    public void repository()
    {
    }
    
    @Pointcut("execution(* (@org.springframework.stereotype.Controller *).*(..))")
    public void controller()
    {
    }
    
    @Around("(repository())")
    public Object daoMethodsLogger(ProceedingJoinPoint pjp) throws Throwable
    {
        long start = System.currentTimeMillis();
        log.info("Calling method: " + pjp.getSignature());
        Object output = pjp.proceed();
        log.info("Method execution completed.");
        long elapsedTime = System.currentTimeMillis() - start;
        log.info("Method execution time: " + elapsedTime + " milliseconds.");
        return output;
    }
    
    @Around("(controller())")
    public Object controllersLogger(ProceedingJoinPoint pjp) throws Throwable
    {
        HttpServletRequest request = ((ServletRequestAttributes)
            Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        
        log.info("Address: " + request.getRemoteAddr() + " performs HTTP " + request.getMethod() + " on " + request.getRequestURL() + " calling a method: " + pjp.getSignature());
        
        return pjp.proceed();
    }
    
}
