package work.shop.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Material {
	
	//These annotations auto increment the Id values. This is true for all entities
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long materialId;
	private String materialName;
	private String materialDangers;
	private String materialCost;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "materials", cascade = CascadeType.PERSIST)
	private Set<Product> products = new HashSet<>();
	
	
	
	//last updated 8/28
	//10:33PM
}
