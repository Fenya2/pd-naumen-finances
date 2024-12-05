package com.finances.controllerView;

import com.finances.model.User;
import com.finances.service.account.AccountService;
import com.finances.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/home")
public class HomeViewController {
    private final UserService userService;
    private final AccountService accountService;

    @Autowired
    public HomeViewController(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @GetMapping()
    public String viewHomePage(@RequestParam long userId, Model model) {
        User user = userService.findById(userId);

        double balance = accountService.getUserAccount(user).getBalance();

        model.addAttribute("balance", balance);
        model.addAttribute("username", user.getName());
        model.addAttribute("userId", userId);

        return "home/home";
    }
}
