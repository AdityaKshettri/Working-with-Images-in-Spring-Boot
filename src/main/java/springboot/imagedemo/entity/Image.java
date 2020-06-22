package springboot.imagedemo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Image 
{
	@Id
	@GeneratedValue
	private int id;
	
	private String name;
	
	@OneToOne
	private User owner;
	
	public Image() {}
	
	public Image(String name, User owner) {
		this.name = name;
		this.owner = owner;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "Image [id=" + id + ", name=" + name + ", owner=" + owner + "]";
	}
}
