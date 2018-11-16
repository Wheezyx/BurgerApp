package burgerapp.components.order;

import burgerapp.components.burger.Burger;
import burgerapp.components.burger.BurgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/panel")
public class PanelController
{
    private final OrderService orderService;
    private final BurgerService burgerService;
    
    @Autowired
    private PanelController(OrderService orderService, BurgerService burgerService)
    {
        this.orderService = orderService;
        this.burgerService = burgerService;
    }
    
    @GetMapping("/orders")
    public String showOrders(@RequestParam(required = false) OrderStatus status, Model model)
    {
        Optional<List<Order>> orders;
        if(status == null)
        {
            orders = orderService.getAll();
        }
        else
        {
            orders = orderService.findAllByOrderStatus(status);
        }
        orders.ifPresent(orders1 -> model.addAttribute("orders", orders1));
        return "orders";
    }
    
    @GetMapping
    public String showPanel()
    {
        return "panel";
    }
    
    @GetMapping("/order/{id}")
    public String showSingleOrder(@PathVariable Long id, Model model)
    {
        Optional<Order> order = orderService.get(id);
        return order.map(o -> panelUpdate(o, model))
                    .orElse("redirect:/");
    }
    
    @PostMapping("/order/{id}")
    public String changeOrderStatus(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes)
    {
        Optional<Order> order = orderService.get(id);
        order.ifPresent(o -> {
            o.setStatus(OrderStatus.nextStatus(o.getStatus()));
            orderService.update(o);
        });
        return order.map(o -> panelUpdate(o, model))
                    .orElse("redirect:/");
    }
    
    @GetMapping("/add-burger")
    public String addBurger(Model model)
    {
        model.addAttribute("formBurger", new Burger());
        return "add-burger";
    }
    
    private String panelUpdate(Order order, Model model)
    {
        model.addAttribute("order", order);
        model.addAttribute("fullAmount", order.getBurgers().stream().mapToDouble(Burger::getPrice).sum());
        return "panel-order";
    }
    
    @PostMapping("/add-burger")
    public String addBurger(@Valid @ModelAttribute("formBurger") Burger formBurger, BindingResult result, RedirectAttributes redirectAttributes)
    {
        if(result.hasErrors())
        {
            return "add-burger";
        }
        boolean status = burgerService.add(formBurger);
        if(status)
        {
            redirectAttributes.addFlashAttribute("rdrmessage", "Success, burger created, Feel free to add another.");
        }
        else
        {
            redirectAttributes.addFlashAttribute("rdrmessage", "Error with creating new burger: Name already used");
        }
        return "redirect:/panel/add-burger";
    }
    
}
