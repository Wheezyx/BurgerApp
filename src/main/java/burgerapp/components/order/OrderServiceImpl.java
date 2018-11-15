package burgerapp.components.order;

import burgerapp.components.generic.GenericDao;
import burgerapp.components.generic.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class OrderServiceImpl extends GenericServiceImpl<Order, Long> implements OrderService
{
    private final OrderDao orderDao;
    
    public OrderServiceImpl(
        @Qualifier("orderDaoImpl") GenericDao<Order, Long> genericDao)
    {
        super(genericDao);
        this.orderDao = (OrderDao) genericDao;
    }
    
    @Override
    public boolean add(Order order)
    {
        try
        {
            String code = generateCode();
            order.setCode(code);
            orderDao.add(order);
            return true;
        }
        catch(ConstraintViolationException e)
        {
            Set<ConstraintViolation<?>> errors = e.getConstraintViolations();
            errors.forEach(err -> System.err.println(
                err.getPropertyPath() + " " +
                err.getInvalidValue() + " " +
                err.getMessage()));
            return false;
        }
    }
    
    private String generateCode()
    {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(0, 6);
    }
    
    @Override
    public Optional<List<Order>> findAllByOrderStatus(OrderStatus status)
    {
        return this.orderDao.findAllByOrderStatus(status);
    }
}
