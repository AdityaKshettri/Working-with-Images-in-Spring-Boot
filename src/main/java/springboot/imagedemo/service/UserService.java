package springboot.imagedemo.service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import springboot.imagedemo.entity.User;
import springboot.imagedemo.repository.UserRepository;

@Service
public class UserService implements UserDetailsService
{
	@Autowired
	private UserRepository userRepository;
	
	public User getCurrentUser()
	{
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User currentUser = userRepository.findByUsername(username);
		return currentUser;
	}
	
	@Transactional
	public void save(User newUser)
	{
		newUser.setPassword("{noop}" + newUser.getPassword());
		userRepository.save(newUser);
	}
	
	public boolean isAuthenticated()
	{
		if(getCurrentUser() == null)
			return false;
		return true;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		User user = userRepository.findByUsername(username);
		return new org.springframework.security.core.userdetails.User(
			user.getUsername(),
			user.getPassword(),
			Stream.of(user.getRoles())
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList()));
	}
	
}
