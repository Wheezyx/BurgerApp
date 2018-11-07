package burgerapp.components.order;

import burgerapp.components.burger.Burger;
import burgerapp.components.burger.BurgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String addBurgerToOrder(@RequestParam Long burgerId, RedirectAttributes redirectAttributes)
    {
        Optional<Burger> burger = burgerService.get(burgerId);
        burger.ifPresent(clientOrder::addBurger);
        if(burger.isPresent())
        {
            redirectAttributes.addFlashAttribute("rdrmessage", "Dodano" + burger.get().getName() + " do zamówienia");
        }
        else
        {
            redirectAttributes.addFlashAttribute("rdrmessage", "Nie dodano do zamówienia, spróbuj ponownie!");
        }
        return "redirect:/";
    }
    
    @GetMapping("/orders")
    public String getOrders()
    {
        return "client-orders";
    }
    
    @GetMapping("/order")
    public String getCurrentOrder(Model model)
    {
        model.addAttribute("order", clientOrder.getOrder());
        double orderPrice = clientOrder.getOrder()
                                       .getBurgers().stream()
                                       .mapToDouble(Burger::getPrice)
                                       .sum();
        model.addAttribute("fullAmount", orderPrice);
        return "order";
    }
    
    @PostMapping("/order/create")
    public String createOrder(@RequestParam String clientName, Model model)
    {
        Order order = clientOrder.getOrder();
        order.setClientName(clientName);
        orderService.add(order);
        clientOrder.clear();
        model.addAttribute("message", "Numer zamówienia:" + order.getId() + " Twój kod odbioru zamówienia to:" + order.getCode());
        return "message";
    }
}
