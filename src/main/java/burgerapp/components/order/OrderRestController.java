package burgerapp.components.order;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class OrderRestController
{
    private OrderService orderService;
    
    @GetMapping("/api/orders")
    public List<Order> getOrderList()
    {
        Optional<List<Order>> optionalList = this.orderService.getAll();
    
        return optionalList.orElse(Collections.emptyList());
    }
}
