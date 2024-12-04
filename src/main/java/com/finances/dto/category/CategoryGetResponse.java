package com.finances.dto.category;

public record CategoryGetResponse(long idCategory,
                                  long[] idChild,
                                  String name) {}
