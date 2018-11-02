package burgerapp.components.order;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class PanelController
{
    private OrderService orderService;
    
    private PanelController(OrderService orderService)
    {
        this.orderService = orderService;
    }
    
    @GetMapping("/panel/orders")
    public String showOrders(@RequestParam(required = false) OrderStatus status, Model model){
        Optional<List<Order>> orders;
        if (status == null)
            orders = orderService.getAll();
        else
            orders = orderService.findAllByOrderStatus(status);
        model.addAttribute("orders", orders);
        return "orders";
    }
    @GetMapping("/panel")
    public String showPanel(){
        return "panel";
    }
}
