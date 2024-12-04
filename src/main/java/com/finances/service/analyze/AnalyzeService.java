package com.finances.service.analyze;

import com.finances.dto.analyze.CategoryExpenseReport;
import com.finances.dto.analyze.CategoryIncomeReport;

import java.time.LocalDate;
import java.util.List;

public interface AnalyzeService {
    List<CategoryExpenseReport> getCurrentMonthExpenses(long userId);
    List<CategoryIncomeReport> getCurrentMonthIncome(long userId);
    List<CategoryExpenseReport> getPreviousMonthExpenses(long userId);
    List<CategoryIncomeReport> getPreviousMonthIncome(long userId);
    List<CategoryExpenseReport> getExpensesByCategoryForPeriod(long userId, LocalDate startDate, LocalDate endDate);
    List<CategoryIncomeReport> getIncomeByCategoryForPeriod(long userId, LocalDate startDate, LocalDate endDate);
}
