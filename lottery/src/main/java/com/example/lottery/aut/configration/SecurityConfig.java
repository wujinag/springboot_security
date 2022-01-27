package com.example.lottery.aut.configration;

import com.example.lottery.aut.filter.JWTAuthenticationFilter;
import com.example.lottery.aut.filter.JWTAuthorizationFilter;
import com.example.lottery.exception.JWTAccessDeniedHandler;
import com.example.lottery.exception.JWTAuthenticationEntryPoint;
import io.jsonwebtoken.JwtHandlerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Description：Security鉴权设置
 * Spring-Security配置类(需要继承WebSecurityConfigurerAdapter) - 项目启动执行
 * Author: wu.jiang
 * Date: Created in 2022/1/26 15:12
 * Version: 0.0.1
 * 注释:
 *  * 注解@EnableWebSecurity开启Security功能
 *  * 注解@EnableGlobalMethodSecurity(prePostEnabled = true),Security默认是禁用注解的，
 *  要想开启注解需要此配置,其表达式时间方法级别的安全性4个注解：(
 *  * @PreAuthorize 在方法调用之前,基于表达式的计算结果来限制对方法的访问
 *  * @PostAuthorize 允许方法调用,但是如果表达式计算结果为false,将抛出一个安全性异常
 *  * @PostFilter 允许方法调用,但必须按照表达式来过滤方法的结果
 *  * @PreFilter 允许方法调用,但必须在进入方法之前过滤输入值 )
 *  例如：@PreAuthorize("hasRole('ADMIN')")表示用户具有admin角色,才能访问该方法
 *
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    /**
     * 对密码进行加密的类,配置在spring中,方便调用
     * 例如：bCryptPasswordEncoder.encode(registerUser.get("password"))
     * @return
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
                http
                //开启跨域
                .cors()
                .and()
                //禁用CSRF保护
                .csrf().disable()
                .formLogin().loginPage("/index").permitAll()
                .and()
                .authorizeRequests()
                 //   例子： /tasks/**请求路径下的DELETE请求需要"ADMIN"权限才能操作
                // .antMatchers(HttpMethod.DELETE, "/tasks/**").hasRole("ADMIN")
               // 测试用资源，需要验证了的用户才能访问
               //  .antMatchers("/tasks/**").authenticated()
                /**对http所有的请求必须通过授权认证才可以访问*/
                .antMatchers("/lottery/**").authenticated()
                .anyRequest().permitAll()
                // 添加自定义的登录拦截方式
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                //添加自定义的请求拦截方式
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                //ALWAYS= 总是创建HttpSession IF_REQUIRED= Spring Security只会在需要时创建一个HttpSession
                //NEVER=Spring Security不会创建HttpSession，但如果它已经存在，将可以使用HttpSession
                //STATELESS= Spring Security永远不会创建HttpSession，它不会使用HttpSession来获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 自定义异常处理（没有携带token）
                .exceptionHandling().authenticationEntryPoint(new JWTAuthenticationEntryPoint())
                // 添加无权限处理的处理
                .accessDeniedHandler(new JWTAccessDeniedHandler());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

}
