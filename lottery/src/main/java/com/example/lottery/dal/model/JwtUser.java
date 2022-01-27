package com.example.lottery.dal.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Description： jwtUser用户信息
 * Author: wu.jiang
 * Date: Created in 2022/1/26 16:56
 * Version: 0.0.1
 */
public class JwtUser implements UserDetails {

    private Integer id;

    private String account;

    private String password;

    private String name;

    private Collection<? extends GrantedAuthority> authorities;

    public JwtUser() {
    }

    /**
     * 使用user创建jwtUser的构造器
     * @param user 用户对象
     */
    public JwtUser(LotteryUser user) {
        id = user.getId();
        account = user.getAccount();
        password = user.getPassword();
        if(user.getRoles() != null && user.getRoles().size() > 0){
            List<GrantedAuthority> list = new ArrayList<>();
            for (String role : user.getRoles()) {
                list.add(new SimpleGrantedAuthority(role));
            }
            authorities = list;
        }
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return account;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String toString() {
        return "JwtUser{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}
