package burgerapp.components.order;

import burgerapp.components.generic.GenericDao;
import burgerapp.components.generic.GenericServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl extends GenericServiceImpl<Order, Long> implements OrderService {
    private final OrderDao orderDao;

    public OrderServiceImpl(
            @Qualifier("orderDaoImpl") GenericDao<Order, Long> genericDao) {
        super(genericDao);
        this.orderDao = (OrderDao) genericDao;
    }

    @Override
    public boolean add(Order order) {
        try {
            String code = generateCode();
            order.setCode(code);
            orderDao.add(order);
            log.info("Successful created and saved " + order.getClass().getSimpleName());
            return true;
        } catch (ConstraintViolationException | PersistenceException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    private String generateCode() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(0, 6);
    }

    @Override
    public Optional<List<Order>> findAllByOrderStatus(OrderStatus status) {
        return this.orderDao.findAllByOrderStatus(status);
    }

    public Map<String, Long> getAllBurgersFromAllOrders() {
        Optional<List<String>> optionalList = orderDao.getBurgersFromAllOrders();
        if (optionalList.isPresent()) {
            return optionalList.get()
                    .stream()
                    .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        }
        return Collections.emptyMap();
    }
}
