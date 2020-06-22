package springboot.imagedemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springboot.imagedemo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{
	public User findByUsername(String username);
}
