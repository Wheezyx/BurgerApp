package burgerapp.components;

import burgerapp.components.burger.Burger;
import burgerapp.components.burger.BurgerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController
{
    private BurgerRepository burgerRepository;
    
    public HomeController(BurgerRepository burgerRepository)
    {
        this.burgerRepository = burgerRepository;
        burgerRepository.setClazz(Burger.class);
    }
    
    @GetMapping("/")
    public String home(Model model)
    {
        List<Burger> burgers = burgerRepository.findAll();
        model.addAttribute("burgers", burgers);
        return "index";
    }
}
