package springboot.imagedemo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import springboot.imagedemo.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
	@Override
	protected void configure(HttpSecurity http) throws Exception 
	{
		http
			.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/h2-console").permitAll()//for h2-console
				.antMatchers("/signup").permitAll()
				.antMatchers(HttpMethod.GET, "/images/**").permitAll()
				.and()
			.authorizeRequests()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/", true)
				.permitAll()
				.and()
			.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/");
		
		http.csrf().disable();//for h2-console
        http.headers().frameOptions().disable();//for h2-console
	}
	
	@Autowired
	public void configureJpaBasedUsers(AuthenticationManagerBuilder auth, UserService userService) throws Exception
	{
		auth.userDetailsService(userService);
	}
}
