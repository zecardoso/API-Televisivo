package com.apitelevisivo.config;

import com.apitelevisivo.security.jwt.JwtConfigurer;
import com.apitelevisivo.security.jwt.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/login").permitAll()
            .antMatchers("/v2/**").permitAll()
            .antMatchers("/api/**").hasAnyRole("ADMINISTRADOR", "USUARIO")
            .anyRequest().authenticated();

        // http.httpBasic();

        http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .maximumSessions(1);
        
        http.csrf().disable();

        http.apply(new JwtConfigurer(tokenProvider));
    }

    @Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
    }

    @Override
		public void configure(WebSecurity web) throws Exception {
			 web.ignoring().antMatchers("/v2/api-docs",
                     "/configuration/ui",
                     "/swagger-resources/**",
                     "/configuration/security",
                     "/swagger-ui.html",
                     "/webjars/**");
		}
}