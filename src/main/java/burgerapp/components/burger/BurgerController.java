package burgerapp.components.burger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class BurgerController
{
    private BurgerRepository burgerRepository;
    
    public BurgerController(BurgerRepository burgerRepository)
    {
        this.burgerRepository = burgerRepository;
    }
    
    @GetMapping("/danie/{name}")
    public String getBurger(@PathVariable String name, Model model)
    {
        Optional<Burger> burger = burgerRepository.findByName(name.replaceAll("-", " "));
        burger.ifPresent(bur-> model.addAttribute("burger", bur));
        return burger.map(bur -> "burger").orElse("redirect:/");
    }
    
}
