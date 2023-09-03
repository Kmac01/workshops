package work.shop.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import work.shop.entity.Listing;
import work.shop.entity.Material;
import work.shop.entity.Product;
import work.shop.entity.Workspace;

//DTO separated into respective classes because its easier to visualize without becoming cluttered.
//Also allows for upscaling without cramming all of it into one class
@Data
@NoArgsConstructor
public class WorkshopData {
	private Long productId;
	private String productName;
	private String productTimeCost;
	private String productPrice;
	private String productAmount;
	private Set<WorkshopMaterial> materials = new HashSet<>();
	private Set<WorkshopListing> listings = new HashSet<>();
	private Set<WorkshopWorkspace> workspaces = new HashSet<>();
	
	public WorkshopData(Product proDuct) {
		productId = proDuct.getProductId();
		productName = proDuct.getProductName();
		productTimeCost = proDuct.getProductTimeCost();
		productPrice = proDuct.getProductPrice();
		productAmount = proDuct.getProductAmount();
		
		for(Material material : proDuct.getMaterials()) {
			materials.add(new WorkshopMaterial(material));
		}
		
		for(Listing listing : proDuct.getListings()) {
			listings.add(new WorkshopListing(listing));
		}
		
		for(Workspace workspace : proDuct.getWorkspaces()) {
			workspaces.add(new WorkshopWorkspace(workspace));
		}
		
		//primary DTO. expand with further entries at this point
	}
	
	
	
	//last updated 9/1
	//7:03PM
}
