package com.finances.controllerView;

import com.finances.dto.goal.GoalGetResponse;
import com.finances.dto.goal.GoalMapper;
import com.finances.model.Account;
import com.finances.model.User;
import com.finances.service.account.AccountService;
import com.finances.service.goal.GoalService;
import com.finances.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Контроллер, возвращающий основные страницы, с которыми взаимодействует пользователь
 */
@Controller
@RequestMapping("/home")
public class ViewController {

    private final UserService userService;
    private final AccountService accountService;
    private final GoalService goalService;

    @Autowired
    public ViewController(UserService userService,
                          AccountService accountService,
                          GoalService goalService) {
        this.userService = userService;
        this.accountService = accountService;
        this.goalService = goalService;
    }

    /**
     * Возвращает домашнюю страницу пользователя
     */
    @GetMapping("/{login}")
    public String home(@PathVariable String login, Model model) {
        final User user = userService.findByLogin(login);
        final Account account = accountService.getUserAccount(user);
        model.addAttribute("username", login);
        model.addAttribute("balance", account.getBalance());
        model.addAttribute("goals", generateGoalUrl(user));

        return "home";
    }

    @GetMapping("/{id}/goals")
    public String getGoals(@PathVariable long id, Model model) {
        final User user = userService.findById(id);
        final List<GoalGetResponse> goals = goalService.getAllGoalsForUser(user)
                        .stream().map(GoalMapper::toGoalGetResponse).toList();
        model.addAttribute("back", "/home/%s".formatted(user.getLogin()));
        model.addAttribute("goals", goals);
        model.addAttribute("create", "test");
        return "goals";
    }

    private String generateGoalUrl(User user) {
        return "/home/%s/goals".formatted(user.getId());
    }
}
