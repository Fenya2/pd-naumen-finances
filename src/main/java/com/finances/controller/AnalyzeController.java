package com.finances.controller;

import com.finances.dto.analyze.CategoryExpenseReport;
import com.finances.dto.analyze.CategoryIncomeReport;
import com.finances.service.analyze.AnalyzeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/analyze")
@RequiredArgsConstructor
public class AnalyzeController {

    private final AnalyzeService analyzeService;

    /**
     * Получить расходы по категориям за текущий месяц
     *
     * @param userId идентификатор пользователя
     * @return список отчетов о расходах по категориям
     */
    @GetMapping("/current-month/expenses")
    public List<CategoryExpenseReport> getCurrentMonthExpenses(@RequestParam long userId) {
        return analyzeService.getCurrentMonthExpenses(userId);
    }

    /**
     * Получить доходы по категориям за текущий месяц
     *
     * @param userId идентификатор пользователя
     * @return список отчетов о доходах по категориям
     */
    @GetMapping("/current-month/income")
    public List<CategoryIncomeReport> getCurrentMonthIncome(@RequestParam long userId) {
        return analyzeService.getCurrentMonthIncome(userId);
    }

    /**
     * Получить расходы по категориям за прошедший месяц
     *
     * @param userId идентификатор пользователя
     * @return список отчетов о расходах по категориям
     */
    @GetMapping("/previous-month/expenses")
    public List<CategoryExpenseReport> getPreviousMonthExpenses(@RequestParam long userId) {
        return analyzeService.getPreviousMonthExpenses(userId);
    }

    /**
     * Получить доходы по категориям за прошедший месяц
     *
     * @param userId идентификатор пользователя
     * @return список отчетов о доходах по категориям
     */
    @GetMapping("/previous-month/income")
    public List<CategoryIncomeReport> getPreviousMonthIncome(@RequestParam long userId) {
        return analyzeService.getPreviousMonthIncome(userId);
    }

    /**
     * Получить расходы по категориям за произвольный период
     *
     * @param userId    идентификатор пользователя
     * @param startDate начальная дата периода (в формате YYYY-MM-DD)
     * @param endDate   конечная дата периода (в формате YYYY-MM-DD)
     * @return список отчетов о расходах по категориям
     */
    @GetMapping("/expenses")
    public List<CategoryExpenseReport> getExpensesByPeriod(
            @RequestParam long userId,
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return analyzeService.getExpensesByCategoryForPeriod(userId, start, end);
    }

    /**
     * Получить доходы по категориям за произвольный период
     *
     * @param userId    идентификатор пользователя
     * @param startDate начальная дата периода (в формате YYYY-MM-DD)
     * @param endDate   конечная дата периода (в формате YYYY-MM-DD)
     * @return список отчетов о доходах по категориям
     */
    @GetMapping("/income")
    public List<CategoryIncomeReport> getIncomeByPeriod(
            @RequestParam long userId,
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return analyzeService.getIncomeByCategoryForPeriod(userId, start, end);
    }
}
