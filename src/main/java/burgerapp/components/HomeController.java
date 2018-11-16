package burgerapp.components;

import burgerapp.components.burger.Burger;
import burgerapp.components.burger.BurgerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class HomeController
{
    private BurgerService burgerService;
    
    @GetMapping("/")
    public String home(Model model)
    {
        Optional<List<Burger>> burgers = burgerService.getAll();
        burgers.ifPresent(burs -> model.addAttribute("burgers", burs));
        return "index2";
    }
    
    @GetMapping("/login")
    public String login()
    {
        return "login";
    }
}
