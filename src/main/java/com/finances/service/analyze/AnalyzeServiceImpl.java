package com.finances.service.analyze;

import com.finances.dto.analyze.CategoryExpenseReport;
import com.finances.dto.analyze.CategoryIncomeReport;
import com.finances.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyzeServiceImpl implements AnalyzeService {

    private final TransactionRepository transactionRepository;

    @Override
    public List<CategoryExpenseReport> getCurrentMonthExpenses(long userId) {
        LocalDate now = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfMonth(1);
        LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());
        return mapExpensesToReport(
                transactionRepository.getExpensesByCategoryForPeriod(
                        userId,
                        toDate(startOfMonth),
                        toDate(endOfMonth)
                )
        );
    }

    @Override
    public List<CategoryIncomeReport> getCurrentMonthIncome(long userId) {
        LocalDate now = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfMonth(1);
        LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());
        return mapIncomeToReport(
                transactionRepository.getIncomeByCategoryForPeriod(
                        userId,
                        toDate(startOfMonth),
                        toDate(endOfMonth)
                )
        );
    }

    @Override
    public List<CategoryExpenseReport> getPreviousMonthExpenses(long userId) {
        YearMonth previousMonth = YearMonth.now().minusMonths(1);
        LocalDate startOfMonth = previousMonth.atDay(1);
        LocalDate endOfMonth = previousMonth.atEndOfMonth();
        return mapExpensesToReport(
                transactionRepository.getExpensesByCategoryForPeriod(
                        userId,
                        toDate(startOfMonth),
                        toDate(endOfMonth)
                )
        );
    }

    @Override
    public List<CategoryIncomeReport> getPreviousMonthIncome(long userId) {
        YearMonth previousMonth = YearMonth.now().minusMonths(1);
        LocalDate startOfMonth = previousMonth.atDay(1);
        LocalDate endOfMonth = previousMonth.atEndOfMonth();
        return mapIncomeToReport(
                transactionRepository.getIncomeByCategoryForPeriod(
                        userId,
                        toDate(startOfMonth),
                        toDate(endOfMonth)
                )
        );
    }

    @Override
    public List<CategoryExpenseReport> getExpensesByCategoryForPeriod(long userId, LocalDate startDate, LocalDate endDate) {
        return mapExpensesToReport(
                transactionRepository.getExpensesByCategoryForPeriod(
                        userId,
                        toDate(startDate),
                        toDate(endDate)
                )
        );
    }

    @Override
    public List<CategoryIncomeReport> getIncomeByCategoryForPeriod(long userId, LocalDate startDate, LocalDate endDate) {
        return mapIncomeToReport(
                transactionRepository.getIncomeByCategoryForPeriod(
                        userId,
                        toDate(startDate),
                        toDate(endDate)
                )
        );
    }

    private List<CategoryExpenseReport> mapExpensesToReport(List<Object[]> results) {
        return results.stream()
                .map(result -> new CategoryExpenseReport(
                        (String) result[0],
                        (Double) result[1]
                ))
                .toList();
    }

    private List<CategoryIncomeReport> mapIncomeToReport(List<Object[]> results) {
        return results.stream()
                .map(result -> new CategoryIncomeReport(
                        (String) result[0],
                        (Double) result[1]
                ))
                .toList();
    }

    private Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
