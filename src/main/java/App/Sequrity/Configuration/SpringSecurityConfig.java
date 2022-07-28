package App.Sequrity.Configuration;

import App.Sequrity.Filter.JwtCsrfFilter;
//import App.Sequrity.Repository.JwtTokenRepository;
import App.Sequrity.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
//--- https://www.baeldung.com/spring-boot-security-autoconfiguration

    @Autowired
    private UserService service;

    /*
    @Autowired
    private JwtTokenRepository jwtTokenRepository;
     */


    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Bean
    public PasswordEncoder devPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .addFilterAt(new JwtCsrfFilter(resolver), CsrfFilter.class)
                .csrf().ignoringAntMatchers("/**")
                .and()
                .authorizeRequests()
                .antMatchers("/login")
                .authenticated()
                .and()
                .formLogin();
        /*
                .httpBasic()
                .authenticationEntryPoint(((request, response, e) -> resolver.resolveException(request, response, null, e)));

         */


    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.service);
    }


}