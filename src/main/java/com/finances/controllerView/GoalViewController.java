package com.finances.controllerView;

import com.finances.model.Goal;
import com.finances.model.User;
import com.finances.service.goal.GoalService;
import com.finances.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/goal/view")
public class GoalViewController {
    private final GoalService goalService;
    private final UserService userService;

    @Autowired
    public GoalViewController(GoalService goalService, UserService userService) {
        this.goalService = goalService;
        this.userService = userService;
    }

    @GetMapping
    public String viewGoal(@RequestParam long userId, Model model) {
        User user = userService.findById(userId);
        List<Goal> allGoal = goalService.getAllGoalsForUser(user);

        model.addAttribute("allGoal", allGoal);
        model.addAttribute("user", user);

        return "goal/goal";
    }

    @PostMapping("/add")
    public String addGoal(@RequestParam long userId,
                          @RequestParam String name,
                          @RequestParam double amount,
                          @RequestParam String date) {

        User user = userService.findById(userId);
        Goal newGoal = new Goal();
        newGoal.setName(name);
        newGoal.setAmount(amount);
        newGoal.setDate(Date.from(LocalDate.parse(date).atStartOfDay(ZoneId.systemDefault()).toInstant())); // Преобразование строки в LocalDate
        newGoal.setOwner(user); // Установка владельца цели

         // Сохранение новой цели

        return "redirect:/goal/view?userId=" + userId; // Перенаправление обратно на страницу с целями
    }
}
