package com.finances.controller;

import com.finances.dto.analyze.CategoryExpenseReport;
import com.finances.dto.analyze.CategoryIncomeReport;
import com.finances.security.GlobalUserValidator;
import com.finances.service.analyze.AnalyzeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/analyze")
@RequiredArgsConstructor
public class AnalyzeController {

    private final AnalyzeService analyzeService;
    private final GlobalUserValidator globalUserValidator;

    /**
     * Получить расходы по категориям за текущий месяц у пользователя с переданным id
     *
     * @param userId идентификатор пользователя
     * @return список отчетов о расходах по категориям
     */
    @GetMapping("/current-month/expenses")
    public List<CategoryExpenseReport> getCurrentMonthExpenses(Authentication authentication, @RequestParam long userId) throws AccessDeniedException {
        globalUserValidator.checkUserIsValid(authentication, userId);
        return analyzeService.getCurrentMonthExpenses(userId);
    }

    /**
     * Получить доходы по категориям за текущий месяц у пользователя с переданным id
     *
     * @param userId идентификатор пользователя
     * @return список отчетов о доходах по категориям
     */
    @GetMapping("/current-month/income")
    public List<CategoryIncomeReport> getCurrentMonthIncome(Authentication authentication, @RequestParam long userId) throws AccessDeniedException {
        globalUserValidator.checkUserIsValid(authentication, userId);
        return analyzeService.getCurrentMonthIncome(userId);
    }

    /**
     * Получить расходы по категориям за прошедший месяц у пользователя с переданным id
     *
     * @param userId идентификатор пользователя
     * @return список отчетов о расходах по категориям
     */
    @GetMapping("/previous-month/expenses")
    public List<CategoryExpenseReport> getPreviousMonthExpenses(
            Authentication authentication,
            @RequestParam long userId) throws AccessDeniedException {
        globalUserValidator.checkUserIsValid(authentication, userId);
        return analyzeService.getPreviousMonthExpenses(userId);
    }

    /**
     * Получить доходы по категориям за прошедший месяц у пользователя с переданным id
     *
     * @param userId идентификатор пользователя
     * @return список отчетов о доходах по категориям
     */
    @GetMapping("/previous-month/income")
    public List<CategoryIncomeReport> getPreviousMonthIncome(
            Authentication authentication,
            @RequestParam long userId) throws AccessDeniedException {
        globalUserValidator.checkUserIsValid(authentication, userId);
        return analyzeService.getPreviousMonthIncome(userId);
    }

    /**
     * Получить расходы по категориям за произвольный период у пользователя с переданным id
     *
     * @param userId    идентификатор пользователя
     * @param startDate начальная дата периода (в формате YYYY-MM-DD)
     * @param endDate   конечная дата периода (в формате YYYY-MM-DD)
     * @return список отчетов о расходах по категориям
     */
    @GetMapping("/expenses")
    public List<CategoryExpenseReport> getExpensesByPeriod(
            Authentication authentication,
            @RequestParam long userId,
            @RequestParam String startDate,
            @RequestParam String endDate
    ) throws AccessDeniedException {
        globalUserValidator.checkUserIsValid(authentication, userId);
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return analyzeService.getExpensesByCategoryForPeriod(userId, start, end);
    }

    /**
     * Получить доходы по категориям за произвольный период у пользователя с переданным id
     *
     * @param userId    идентификатор пользователя
     * @param startDate начальная дата периода (в формате YYYY-MM-DD)
     * @param endDate   конечная дата периода (в формате YYYY-MM-DD)
     * @return список отчетов о доходах по категориям
     */
    @GetMapping("/income")
    public List<CategoryIncomeReport> getIncomeByPeriod(
            Authentication authentication,
            @RequestParam long userId,
            @RequestParam String startDate,
            @RequestParam String endDate
    ) throws AccessDeniedException {
        globalUserValidator.checkUserIsValid(authentication, userId);
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return analyzeService.getIncomeByCategoryForPeriod(userId, start, end);
    }
}
