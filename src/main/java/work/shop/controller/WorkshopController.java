package work.shop.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import work.shop.controller.model.WorkshopData;
import work.shop.controller.model.WorkshopListing;
import work.shop.controller.model.WorkshopMaterial;
import work.shop.controller.model.WorkshopWorkspace;
import work.shop.service.WorkshopService;

//base mapping is /workshop. This goes after (http://localhost:8080)
//everything query builds upon this
@RestController
@RequestMapping("/workshop")
@Slf4j
public class WorkshopController {
	@Autowired
	private WorkshopService workshopService;
	
	//post ARC query URI: http://localhost:8080/workshop/product
	//example at src/test/resources/create-product.json 
	@PostMapping("/product")
	@ResponseStatus(code = HttpStatus.CREATED)
	public WorkshopData insertWorkshop(@RequestBody WorkshopData workshopData) {
		log.info("CREATING: product {}", workshopData);
		return workshopService.saveProduct(workshopData);
	}
	
	
	//put ARC query URI: http://localhost:8080/workshop/product/1
	//example at src/test/resources/update-product.json 
	@PutMapping("product/{workshopId}")
	public WorkshopData updateWorkshop(@PathVariable Long workshopId, @RequestBody WorkshopData workshopData) {
		workshopData.setProductId(workshopId);
		return workshopService.saveProduct(workshopData);
	}
	
	
	//get ARC query URI: http://localhost:8080/workshop
	//retrieves all products
	@GetMapping
	public List<WorkshopData> retrieveAllProducts(){
		log.info("Listing all Products");
		return workshopService.retrieveAllProducts();
	}
	
	//get ARC query URI: http://localhost:8080/workshop/1
	//retrieves one product
	@GetMapping("/{workshopId}")
	public WorkshopData retrieveProductById(@PathVariable Long workshopId) {
		log.info("Listing product: ID {}", workshopId);
		return workshopService.retrieveProductById(workshopId);
	}
	
	//delete ARC query URI: http://localhost:8080/workshop/1
	@DeleteMapping("/{workshopId}")
	public Map<String, String> deleteProductById(@PathVariable Long workshopId){
	log.info("Deleting product: ID {}", workshopId);
	workshopService.deleteProductById(workshopId);
	return Map.of("message", "DELETED: Product: ID " + workshopId);
	}
	
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//Listing queries
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	//post ARC query URI: http://localhost:8080/workshop/1/listing
	//example at src/test/resources/create-listing.json 
	@PostMapping("/{workshopId}/listing")
	@ResponseStatus(code = HttpStatus.CREATED)
	public WorkshopListing addListingToProduct(@PathVariable Long workshopId, @RequestBody WorkshopListing workshopListing) {
		log.info("Creating listing {} for product: ID {}", workshopListing, workshopId);
		return workshopService.saveListing(workshopId, workshopListing);
	}
	
	//put ARC query URI: http://localhost:8080/workshop/1/listing/1
	//example at src/test/resources/create-workspace.json 
	@PutMapping("/{workshopId}/listing/{listingId}")
	public WorkshopListing updateListing(@PathVariable Long workshopId, @RequestBody WorkshopListing workshopListing) {
		workshopListing.setListingId(workshopId);
		return workshopService.saveListing(workshopId, workshopListing);
	}
	
	//no need for delete method. If a product is sold, removing the product cascade deletes the child entity (listing).
	
	
	
	//get ARC query URI: http://localhost:8080/workshop/1/listing/1
	@GetMapping("/{workshopId}/listing/{listingId}")
	public WorkshopListing retrieveListingById(@PathVariable Long workshopId, @PathVariable Long listingId) {
		log.info("Listing product: ID {}", workshopId);
		return workshopService.retrieveListingById(workshopId, listingId);
	}
	
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//Material queries
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	
	//post ARC query URI: http://localhost:8080/workshop/1/material
	//example at src/test/resources/create-material.json 
	@PostMapping("/{workshopId}/material")
	@ResponseStatus(code = HttpStatus.CREATED)
	public WorkshopMaterial addMaterialToProduct(@PathVariable Long workshopId, @RequestBody WorkshopMaterial workshopMaterial) {
		log.info("Creating material {} for product: ID {}", workshopMaterial, workshopId);
		return workshopService.saveMaterial(workshopId, workshopMaterial);
	}
	
