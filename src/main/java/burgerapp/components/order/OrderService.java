package burgerapp.components.order;

import burgerapp.components.generic.GenericService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderService extends GenericService<Order, Long>
{
    Optional<List<Order>> findAllByOrderStatus(OrderStatus status);
    
    Map<String, Long> getAllBurgersFromAllOrders();
    
    Optional<List<Order>> findAllWithoutStatus(OrderStatus orderStatus);
}
