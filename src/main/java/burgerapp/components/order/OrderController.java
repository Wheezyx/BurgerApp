package burgerapp.components.order;

import burgerapp.components.burger.Burger;
import burgerapp.components.burger.BurgerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Iterator;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class OrderController
{
    private ClientOrder clientOrder;
    private BurgerService burgerService;
    private OrderService orderService;
    
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
    public String createOrder(@RequestParam String clientName,RedirectAttributes redirectAttributes)
    {
        
        Order order = clientOrder.getOrder();
        order.setClientName(clientName);
        boolean status = orderService.add(order);
        if (status){
            clientOrder.clear();
            redirectAttributes.addFlashAttribute("finalizeOrder", "Numer zamówienia:" + order.getId() +
                                                                  " Twój kod odbioru zamówienia to:" + order.getCode());
        } else{
            redirectAttributes.addFlashAttribute("rdrmessage", "Nie można utworzyć zamówienia, spróbuj za chwilę!");
        }
        return "redirect:/order";
    }
    
    @GetMapping("/order/clear")
    public String clearOrder(@RequestParam(value = "name", required = false) String name, RedirectAttributes redirectAttributes)
    {
        final String message;
        if(name != null)
        {
            for(Iterator<Burger> i = clientOrder.getOrder().getBurgers().iterator(); i.hasNext(); )
            {
                Burger burger = i.next();
                if(burger.getName().equals(name))
                {
                    i.remove();
                    break;
                }
            }
            message = "Usunięto " + name;
        }
        else if(clientOrder.getOrder().getBurgers().isEmpty())
        {
            message = "Zamówienie jest puste!";
        }
        else
        {
            clientOrder.clear();
            message = "Zamówienie wyczyszczone";
        }
        redirectAttributes.addFlashAttribute("rdrmessage", message);
        return name == null ? "redirect:/" : "redirect:/order";
    }
}
