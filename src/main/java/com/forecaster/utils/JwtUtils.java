package com.forecaster.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtils {
    private static String signKey = "CosmosBackpacker";
    private static Long expire = 43200000L;

    /**
     * 生成JWT令牌
     *
     * @param claims JWT第二部分负载 payload 中存储的内容
     * @return
     */
    public static String generateJwt(Map<String, Object> claims) {
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, signKey)  //设置签名算法
                .setClaims(claims)   //指定要被算的数据主体
                .setExpiration(new Date(System.currentTimeMillis() + expire))  //设置JWT令牌的维持时间
                .compact();     //返回jwt字符串
        return jwt;
    }

    /**
     * 解析JWT令牌
     *
     * @param jwt JWT令牌
     * @return JWT第二部分负载 payload 中存储的内容
     */
    public static Claims parseJWT(String jwt) {
        Claims claims = Jwts.parser()       //调用jwt里面自带的解析器
                .setSigningKey(signKey)       //设置解析的签名
                .parseClaimsJws(jwt)              //设置要解析的内容
                .getBody();                    //返回解析结果的载荷
        return claims;
    }
}
