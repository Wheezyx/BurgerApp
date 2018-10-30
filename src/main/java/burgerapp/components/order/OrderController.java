package burgerapp.components.order;

import burgerapp.components.burger.Burger;
import burgerapp.components.burger.BurgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class OrderController
{
    private ClientOrder clientOrder;
    private BurgerService burgerService;
    private OrderService orderService;
    
    @Autowired
    private OrderController(ClientOrder clientOrder, BurgerService burgerService, OrderService orderService)
    {
        this.clientOrder = clientOrder;
        this.burgerService = burgerService;
        this.orderService = orderService;
    }
    
    @GetMapping("/order/add")
    public String addBurgerToOrder(@RequestParam Long burgerId, Model model)
    {
        Optional<Burger> burger = burgerService.get(burgerId);
        burger.ifPresent(clientOrder::addBurger);
        if(burger.isPresent())
        {
            model.addAttribute("message", "Dodano");
        }
        else
        {
            model.addAttribute("message", "Nie Dodano");
        }
        return "message";
    }
    
    @GetMapping("/order")
    public String getCurrentOrder(Model model)
    {
        model.addAttribute("order", clientOrder.getOrder());
        model.addAttribute("fullAmount", clientOrder
            .getOrder()
            .getBurgers().stream()
            .mapToDouble(Burger::getPrice)
            .sum());
        return "order";
    }
    
    @PostMapping("/order/create")
    public String createOrder(@RequestParam String clientName, Model model)
    {
        Order order = clientOrder.getOrder();
        order.setClientName(clientName);
        orderService.add(order);
        clientOrder.clear();
        model.addAttribute("message", "Twój kod odbioru zamówienia to:" + order.getCode());
        return "message";
    }
}
