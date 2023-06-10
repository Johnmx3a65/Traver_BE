package com.parovsky.traver.config;

import com.parovsky.traver.role.Role;
import com.parovsky.traver.security.jwt.AuthTokenFilter;
import io.pivotal.cfenv.core.CfEnv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserDetailsService userDetailsService;

	@Autowired
	public WebSecurityConfig(@Qualifier("AppUserDetailsService") UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Enable CORS and disable CSRF
		http = http.cors().and().csrf().disable();

		// Set session management to stateless
		http = http
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and();

		// Set unauthorized requests exception handler
		http = http
				.exceptionHandling()
				.authenticationEntryPoint(
						(request, response, ex) -> response.sendError(
								HttpServletResponse.SC_UNAUTHORIZED,
								ex.getMessage()
						)
				)
				.and();

		// Set permissions on endpoints
		http.authorizeRequests()
				// public endpoints
				.antMatchers("/api/auth/**").permitAll()
				// private endpoints
				.antMatchers(HttpMethod.GET, "/current-user").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
				.antMatchers(HttpMethod.GET, "/photos/*").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
				.antMatchers(HttpMethod.GET, "/categories").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
				.antMatchers(HttpMethod.GET, "/categories/favorite").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
				.antMatchers(HttpMethod.GET, "/category/*").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
				.antMatchers(HttpMethod.GET, "/locations").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
				.antMatchers(HttpMethod.GET, "/locations/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
				.antMatchers(HttpMethod.GET, "/location/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
				.antMatchers(HttpMethod.POST, "/location/favourite/*").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
				.antMatchers(HttpMethod.DELETE, "/location/favourite/*").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
				// admin endpoints
				.anyRequest().hasRole(Role.ADMIN.name());

		// Add JWT token filter
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public CfEnv cfEnv() {
		return new CfEnv();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Collections.singletonList(System.getenv("ORIGIN")));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
		configuration.setExposedHeaders(Arrays.asList("x-auth-token", "set-cookie"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
