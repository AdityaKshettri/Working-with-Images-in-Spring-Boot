package springboot.imagedemo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import springboot.imagedemo.entity.Image;
import springboot.imagedemo.repository.ImageRepository;

@Service
public class ImageService 
{
	private static String UPLOAD_ROOT = "images";
	
	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	@Autowired
	private UserService userService;
	
	public Page<Image> findPage(Pageable pageable) {
		return imageRepository.findAll(pageable);
	}
	
	public Resource findOneImage(String filename) {
		return resourceLoader.getResource("file:" + UPLOAD_ROOT + "/" + filename);
	}
	
	public void createImage(MultipartFile file) throws IOException
	{
		if(!file.isEmpty())
		{
			Files.copy(file.getInputStream(), Paths.get(UPLOAD_ROOT, file.getOriginalFilename()));
			imageRepository.save(new Image(file.getOriginalFilename(), userService.getCurrentUser()));
		}
	}
	
	public void deleteImage(String filename) throws IOException
	{
		final Image byName = imageRepository.findByName(filename);
		imageRepository.delete(byName);
		Files.deleteIfExists(Paths.get(UPLOAD_ROOT, filename));
	}
}
