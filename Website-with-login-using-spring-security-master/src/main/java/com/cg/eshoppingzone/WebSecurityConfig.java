package com.cg.eshoppingzone;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@EnableOAuth2Sso
@Configuration
@EnableWebSecurity
@RestController
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
      return authenticationManager();
    }
    
    
    
    @RequestMapping({ "/user", "/me" })
	public Map<String, String> user(Principal principal) {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("name", principal.getName());
		return map;
	}
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
       /* .csrf().disable().antMatcher("/**").authorizeRequests().antMatchers("/**", "/login**").permitAll()
		.anyRequest().authenticated().and().logout().logoutSuccessUrl("/").clearAuthentication(true).permitAll();*/
                .authorizeRequests()
                    .antMatchers("/resources/**", "/registration","/**","/homelogin**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/mlogin")
                    .permitAll()
                    .and()
                .logout()
                    .permitAll();
     
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
    
   /* @RequestMapping("/user")
	public Principal user(Principal principal) {

		return principal;
	}*/
    
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}