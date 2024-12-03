package com.finances.dto.category;

import java.util.Date;

public record CategoryDTO(long id,
                          long idOwner,
                          String name,
                          double amount,
                          Date date,
                          long idAccount) {
}
