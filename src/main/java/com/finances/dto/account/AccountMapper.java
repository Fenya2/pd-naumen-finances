package com.finances.dto.account;

import com.finances.model.Account;

/**
 * Занимается преобразованием dto в сущности и обратно для {@link Acc}
 */
public class AccountMapper {
    private AccountMapper() {
        throw new UnsupportedOperationException();
    }

    public static GetAccountResponse toGetAccountResponse(Account account) {
        return new GetAccountResponse(account.getId(), account.getOwner().getId(), account.getBalance());
    }
}
