package com.nadine.books.security;

public interface SecParams {
    long EXP_TIME = 10 * 24 * 60 * 60 * 1000;
    String SECRET = "123456789";
    String PREFIX = "Bearer ";
}
