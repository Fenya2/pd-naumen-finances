package com.finances.service.analyze;

import com.finances.dto.analyze.CategoryExpenseReport;
import com.finances.dto.analyze.CategoryIncomeReport;

import java.time.LocalDate;
import java.util.List;


public interface AnalyzeService {

    /**
     * Получает список отчетов о расходах текущего месяца по категориям для указанного пользователя.
     *
     * @param userId идентификатор пользователя
     * @return список отчетов о расходах по категориям
     */
    List<CategoryExpenseReport> getCurrentMonthExpenses(long userId);

    /**
     * Получает список отчетов о доходах текущего месяца по категориям для указанного пользователя.
     *
     * @param userId идентификатор пользователя
     * @return список отчетов о доходах по категориям
     */
    List<CategoryIncomeReport> getCurrentMonthIncome(long userId);

    /**
     * Получает список отчетов о расходах предыдущего месяца по категориям для указанного пользователя.
     *
     * @param userId идентификатор пользователя
     * @return список отчетов о расходах по категориям
     */
    List<CategoryExpenseReport> getPreviousMonthExpenses(long userId);

    /**
     * Получает список отчетов о доходах предыдущего месяца по категориям для указанного пользователя.
     *
     * @param userId идентификатор пользователя
     * @return список отчетов о доходах по категориям
     */
    List<CategoryIncomeReport> getPreviousMonthIncome(long userId);

    /**
     * Получает список отчетов о расходах по категориям для указанного пользователя за указанный период.
     *
     * @param userId    идентификатор пользователя
     * @param startDate начальная дата периода
     * @param endDate   конечная дата периода
     * @return список отчетов о расходах по категориям за период
     */
    List<CategoryExpenseReport> getExpensesByCategoryForPeriod(long userId, LocalDate startDate, LocalDate endDate);

    /**
     * Получает список отчетов о доходах по категориям для указанного пользователя за указанный период.
     *
     * @param userId    идентификатор пользователя
     * @param startDate начальная дата периода
     * @param endDate   конечная дата периода
     * @return список отчетов о доходах по категориям за период
     */
    List<CategoryIncomeReport> getIncomeByCategoryForPeriod(long userId, LocalDate startDate, LocalDate endDate);
}
