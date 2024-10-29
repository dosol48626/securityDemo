package com.dosol.securitydemo.config.auth;

import com.dosol.securitydemo.domain.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class PrincipalDetails implements UserDetails {
    private User user; //유저라는 객체 하나를 필드로 가지고 있음.

    public PrincipalDetails(User user) {
        this.user = user; //이런게 생성자이다.
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //권한? 어떤 롤을 가지고 있는지
        //어떤 사용자의 롤 정보를 컬렉션에 담아서 리턴

        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(()->{return user.getRole();}); //()이거 한번 더 하면 파라미터 안하고 그냥 보내는거
        return authorities;
        //로그인 된 유저의 정보?
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername(); //얻은거 그대로 가져옴 아이디 비번
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
