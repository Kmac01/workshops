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


//added entity after project completion
//many to many relationship with Product
//similar function to material but separate due to necessity to differentiate the two easily
@Entity
@Data
public class Workspace {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long workspaceId;
	private String workspaceName;
	private String workspaceProductivity;
	private String workspaceTools;
	private String workspaceStorage;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "workspaces", cascade = CascadeType.PERSIST)
	private Set<Product> products = new HashSet<>();
	

	//Last updated 9/1
	//7:06PM
}
