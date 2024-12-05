package com.finances.controllerView;

import com.finances.dto.analyze.CategoryExpenseReport;
import com.finances.dto.analyze.CategoryIncomeReport;
import com.finances.service.analyze.AnalyzeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

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
        String currentMonth = LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
        model.addAttribute("reports", reports);
        model.addAttribute("title", "Расходы за текущий месяц");
        model.addAttribute("month", currentMonth);
        model.addAttribute("backUrl", "/analyze?userId=" + userId);
        return "analyze/expenseReport";
    }

    /**
     * Отображает страницу с отчетом о доходах за текущий месяц
     */
    @GetMapping("/current-month/income-view")
    public String viewCurrentMonthIncome(@RequestParam long userId, Model model) {
        List<CategoryIncomeReport> reports = analyzeService.getCurrentMonthIncome(userId);
        String currentMonth = LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
        model.addAttribute("reports", reports);
        model.addAttribute("title", "Доходы за текущий месяц");
        model.addAttribute("month", currentMonth);
        model.addAttribute("backUrl", "/analyze?userId=" + userId);
        return "analyze/incomeReport";
    }

    /**
     * Отображает страницу с отчетом о расходах за прошедший месяц
     */
    @GetMapping("/previous-month/expenses-view")
    public String viewPreviousMonthExpenses(@RequestParam long userId, Model model) {
        List<CategoryExpenseReport> reports = analyzeService.getPreviousMonthExpenses(userId);
        String previousMonth = LocalDate.now().minusMonths(1).getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
        model.addAttribute("reports", reports);
        model.addAttribute("title", "Расходы за прошедший месяц");
        model.addAttribute("month", previousMonth);
        model.addAttribute("backUrl", "/analyze?userId=" + userId);
        return "analyze/expenseReport";
    }

    /**
     * Отображает страницу с отчетом о доходах за прошедший месяц
     */
    @GetMapping("/previous-month/income-view")
    public String viewPreviousMonthIncome(@RequestParam long userId, Model model) {
        List<CategoryIncomeReport> reports = analyzeService.getPreviousMonthIncome(userId);
        String previousMonth = LocalDate.now().minusMonths(1).getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
        model.addAttribute("reports", reports);
        model.addAttribute("title", "Доходы за прошедший месяц");
        model.addAttribute("month", previousMonth);
        model.addAttribute("backUrl", "/analyze?userId=" + userId);
        return "analyze/incomeReport";
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
        model.addAttribute("month", start.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) +
                " - " +
                end.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()));
        model.addAttribute("backUrl", "/analyze?userId=" + userId);
        return "analyze/expenseReport";
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
        model.addAttribute("month", start.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) +
                " - " +
                end.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()));
        model.addAttribute("backUrl", "/analyze?userId=" + userId);
        return "analyze/incomeReport";
    }

    /**
     * Отображает страницу аналитики с текущими доходами и расходами,
     * а также кнопками для перехода к подробным отчетам по категориям.
     */
    @GetMapping()
    public String viewAnalyzeDashboard(@RequestParam long userId, Model model) {
        // Получение данных о текущих доходах и расходах
        List<CategoryIncomeReport> currentMonthIncome = analyzeService.getCurrentMonthIncome(userId);
        List<CategoryExpenseReport> currentMonthExpenses = analyzeService.getCurrentMonthExpenses(userId);

        double sumIncome = currentMonthIncome.stream()
                .mapToDouble(CategoryIncomeReport::income)
                .sum();
        double sumExpected = currentMonthExpenses.stream()
                .mapToDouble(CategoryExpenseReport::expense)
                .sum();

        // Название текущего месяца
        String currentMonth = LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());

        // Добавление атрибутов в модель
        model.addAttribute("currentMonthIncome", sumIncome);
        model.addAttribute("currentMonthExpenses", sumExpected);
        model.addAttribute("currentMonth", currentMonth);
        model.addAttribute("userId", userId);

        // Возврат имени шаблона для отображения
        return "analyze/analyzeDashboard";
    }
}
