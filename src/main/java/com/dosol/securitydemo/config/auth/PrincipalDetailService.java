package com.dosol.securitydemo.config.auth;

import com.dosol.securitydemo.domain.User;
import com.dosol.securitydemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername");
        User user = userRepository.findByUsername(username); //이렇게 유저 한개를 가져옵니다.
        if (user == null) {
            return null;
        }
        PrincipalDetails principalDetails = new PrincipalDetails(user);
        //프린시팔디테일에 유저를 넣은거를 프린시팔디테일을 하나 얻음
        log.info(principalDetails);
        return principalDetails;
    }
}