	//put ARC query URI: http://localhost:8080/workshop/1/material/1
	//example at src/test/resources/update-product.json 
	@PutMapping("/{workshopId}/material/{materialId}")
	public WorkshopMaterial updateMaterial(@PathVariable Long workshopId, @RequestBody WorkshopMaterial workshopMaterial) {
		workshopMaterial.setMaterialId(workshopId);
		return workshopService.saveMaterial(workshopId, workshopMaterial);
	}
	
	//delete ARC query URI: http://localhost:8080/workshop/1/material/1
	@DeleteMapping("/{workshopId}/material/{materialId}")
	public Map<String, String> deleteMaterialById(@PathVariable Long workshopId, @RequestBody WorkshopMaterial workshopmaterial, @PathVariable Long materialId){
	log.info("Deleting workspace: ID {}", materialId);
	workshopService.deleteMaterialById(workshopId, workshopmaterial, materialId);
	return Map.of("message", "DELETED: Workspace: ID " + materialId);
	}
	
	//get ARC query URI: http://localhost:8080/workshop/1/material/1
	@GetMapping("/{workshopId}/material/{materialId}")
	public WorkshopMaterial retrieveMaterialById(@PathVariable Long workshopId, @PathVariable Long materialId) {
		log.info("Listing product: ID {}", workshopId);
		return workshopService.retrieveMaterialById(workshopId, materialId);
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//Workspace queries
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	//post ARC query URI: http://localhost:8080/workshop/1/workspace
	//example at src/test/resources/create-workspace.json 
	@PostMapping("/{workshopId}/workspace")
	@ResponseStatus(code = HttpStatus.CREATED)
	public WorkshopWorkspace addWorkspaceToProduct(@PathVariable Long workshopId, @RequestBody WorkshopWorkspace workshopWorkspace) {
		log.info("Creating workspace {} for product: ID {}", workshopWorkspace, workshopId);
		return workshopService.saveWorkspace(workshopId, workshopWorkspace);
	}
	
	//put ARC query URI: http://localhost:8080/workshop/1/workspace/1
	//example at src/test/resources/create-workspace.json 
	@PutMapping("/{workshopId}/workspace/{workspaceId}")
	public WorkshopWorkspace updateWorkspace(@PathVariable Long workshopId, @RequestBody WorkshopWorkspace workshopWorkspace) {
		workshopWorkspace.setWorkspaceId(workshopId);
		return workshopService.saveWorkspace(workshopId, workshopWorkspace);
	}
	
	
	
	//delete ARC query URI: http://localhost:8080/workshop/1/workspace/1
	@DeleteMapping("/{workshopId}/workspace/{workspaceId}")
	public Map<String, String> deleteWorkspaceById(@PathVariable Long workshopId, @RequestBody WorkshopWorkspace workshopWorkspace, @PathVariable Long workspaceId){
	log.info("Deleting workspace: ID {}", workspaceId);
	workshopService.deleteWorkspaceById(workshopId, workshopWorkspace, workspaceId);
	return Map.of("message", "DELETED: Workspace: ID " + workspaceId);
	}
	
	
	//get ARC query URI: http://localhost:8080/workshop/1/workspace/1
	@GetMapping("/{workshopId}/workspace/{workspaceId}")
	public WorkshopWorkspace retrieveWorkspaceById(@PathVariable Long workshopId, @PathVariable Long workspaceId) {
		log.info("Listing product: ID {}", workshopId);
		return workshopService.retrieveWorkspaceById(workshopId, workspaceId);
	}
	
	
	
	
	
	//last updated 9/2
	//10:11pm

}
