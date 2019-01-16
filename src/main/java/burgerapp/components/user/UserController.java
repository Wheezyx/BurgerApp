package burgerapp.components.user;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@Controller
@AllArgsConstructor
@RequestMapping("/panel/users")
public class UserController {

    private final UserService userService;

    private final static String ROLE = "USER";

    @GetMapping("/add")
    public String addUser(Model model) {
        //np tu.
        model.addAttribute("formUser", new User());
        return "add-user";

    }

    @PostMapping("/add")
    public String addUser(@Valid @ModelAttribute("formUser") User formUser, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "add-user";
        }

        boolean status = userService.addWithDefaultRole(formUser);
        if (status) {
            redirectAttributes.addFlashAttribute("rdrmessage", "Success, user added.");
        } else {
            redirectAttributes.addFlashAttribute("rdrmessage", "Error with creating new user: Name already used");
        }
        return "redirect:/panel/users/add";
    }

    @GetMapping
    public String findAllByRole(Model model) {
        Optional<List<User>> optionalUsers = userService.findWithDefaultRole(ROLE);
        optionalUsers.ifPresent(users -> model.addAttribute("users", users));
        return "users";
    }

    @GetMapping("/change-status/{id}")
    public String changeUserEnabled(@PathVariable String id) {
        boolean status = this.userService.changeUserEnabledById(id);
        return "redirect:/panel/users";
    }

}
