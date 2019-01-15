package burgerapp.components.user;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("/panel/users")
public class UserController {

    private final UserService userService;


    @GetMapping("/add")
    public String addUser(Model model) {
        model.addAttribute("formUser", new User());
        return "add-user";

    }

    @PostMapping("/add")
    public String addUser(@Valid @ModelAttribute("formUser") User formUser, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "add-user";
        }
//TODO CHANGE rdrmessage
        boolean status = userService.addWithDefaultRole(formUser);
        if (status) {
            redirectAttributes.addFlashAttribute("rdrmessage", "Success, burger created, Feel free to add another.");
        } else {
            redirectAttributes.addFlashAttribute("rdrmessage", "Error with creating new burger: Name already used");
        }
        return "redirect:/panel/users/add";
    }


}
