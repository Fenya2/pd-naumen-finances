package com.finances.controllerView;

import com.finances.dto.analyze.CategoryExpenseReport;
import com.finances.dto.analyze.CategoryIncomeReport;
import com.finances.service.analyze.AnalyzeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/analyze")
@RequiredArgsConstructor
public class AnalyzeViewController {

    private final AnalyzeService analyzeService;

    /**
     * Отображает страницу с отчетом о расходах за текущий месяц
     */
    @GetMapping("/current-month/expenses-view")
    public String viewCurrentMonthExpenses(@RequestParam long userId, Model model) {
        List<CategoryExpenseReport> reports = analyzeService.getCurrentMonthExpenses(userId);
        model.addAttribute("reports", reports);
        model.addAttribute("title", "Расходы за текущий месяц");
        return "reports/expenseReport";
    }

    /**
     * Отображает страницу с отчетом о доходах за текущий месяц
     */
    @GetMapping("/current-month/income-view")
    public String viewCurrentMonthIncome(@RequestParam long userId, Model model) {
        List<CategoryIncomeReport> reports = analyzeService.getCurrentMonthIncome(userId);
        model.addAttribute("reports", reports);
        model.addAttribute("title", "Доходы за текущий месяц");
        return "reports/incomeReport";
    }

    /**
     * Отображает страницу с отчетом о расходах за прошедший месяц
     */
    @GetMapping("/previous-month/expenses-view")
    public String viewPreviousMonthExpenses(@RequestParam long userId, Model model) {
        List<CategoryExpenseReport> reports = analyzeService.getPreviousMonthExpenses(userId);
        model.addAttribute("reports", reports);
        model.addAttribute("title", "Расходы за прошедший месяц");
        return "reports/expenseReport";
    }

    /**
     * Отображает страницу с отчетом о доходах за прошедший месяц
     */
    @GetMapping("/previous-month/income-view")
    public String viewPreviousMonthIncome(@RequestParam long userId, Model model) {
        List<CategoryIncomeReport> reports = analyzeService.getPreviousMonthIncome(userId);
        model.addAttribute("reports", reports);
        model.addAttribute("title", "Доходы за прошедший месяц");
        return "reports/incomeReport";
    }

    /**
     * Отображает страницу с отчетом о расходах за произвольный период
     */
    @GetMapping("/expenses/period")
    public String viewExpensesByPeriod(
            @RequestParam long userId,
            @RequestParam String startDate,
            @RequestParam String endDate,
            Model model
    ) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<CategoryExpenseReport> reports = analyzeService.getExpensesByCategoryForPeriod(userId, start, end);
        model.addAttribute("reports", reports);
        model.addAttribute("title", "Расходы с " + start + " по " + end);
        return "reports/expenseReport";
    }

    /**
     * Отображает страницу с отчетом о доходах за произвольный период
     */
    @GetMapping("/income/period")
    public String viewIncomeByPeriod(
            @RequestParam long userId,
            @RequestParam String startDate,
            @RequestParam String endDate,
            Model model
    ) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<CategoryIncomeReport> reports = analyzeService.getIncomeByCategoryForPeriod(userId, start, end);
        model.addAttribute("reports", reports);
        model.addAttribute("title", "Доходы с " + start + " по " + end);
        return "reports/incomeReport";
    }
}
