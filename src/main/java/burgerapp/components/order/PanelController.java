package burgerapp.components.order;

import burgerapp.components.burger.Burger;
import burgerapp.components.burger.BurgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/panel")
public class PanelController
{
    private OrderService orderService;
    private BurgerService burgerService;
    
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
    
    @GetMapping("/add-burger")
    public String addBurger(Model model)
    {
        model.addAttribute("formBurger", new Burger());
        return "add-burger";
    }
    
    @PostMapping("/add-burger")
    public String addBurger(@ModelAttribute Burger formBurger, BindingResult result, RedirectAttributes redirectAttributes)
    {
        if(result.hasErrors())
        {
            redirectAttributes.addFlashAttribute("rdrmessage", "Failed adding, errors: " + result.getAllErrors());
        }
        else
        {
            burgerService.add(formBurger);
            redirectAttributes.addFlashAttribute("rdrmessage", "Success, burger created, Feel free to add another.");
        }
        return "redirect:/panel/add-burger";
    }
    
}
