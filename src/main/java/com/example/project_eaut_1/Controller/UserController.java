package com.example.project_eaut_1.Controller;


import com.example.project_eaut_1.DTO.UsersDTO;
import com.example.project_eaut_1.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public String showIndex() {
        return "Index";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new UsersDTO());
        return "User/Login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") UsersDTO userDTO, Model model) {
        if (userService.authenticate(userDTO.getUsername(), userDTO.getPassword())) {
            String role = userService.getUserRole(userDTO.getUsername());
            if ("ADMIN".equals(role)) {
                return "Admin/IndexAdmin";
            } else {
                return "redirect:/index";
            }
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "User/Login";
        }
    }

    @GetMapping("/user/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UsersDTO());
        return "User/Register";
    }

    @PostMapping("/user/register")
    public String registerUser(@ModelAttribute("user") @Validated UsersDTO userDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "User/Register";
        }
        userService.saveUser(userDTO);
        return "redirect:/login";
    }

    @GetMapping("/admin/registerAdmin")
    public String showRegistrationAdminForm(Model model) {
        model.addAttribute("admin", new UsersDTO());
        return "Admin/RegisterAdmin";
    }

    @PostMapping("/admin/registerAdmin")
    public String registerAdmin(@ModelAttribute("admin") UsersDTO userDTO) {
        userService.saveAdmin(userDTO);
        return "redirect:/login";
    }

    @PostMapping("/authenticate")
    public String authenticateUser(@RequestParam String username, @RequestParam String password, Model model) {
        if (userService.authenticate(username, password)) {
            return "redirect:/index";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "User/Login";
        }
    }
}
