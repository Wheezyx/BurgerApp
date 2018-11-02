package burgerapp.components.order;

import burgerapp.components.generic.GenericDaoImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDaoImpl extends GenericDaoImpl<Order, Long> implements OrderDao
{
    
    @Override
    public Optional<List<Order>> findAllByOrderStatus(OrderStatus status)
    {
        TypedQuery<Order> query = this.entityManager.createQuery("SELECT o FROM Order o WHERE o.orderstatus = :status", Order.class);
        List<Order> list = query.setParameter("status", status).getResultList();
        return list.size() > 0 ? Optional.of(list) : Optional.empty();
    }
}
