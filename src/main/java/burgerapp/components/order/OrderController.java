package burgerapp.components.order;

import burgerapp.components.burger.Burger;
import burgerapp.components.burger.BurgerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class OrderController
{
    private ClientOrder clientOrder;
    private BurgerRepository burgerRepository;
    
    private OrderController(ClientOrder clientOrder, BurgerRepository burgerRepository)
    {
        this.clientOrder = clientOrder;
        this.burgerRepository = burgerRepository;
    }
    
    @GetMapping("/zamowienie/dodaj")
    public String addBurgerToOrder(@RequestParam Long id, Model model){
        Optional<Burger> burger = burgerRepository.findOne(id);
        return null;
    }
}
