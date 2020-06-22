package springboot.imagedemo;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;

import springboot.imagedemo.entity.Image;
import springboot.imagedemo.entity.User;
import springboot.imagedemo.repository.ImageRepository;
import springboot.imagedemo.repository.UserRepository;

@Component
public class CommandLineRunnerClass implements CommandLineRunner
{
	private static String UPLOAD_ROOT = "images";
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ImageRepository imageRepository;

	@Override
	public void run(String... args) throws Exception 
	{
		FileSystemUtils.deleteRecursively(new File(UPLOAD_ROOT));
		Files.createDirectories(Paths.get(UPLOAD_ROOT));
		
		User adi = userRepository.save(new User("adi", "{noop}1234", "USER", "ADMIN"));
		User ksh = userRepository.save(new User("ksh", "{noop}1234", "USER"));
		
		FileCopyUtils.copy("Test file", new FileWriter(UPLOAD_ROOT + "/test"));
		imageRepository.save(new Image("test", adi));
		FileCopyUtils.copy("Test file2", new FileWriter(UPLOAD_ROOT + "/test2"));
		imageRepository.save(new Image("test2", adi));
		FileCopyUtils.copy("Test file3", new FileWriter(UPLOAD_ROOT + "/test3"));
		imageRepository.save(new Image("test3", ksh));
	}

}
