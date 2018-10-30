package burgerapp.components.order;

import burgerapp.components.generic.GenericDao;
import burgerapp.components.generic.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderServiceImpl extends GenericServiceImpl<Order, Long> implements OrderService
{
    private OrderDao orderDao;
    
    public OrderServiceImpl(
        @Qualifier("orderDaoImpl") GenericDao<Order, Long> genericDao)
    {
        super(genericDao);
        this.orderDao = (OrderDao) genericDao;
    }
    
    @Override
    public Order add(Order order)
    {
        String code = generateCode();
        order.setCode(code);
        orderDao.add(order);
        return order;
    }
    
    private String generateCode()
    {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(0, 6);
    }
}
