package burgerapp.components.order;

import burgerapp.components.burger.Burger;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
@Getter
public class ClientOrder
{
    private Order order;
    
    public ClientOrder()
    {
        clear();
    }
    
    void addBurger(Burger burger){
        order.getBurgers().add(burger);
    }
    
    private void clear()
    {
        order = new Order();
        order.setStatus(OrderStatus.IN_PROGRESS);
    }
}
