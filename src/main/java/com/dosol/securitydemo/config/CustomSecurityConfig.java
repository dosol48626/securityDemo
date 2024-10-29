package com.dosol.securitydemo.config;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //이게 시큐리티 메소드를 활성화 시킨다는 뜻임
@RequiredArgsConstructor //여기에 필드 둘거니까, 객체를 생성하자마자 의존성 주입을 받겠다.
public class CustomSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //이 함수가 들어가면 로그인, 이제 로그인 창 안뜸.
        return http //이걸 다 외울수 없지만, 어떤 요소가 있고 어떤 역할을 해주는지 알아야한다.
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                // 이리 되있어야 폼의 데이타를 csrf토근 사용한다고 체크 안해도 설정 안해도 받아줌.
                // 레스트도 혼용해야하는데,? 이게 없으면 레스트방식에서 올릴때 안된다.

                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.disable())
                //이건 뭔가하면, 톰캣 서버가 아닌 다른서버로 오는데이터 받아주겠다를 disable하겠다.
                //이거 없으면 쓰겠다이기때문에 설정해줘야함.

                .authorizeHttpRequests(authorizeHttpRequestsConfigurer-> authorizeHttpRequestsConfigurer
                        // DispatcherType.FORWARD 타입에 맞는 요청을 허용
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers("/login", "/sinup", "/user/**", "/","/all").permitAll()
                        // 퍼밋 올이, 시큐리티 쓰면 모든 페이지가 시큐리티 걸려있음. 그래서 로그인 전에 아무것도 못씀.
                        //게시판이나 그런건 보여야하는데, 이걸로 어디어디는 보이게 하겠다. 이거임
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        //어디민 밑에 모든것은, hasAuthority 어드민만 접근할 수 있다.
                        .anyRequest().authenticated())
                        //이거를 제외한 모든것은 로그인을 해야들어갈수 있다.

                //밑에부터는 폼로그인 설정
                .formLogin(formLoginConfigurer-> formLoginConfigurer //로그인 설정해주는거임
                        .loginPage("/user/login") //로그인 폼은 여기 있습니다. 하고 설정해주는거임.
                        //로그인 폼을 요청하면 이렇게 써라. 그러니까 컨트롤러에서 이거 뺀거임

                        .loginProcessingUrl("/loginProcess") //시큐리티는 로그인 처리를 하지 않습니다.
                        //로그인 프로세스는 그냥 스프링에서 제공해줌 개꿀
                        //아이디랑 패스워드 받고, 패스워드는 인코딩해서, 알아서 디비가서 맞춰서 로그인 처리 한다.
                        //이건 만들 필요가 없다. 알아서 만들어준다. 단 액션에 주소가 필요하니 이걸 적어주는거다.

                        .usernameParameter("username") //로그인할때 적을거를 이 이름으로 하겠다
                        .passwordParameter("password") // 같은 맥락임. 이메일이나 다른거쓰면 설정 필요함
                        .defaultSuccessUrl("/") //로그인 성공하면 어디로 갈지. /이거면 홈이겠네.
                        .permitAll()) //퍼밋 끝~

                .logout(logoutConfigurer-> logoutConfigurer //로그아웃부분
                                .logoutUrl("/logout") //로그아웃 주소
                                .logoutSuccessUrl("/") //로그아웃 성공 후 주소
                                .invalidateHttpSession(true) //세션에 있는 정보 무효화
                                .clearAuthentication(true)) //어센틴게이션 트루, 인증정보가 클리어하겠다.
                        .build(); // 그리고 빌드
    }

    @Bean // password 암호화 빈
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        //이 빈을 상요하면 패스워드가 암호화가 된다.
        //로그인할때는 그냥 적으면 암호화된거를 풀어준다?
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer configure() {
        return (web -> web.ignoring() //모든 스태틱 자원 필터 제외
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()));
        //스태틱 리소스로 권한을 주었다말았다 복잡해져서, 이렇게 무시하겠다. 시큐리티 적용을?
        //이미지 같은게 시큐리티에 걸려있거나 그럴수 있으니 무시하겠다.
        //.request<atchers("/static/**")
        //이거 안하면, 스태틱에 이미지 넣어두면 이미직 안나옴. 이미지를 허용안해서.
        //필터 적용이 안되게 한다?
        //제외시켜두는거다? 로그인 안하면 이미지가 안나온다? 이런경우이다.
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       BCryptPasswordEncoder bCryptPasswordEncoder,
                                                       UserDetailsService userDetailsService, AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        //이게 뭐지..? 다시 암호화해서 들고온다? 유저디테일 서비스?? 이거 왜 가져옴??
        //로그인 폼에서 보내는 패스워드는 인코딩 안되있는거임, 그래서 이렇게 해야한다.?

//        AuthenticationManager builder =
//                http.getSharedObject(AuthenticationManager.class); //이 클래스를 이용해서
//
//        builder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
//
//        return builder.build();

        AuthenticationManagerBuilder builder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        builder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);

        return builder.build();
        //이 부분 봐야겠네.
        //유저디테일 서비스? 그걸 가지고 로그인 처리를 함.
    }

}
