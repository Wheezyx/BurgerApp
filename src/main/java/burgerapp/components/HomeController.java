package burgerapp.components;

import burgerapp.components.burger.Burger;
import burgerapp.components.burger.BurgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController
{
    private BurgerService burgerService;
    
    @Autowired
    private HomeController(BurgerService burgerService)
    {
        this.burgerService = burgerService;
    }
    
    @GetMapping("/")
    public String home(Model model)
    {
        Optional<List<Burger>> burgers = burgerService.getAll();
        burgers.ifPresent(burs -> model.addAttribute("burgers", burs));
        return "index";
    }
}
