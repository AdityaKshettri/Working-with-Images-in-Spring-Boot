package springboot.imagedemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import springboot.imagedemo.entity.Image;
import springboot.imagedemo.service.ImageService;
import springboot.imagedemo.service.UserService;

@Controller
public class ImageController 
{
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String index(Model model, Pageable pageable)
	{
		final Page<Image> page = imageService.findPage(pageable);
		model.addAttribute("page", page);
		model.addAttribute("authenticated", userService.isAuthenticated());
		model.addAttribute("currentUser", userService.getCurrentUser());
		if(page.hasPrevious()) {
			model.addAttribute("prev", pageable.previousOrFirst());
		}
		if(page.hasNext()) {
			model.addAttribute("next", pageable.next());
		}
		return "index";
	}
	
	@PostMapping("/images")
	public String createFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes)
	{
		try {
			imageService.createImage(file);
			redirectAttributes.addFlashAttribute("flash.message", "Successfully uploaded " + file.getOriginalFilename());
		} 
		catch (Exception e) {
			redirectAttributes.addFlashAttribute("flash.message", "Failed to upload " + file.getOriginalFilename());
		}
		return "redirect:/";
	}
	
	@GetMapping("/images/delete/{filename}")
	public String deleteFile(@PathVariable String filename, RedirectAttributes redirectAttributes)
	{
		try 
		{
			imageService.deleteImage(filename);
			redirectAttributes.addFlashAttribute("flash.message", "Successfully deleted " + filename);
		} 
		catch (Exception e) 
		{
			redirectAttributes.addFlashAttribute("flash.message", "Failed to delete " + filename + " => " + e.getMessage());
		}
		return "redirect:/";
	}
	
	@GetMapping("/images/{filename}")
	@ResponseBody
	public ResponseEntity<?> oneRawImage(@PathVariable String filename)
	{
		try 
		{
			Resource file = imageService.findOneImage(filename);
			return ResponseEntity.ok()
					.contentLength(file.contentLength())
					.contentType(MediaType.IMAGE_JPEG)
					.body(new InputStreamResource(file.getInputStream()));
		} 
		catch (Exception e) 
		{
			return ResponseEntity.badRequest()
					.body("Couldn't find " + filename + " => " + e.getMessage());
		}
	}
}
