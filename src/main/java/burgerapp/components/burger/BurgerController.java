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
    private static final String REDIRECT_MESSAGE = "rdrmessage";
    private static final String REDIRECT_MAINPAGE = "redirect:/";
    private BurgerService burgerService;
    
    @GetMapping("/burgers/{name}")
    public String getBurger(@PathVariable String name, Model model)
    {
        Optional<Burger> burger = burgerService.findByName(name.replaceAll("-", " "));
        burger.ifPresent(bur -> model.addAttribute("burger", bur));
        return burger.map(bur -> "burger").orElse(REDIRECT_MAINPAGE);
    }
    
    @PostMapping("/burgers/modify")
    public String updateBurger(@Valid @ModelAttribute Burger burger, BindingResult result, RedirectAttributes redirectAttributes)
    {
        if(result.hasErrors())
        {
            redirectAttributes.addFlashAttribute("rdr", "Niepoprawnie wypełnione pola, spróbuj ponownie.");
            return REDIRECT_MAINPAGE;
        }
        boolean status = burgerService.update(burger);
        if(status)
        {
            redirectAttributes.addFlashAttribute(REDIRECT_MESSAGE, "Successful added burger.");
        }
        else
        {
            redirectAttributes.addFlashAttribute(REDIRECT_MESSAGE, "Błąd przy zapisie do bazy, nazwa aktualnie zajęta!");
        }
        return REDIRECT_MAINPAGE;
    }
}
