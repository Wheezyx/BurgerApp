package burgerapp.components.order;

import burgerapp.components.burger.Burger;
import burgerapp.components.generic.GenericDao;
import burgerapp.components.generic.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
    
    public Map<String, Long> getAllBurgersFromAllOrders()
    {
//        Optional<List<Order>> optionalList = orderDao.getAllWithBurgers();
//        if(optionalList.isPresent())
//        {
//            Map<String, Integer> map = new HashMap<>();
//            for(Order order : optionalList.get())
//            {
//                for(Burger burger : order.getBurgers())
//                {
//                    if(!map.containsKey(burger.getName()))
//                    {
//                        map.put(burger.getName(), 1);
//                    }
//                    else
//                    {
//                        map.put(burger.getName(), map.get(burger.getName() + 1));
//                    }
//                }
//            }
//            System.err.println(map);
//            return map;
//        }
//        return Collections.emptyMap();
        Optional<List<String>> optionalList = orderDao.getBurgersFromAllOrders();
        if(optionalList.isPresent())
        {
            return optionalList.get().stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        }
        return Collections.emptyMap();
    }
}
