package com.facelift.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.facelift.security.oauth.CustomerOAuth2UserService;
import com.facelift.security.oauth.OAuth2LoginSuccessHandler;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired private CustomerOAuth2UserService oAuth2UserService;
	@Autowired private OAuth2LoginSuccessHandler oauth2LoginHandler;
	@Autowired private DatabaseLoginSuccessHandler databaseLoginHandler;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	private static final String[] PUBLIC_MATCHERS = {
			"/css/**",
			"/static/**",
			"/js/**",
			"/images/**",
			"/",
			"/newUser",
			"/forgotPassword",
			"/login",
			"/about",
			"/fonts/**",
			"/register",
			"/contact",
			"/productDetails",
			"shoppingCart/addItem",
			"/shop",
			"/faq",
			"/searchByCategory",
			"/searchBook"
	};

	@Configuration
	public class MyHttpSessionListener implements HttpSessionListener {
		@Override
		public void sessionCreated(HttpSessionEvent event) {
			event.getSession().setMaxInactiveInterval(30000000);
		}
	}

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/account_details", "/update_account_details",
					"/address_book/**","/checkout", "/place_order", "/process_paypal_order").authenticated()
			.anyRequest().permitAll()
			.and()
			.formLogin()
				.loginPage("/login")
				.usernameParameter("email")
				.successHandler(databaseLoginHandler)
				.permitAll()
			.and()
			.oauth2Login()
				.loginPage("/login")
				.userInfoEndpoint()
				.userService(oAuth2UserService)
				.and()
				.successHandler(oauth2LoginHandler)
			.and()
			.logout().permitAll()
			.and()
			.rememberMe()
				.key("1234567890_aBcDeFgHiJkLmNoPqRsTuVwXyZ")
				.tokenValiditySeconds(14 * 24 * 60 * 60)
			.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
			;			
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/images/**","/static/**","/rs-plugin/**", "/product/**", "/js/**", "/fonts/**", "/webjars/**","/facelift/**", "/fragments/**","/css/**");
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomerUserDetailsService();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}	
}
