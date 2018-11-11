package burgerapp.components.order;

import burgerapp.components.generic.GenericDaoImpl;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDaoImpl extends GenericDaoImpl<Order, Long> implements OrderDao
{
    
    @Override
    public Optional<List<Order>> findAllByOrderStatus(OrderStatus orderStatus)
    {
        TypedQuery<Order> query = this.entityManager.createQuery("SELECT o FROM Order o WHERE o.status LIKE :orderStatus", Order.class);
        List<Order> list = query.setParameter("orderStatus", orderStatus).getResultList();
        return !list.isEmpty() ? Optional.of(list) : Optional.empty();
    }
    
    @Override
    public Optional<Order> find(Long key)
    {
        Optional<Order> order = Optional.ofNullable(this.entityManager.find(Order.class, key));
        order.ifPresent(o -> Hibernate.initialize(o.getBurgers()));
        return order;
    }
}
