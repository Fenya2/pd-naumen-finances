package com.finances.controllerView;

import com.finances.model.Transaction;
import com.finances.service.transaction.TransactionService;
import org.springframework.ui.Model;
import com.finances.model.User;
import com.finances.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/transaction/view")
public class TransactionViewController {
    private final TransactionService transactionService;
    private final UserService userService;

    @Autowired
    public TransactionViewController(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @GetMapping
    public String viewGoal(@RequestParam long userId, Model model) {
        User user = userService.findById(userId);
        List<Transaction> allTransactionsByUser = transactionService.getAllTransactionsByUser(user);

        model.addAttribute("allTransaction", allTransactionsByUser);
        model.addAttribute("user", userId);

        return "transaction/transaction";
    }
}
