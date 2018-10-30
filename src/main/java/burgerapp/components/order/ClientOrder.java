package burgerapp.components.order;

import burgerapp.components.burger.Burger;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class ClientOrder
{
    private Order order = new Order();
    
    public ClientOrder()
    {
        clear();
    }
    
    public Order getOrder()
    {
        return order;
    }
    
    void addBurger(Burger burger)
    {
        order.getBurgers().add(burger);
    }
    
    public void clear()
    {
        order = new Order();
        order.setStatus(OrderStatus.NEW);
    }
}
