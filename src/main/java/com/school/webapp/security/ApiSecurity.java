package com.school.webapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class ApiSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private SecurityFilter SecurityFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/authenticate").permitAll()
                .antMatchers("/register").hasAnyRole("TEACHER","BURSARY","ADMIN")
                .antMatchers("/retrievestudentinformation/**").hasAnyRole("BURSARY","ADMIN","TEACHER")
                .antMatchers("/editstudentinformation/**").hasRole("TEACHER")
                .antMatchers("/editinformationimage/**").hasRole("TEACHER")
                .antMatchers("/deletestudent/**").hasRole("TEACHER")
                .antMatchers("/retrivenames/**").hasAnyRole("BURSARY","ADMIN","TEACHER")
                .antMatchers("/registerteacher/**").hasAnyRole("BURSARY","ADMIN","TEACHER")
                .antMatchers("/createsession/").hasRole("ADMIN")
                .antMatchers("/getstudentscores/**").hasAnyRole("TEACHER","ADMIN")
                .antMatchers("/updatescore/**").hasRole("TEACHER")
                .antMatchers("/updatesubject/**").hasRole("TEACHER")
                .antMatchers("/insertsubject/**").hasRole("TEACHER")
                .antMatchers("/deletesubject/**").hasRole("TEACHER")
                .antMatchers("/retrieveinformationsession/**").hasAnyRole("BURSARY","ADMIN","TEACHER")
                .antMatchers("/retrievescoresession/**").hasAnyRole("BURSARY","ADMIN","TEACHER")
                .antMatchers("/retrieveparent/**").hasAnyRole("BURSARY","ADMIN","TEACHER")
                .antMatchers("/getparentinformation/**").hasAnyRole("BURSARY","ADMIN","TEACHER")
                .antMatchers("/getschoolfee/**").hasAnyRole("BURSARY","ADMIN")
                .antMatchers("/getschoolfeewithoutterm/**").hasAnyRole("BURSARY","ADMIN","TEACHER")
                .antMatchers("/saveterm/**").hasRole("BURSARY")
                .antMatchers("/savedatatoschoolfeetable/**").hasRole("BURSARY")
                .antMatchers("/savestudentnametoschoolfee/**").hasRole("BURSARY")
                .antMatchers("/deleteschoolfee/**").hasRole("BURSARY")
                .antMatchers("/getdebtors/**").hasAnyRole("BURSARY","ADMIN")
                .antMatchers("/savebook/**").hasRole("BURSARY")
                .antMatchers("/deletebook/**").hasRole("BURSARY")
                .antMatchers("/findallbook/**").hasAnyRole("BURSARY","ADMIN")
                .antMatchers("/searchbook/**").hasAnyRole("BURSARY","ADMIN")
                .antMatchers("/sellbook/**").hasRole("BURSARY")
                .antMatchers("/getbookhistory/**").hasAnyRole("BURSARY","ADMIN")
                .antMatchers("/editbook/**").hasRole("BURSARY")


                .anyRequest().authenticated().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(SecurityFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder getPwdEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
