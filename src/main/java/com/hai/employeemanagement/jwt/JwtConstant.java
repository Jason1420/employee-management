package com.hai.employeemanagement.jwt;

public class JwtConstant {
    public static final long JWT_EXPIRATION = 20 * 1000 ; // 20s
    public static final long DELETE_ACCESS_TOKEN_TIME = 0 ; // 20s
    public static final long REFRESH_TOKEN_EXPIRATION = 60 * 1000 * 60 * 24;
    public static final String JWT_SECRET = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    public static final String  REFRESH_TOKEN_SECRET = "404E635266556A586E32723575HAI38782F413F4428472B4B6250645367566B5970";
}
