package burgerapp.components.burger;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
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
    public String updateBurger(@Valid @ModelAttribute Burger burger, BindingResult result, RedirectAttributes redirectAttributes)
    {
        if(result.hasErrors())
        {
            redirectAttributes.addFlashAttribute("rdr", "Niepoprawnie wypełnione pola, spróbuj ponownie.");
            return "redirect:/";
        }
        boolean status = burgerService.update(burger);
        if(status)
        {
            redirectAttributes.addFlashAttribute("rdrmessage", "Successful added burger.");
        }
        else
        {
            redirectAttributes.addFlashAttribute("rdrmessage", "Błąd przy zapisie do bazy, spróbuj ponownie");
        }
        return "redirect:/";
    }
}
