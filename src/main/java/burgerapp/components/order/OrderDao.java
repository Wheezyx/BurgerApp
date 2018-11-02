package burgerapp.components.order;

import burgerapp.components.generic.GenericDao;

import java.util.List;
import java.util.Optional;

public interface OrderDao extends GenericDao<Order, Long>
{
    Optional<List<Order>> findAllByOrderStatus(OrderStatus status);
}
