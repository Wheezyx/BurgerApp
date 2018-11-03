package burgerapp.components.order;

import burgerapp.components.burger.Burger;
import burgerapp.components.burger.BurgerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class PanelController
{
    private OrderService orderService;
    private BurgerService burgerService;
    
    private PanelController(OrderService orderService, BurgerService burgerService)
    {
        this.orderService = orderService;
        this.burgerService = burgerService;
    }
    
    @GetMapping("/panel/orders")
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
        model.addAttribute("orders", orders);
        return "orders";
    }
    
    @GetMapping("/panel")
    public String showPanel()
    {
        return "panel";
    }
    
    @GetMapping("/panel/add-burger")
    public String addBurger(Model model)
    {
        model.addAttribute("formBurger", new Burger());
        return "add-burger";
    }
    
    @PostMapping("/panel/add-burger")
    public String addBurger(@Valid @ModelAttribute Burger formBurger, BindingResult result, RedirectAttributes redirectAttributes)
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
