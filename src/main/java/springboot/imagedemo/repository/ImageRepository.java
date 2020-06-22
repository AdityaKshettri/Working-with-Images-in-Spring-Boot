package springboot.imagedemo.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import springboot.imagedemo.entity.Image;

@Repository
public interface ImageRepository extends PagingAndSortingRepository<Image, Integer>
{
	public Image findByName(String name);
}
