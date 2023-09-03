package work.shop.entity;

//Checked that java version is correct
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
//having issues with imports. Have restarted multiple times because of this.
//pay very close attention to imports, and that the right libraries are sourced.
@Entity
//with ANY error, the data annotation flags an error. It is irritating, but resolves easily
@Data
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;
	private String productName;
	private String productTimeCost;
	private String productPrice;
	private String productAmount;
	
	//mapping the Material_Consumption jointable between Product, and Material tables
	//Though the name is arbitrary (it is a join table) it is a vizualizer for what I intend to be a store
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "material_consumption", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "material_id"))
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<Material> materials = new HashSet<>();
	
	
	//mapping Product table to Listing table. Used proDuct, because theres no other way to capitalize the middle and if i dont my brain explodes
	//Because my intention is to build upon what I've already done, I will add a listing page for products which will then branch off 
	//cascade type is all because if a product is "Sold" then there should be no remaining listing for said product.
	@OneToMany(mappedBy = "proDuct", cascade = CascadeType.ALL, orphanRemoval = true)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<Listing> listings = new HashSet<>();
	
	
	// Expanded scope of project to include additional join table.
	// Previous iteration was workshop, but having an entity named after the project not being the core focus of said project is not well.
	// Function is identical to Material in concept, but will be necessary for calculating workshop function, and so it is separate.
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "product_workspace", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "workspace_id"))
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<Workspace> workspaces = new HashSet<>();
	
	
	
	//last updated 9/1
	//6:42PM
}
