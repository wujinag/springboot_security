package com.example.lottery.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Description： jwt工具类
 * Author: wu.jiang
 * Date: Created in 2022/1/26 17:18
 * Version: 0.0.1
 */
public class JwtTokenUtils {
    /**
     * 用户请求头中的Token的key(例：Authorization: *************)
     */
    public static final String TOKEN_HEADER = "Authorization";
    /**
     * 返回的Token前缀信息(自定义配置)
     */
    public static final String TOKEN_PREFIX = "Bearer ";
    /**
     * JTW的加密算法SigningKey
     */
    private static final String SECRET = "jwtSecret";
    /**
     * 该JWT的签发者,是否使用是可选的(可以使用项目名称或者作者名称之类)
     */
    private static final String ISS = "authTom";
    /**
     * 角色的key
     */
    private static final String ROLE_CLAIMS = "rol";
    /**
     * 过期时间是3600秒，既是1个小时
     */
    private static final long EXPIRATION = 3600L;
    /**
     * 选择了记住我之后的过期时间为7天
     */
    private static final long EXPIRATION_REMEMBER = 604800L;

    /**
     * 创建token方法
     * @param username 用户名
     * @param role 角色权限集合
     * @param isRememberMe 记住我(是,否) - 记住过期时间为7天,没选择则过期时间为1个小时
     * @return
     */
    public static String createToken(String username, List<String> role, boolean isRememberMe) {
        long expiration = isRememberMe ? EXPIRATION_REMEMBER : EXPIRATION;
        HashMap<String, Object> map = new HashMap<>();
        map.put(ROLE_CLAIMS, role);
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .setClaims(map)
                .setIssuer(ISS)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }

    /**
     * 从token中获取用户名
     * @param token
     * @return
     */
    public static String getUserName(String token){
        return getTokenBody(token).getSubject();
    }

    /**
     * 从token中获取用户角色集合
     * @param token
     * @return
     */
    public static List<String> getUserRole(String token){
        return (List<String>) getTokenBody(token).get(ROLE_CLAIMS);
    }

    /**
     * 判断是否已过期
     * @param token
     * @return
     */
    public static boolean isExpiration(String token) {
        try {
            return getTokenBody(token).getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    /**
     * 获取token请求体内容
     * @param token
     * @return
     */
    private static Claims getTokenBody(String token){
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }
}
