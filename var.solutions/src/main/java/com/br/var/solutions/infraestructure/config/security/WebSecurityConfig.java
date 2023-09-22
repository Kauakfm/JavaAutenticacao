package com.br.var.solutions.infraestructure.config.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
    private JwtAuthenticationEntryPoint JwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtRequestFilter requestFilter;

protected void configure(HttpSecurity httpSecurity) throws Exception
{
    httpSecurity.csrf().disable()
            .authorizeRequests().antMatchers("/pessoa/authorization","/auth","/usuario","/pessoa/authenticate", "/configuration/**", "/webjars/**").permitAll()
            .antMatchers(HttpMethod.OPTIONS, "/pessoa/resumo", "/usuario","/usuario/{id}", "/usuario/", "/pessoa", "/pessoa/{id}").permitAll()
            .anyRequest().authenticated().and().exceptionHandling().authenticationEntryPoint(JwtAuthenticationEntryPoint).and().sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            httpSecurity.addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class);

}
}
