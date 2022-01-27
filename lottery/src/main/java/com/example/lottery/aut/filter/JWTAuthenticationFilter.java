package com.example.lottery.aut.filter;

import com.example.lottery.dal.model.JwtUser;
import com.example.lottery.dal.model.LoginUser;
import com.example.lottery.utils.JwtTokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.CollectionUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Description： 登录拦截器，校验成功并返回一个token给客户端
 * Author: wu.jiang
 * Date: Created in 2022/1/26 16:08
 * Version: 0.0.1
 * 登录拦截器 - 请求登录接口/auth/login时会被拦截
 *  *
 *  * 调用attemptAuthentication方法获取登录请求的(用户名,密码,记住我)等信息,
 *  * 调用authenticationManager.authenticate()让spring-security去进行验证
 *  * JWTAuthenticationFilter继承于UsernamePasswordAuthenticationFilter
 *  * 该拦截器用于获取用户登录的信息，只需创建一个token并调用authenticationManager.authenticate()让spring-security去进行验证就可以了，
 *  * 不用自己查数据库再对比密码了，这一步交给spring去操作。 这个操作有点像是shiro的subject.login(new UsernamePasswordToken())，
 *  * 验证的事情交给框架。
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    //并发情况的安全线程 - 为每个用户配置唯一的登录持久化状态
    private ThreadLocal<Integer> rememberMe = new ThreadLocal<>();

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        super.setFilterProcessesUrl("/login");;
    }


    /**
     * 第一步：获取用户参数,并进行校验(用户名密码)
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //从输入流中获取登录信息
        LoginUser loginUser = new ObjectMapper().readValue(request.getInputStream(), LoginUser.class);
        rememberMe.set(loginUser.getRememberMe() == null ? 0:loginUser.getRememberMe());
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getAccount(),
                        loginUser.getPassword(),
                        new ArrayList<>())
        );
    }

    /**
     * 第二步：第一步方法成功验证后,则生成token并返回
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        JwtUser jwtUser = (JwtUser) authResult.getPrincipal();
        boolean isRemmerMe = rememberMe.get() == 1;
        List<String> roles = new ArrayList<>();
        Collection<? extends GrantedAuthority> authorities = jwtUser.getAuthorities();
        if(!CollectionUtils.isEmpty(roles)){
            authorities.stream().forEach(e->{
                roles.add(e.getAuthority());
            });
        }
        //根据jwtuser信息创建一个token
         String token = JwtTokenUtils.createToken(jwtUser.getUsername(), roles, isRemmerMe);
        response.setHeader("token",JwtTokenUtils.TOKEN_PREFIX+token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        response.getWriter().write("Authentication failed,reason:"+failed.getMessage());
    }
}
