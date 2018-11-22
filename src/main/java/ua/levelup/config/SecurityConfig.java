package ua.levelup.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //Регистрируем бин userDetailsService
    @Override
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    //Тут непосредственно конфигурируются ограничения для каждой из ролей
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
        .authorizeRequests()
                .antMatchers("/resources/css/**", "/search/**").permitAll()
                .antMatchers("/category/**", "/product/**", "/manufacturer/**"
                        , "/order/**", "/user/**").hasRole("ADMIN")
                .antMatchers("/profile/order/**").hasRole("ACTIVE")
                .antMatchers("/cart/**")
                .access("!isAuthenticated() or hasRole('ACTIVE')")
                .and()
        .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/j_spring_security_check")
                .failureUrl("/loginfailed")
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll().and()
        .logout()
                .permitAll()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                //Делаем не валидной текущую сессию
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
    }
}