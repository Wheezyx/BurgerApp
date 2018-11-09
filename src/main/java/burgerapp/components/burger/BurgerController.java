package burgerapp.components.burger;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class BurgerController
{
    private BurgerService burgerService;
    
    @GetMapping("/burgers/{name}")
    public String getBurger(@PathVariable String name, Model model)
    {
        Optional<Burger> burger = burgerService.findByName(name.replaceAll("-", " "));
        burger.ifPresent(bur -> model.addAttribute("burger", bur));
        return burger.map(bur -> "burger").orElse("redirect:/");
    }
    
    @PostMapping("/burgers/modify")
    public String updateBurger(@ModelAttribute Burger burger, RedirectAttributes redirectAttributes)
    {
        try
        {
            burgerService.update(burger);
            redirectAttributes.addFlashAttribute("rdrmessage", "Successful eddited burger.");
        }
        catch(RuntimeException e)
        {
            redirectAttributes.addFlashAttribute("rdrmessage", "Cannot modify: " + e.getMessage());
        }
        return "redirect:/burgers/" + burger.getName().replace(' ', '-');
    }
}
