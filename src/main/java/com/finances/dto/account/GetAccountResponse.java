package com.finances.dto.account;

public record GetAccountResponse(long id, long ownerId, double balance) {
}
