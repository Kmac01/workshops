package work.shop.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import work.shop.controller.model.WorkshopData;
import work.shop.controller.model.WorkshopListing;
import work.shop.controller.model.WorkshopMaterial;
import work.shop.controller.model.WorkshopWorkspace;
import work.shop.dao.ListingDao;
import work.shop.dao.MaterialDao;
import work.shop.dao.ProductDao;
import work.shop.dao.WorkspaceDao;
import work.shop.entity.Listing;
import work.shop.entity.Material;
import work.shop.entity.Product;
import work.shop.entity.Workspace;

@Service
public class WorkshopService {
	@Autowired
	private ProductDao productDao;
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Product methods
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
	
	//sends product variable to Dao layer
	@Transactional(readOnly = false)
	public WorkshopData saveProduct(WorkshopData workshopData) {
		Long productId = workshopData.getProductId();
		Product product = findOrCreateProduct(productId);

		copyWorkshopFields(product, workshopData);
		return new WorkshopData(productDao.save(product));
		

	}
	
	//copies product entity fields
	private void copyWorkshopFields(Product proDuct, WorkshopData workshopData) {
		proDuct.setProductName(workshopData.getProductName());
		proDuct.setProductTimeCost(workshopData.getProductTimeCost());
		proDuct.setProductId(workshopData.getProductId());
		proDuct.setProductPrice(workshopData.getProductPrice());
		proDuct.setProductAmount(workshopData.getProductAmount());
	}

	
	private Product findOrCreateProduct(Long productId) {
		if(Objects.isNull(productId)) {
			return new Product();
		} else {
			return findProductById(productId);
		}
	}

	private Product findProductById(Long productId) {
		return productDao.findById(productId).orElseThrow(() -> new NoSuchElementException("Product ID not found " + productId));
		
	}
	
	@Transactional(readOnly = true)
	public List<WorkshopData> retrieveAllProducts(){
		List<Product> products = productDao.findAll();
		List<WorkshopData> result = new LinkedList<>();
		for(Product product : products) {
			WorkshopData shopdata = new WorkshopData(product);
			shopdata.getListings().clear();
			shopdata.getMaterials().clear();
			shopdata.getWorkspaces().clear();
			result.add(shopdata);
		}
		return result;
	}
	
	@Transactional(readOnly = true)
	public WorkshopData retrieveProductById(Long productId) {
		return new WorkshopData(findProductById(productId));
	}
	
	@Transactional(readOnly = false)
	public void deleteProductById(Long productId) {
		Product product = findProductById(productId);
		productDao.delete(product);
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// One to Many relationship, Listing service methods
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
	
	//copyfields method
	
	@Autowired
	private ListingDao listingDao;
	
	private void copyListingFields(Listing listing, WorkshopListing workshopListing) {
		listing.setListingName(workshopListing.getListingName());
		listing.setListingId(workshopListing.getListingId());
		listing.setListingPromotions(workshopListing.getListingPromotions());
		listing.setListingAdvertisers(workshopListing.getListingAdvertisers());
	}
	
	private Listing findOrCreateListing(Long productId, Long listingId) {
		if(Objects.isNull(listingId)) {
			return new Listing();
		}
		return findListingById(productId, listingId);
	}
	
	private Listing findListingById(Long productId, Long listingId) {
		Listing listing = listingDao.findById(listingId).orElseThrow(() -> new NoSuchElementException("Listing not found: ID " + listingId));
		if(listing.getProDuct().getProductId() != productId) {
			throw new IllegalArgumentException("Product: ID " + productId + " not found with Listing: ID " + listingId);
		}
		return listing;
	}
	
	@Transactional(readOnly = false)
	public WorkshopListing saveListing(Long productId, WorkshopListing workshopListing) {
		//relationship one to many based off product table
		Product product = findProductById(productId);
		Long listingId = workshopListing.getListingId();
		Listing listing = findOrCreateListing(productId, listingId);
		//calls copyListingFields to work with this save method
		copyListingFields(listing, workshopListing);
		//ProDuct is a bit of confusion from using assistance from my last project. Superficial blemish.
		listing.setProDuct(product);
		product.getListings().add(listing);
		//saving utilizing in ListingDao
		Listing dbListing = listingDao.save(listing);
		//return statement
		return new WorkshopListing(dbListing);
	}
	
	
	@Transactional(readOnly = true)
	public WorkshopListing retrieveListingById(Long productId, Long listingId) {

		return new WorkshopListing(findListingById(productId, listingId));
	}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
	//Many to Many relationship, Material service methods
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	@Autowired
	private MaterialDao materialDao;

	private void copyMaterialFields(Material material, WorkshopMaterial workshopMaterial) {
		material.setMaterialName(workshopMaterial.getMaterialName()); 
		material.setMaterialId(workshopMaterial.getMaterialId());
		material.setMaterialDangers(workshopMaterial.getMaterialDangers());
		material.setMaterialCost(workshopMaterial.getMaterialCost());
	}
	
	private Material findOrCreateMaterial(Long productId, Long materialId) {
		if(Objects.isNull(materialId)) {
			return new Material();
		}
		return findMaterialById(productId, materialId);
	}

	private Material findMaterialById(Long productId, Long materialId) {
		Material material = materialDao.findById(materialId).orElseThrow(() -> new NoSuchElementException("Material not found: ID " + materialId));
		
		boolean found = false;
		// I struggle with enhanced for loops so assistance from previous project used
		for(Product product : material.getProducts()) {
			if(product.getProductId() == productId) {
				found = true;
				break;
			}
		}
		if(!found) {
			throw new IllegalArgumentException("Product: ID " + productId + " does not use material: ID " + materialId);
		}
		return material;
	}
	
	@Transactional
	public WorkshopMaterial saveMaterial(Long productId, WorkshopMaterial workshopMaterial) {
		Product product = findProductById(productId);
		Long materialId = workshopMaterial.getMaterialId();
		Material material = findOrCreateMaterial(productId, materialId);
		//calls copyMaterialFields to work with this save method, same as listing version of method
		copyMaterialFields(material, workshopMaterial);
		material.getProducts().add(product);
		product.getMaterials().add(material);
		//creating variable to save in Dao layer
		Material dbMaterial = materialDao.save(material);
		return new WorkshopMaterial(dbMaterial);
	}
	
	
	@Transactional(readOnly = false)
	public void deleteMaterialById(Long productId, WorkshopMaterial workshopMaterial, Long materialId) {
		Product product = findProductById(productId);
		Material material = findMaterialById(productId, materialId);
		//calls the Product entity to remove the child entity to permit deletion
		product.getMaterials().remove(material);
		//calls the Dao to delete specified variable
		materialDao.delete(material);
	}
	
	//I do not see a need for a retrieve all method, as materials will vastly outnumber products
	@Transactional(readOnly = true)
	public WorkshopMaterial retrieveMaterialById(Long productId, Long materialId) {

		return new WorkshopMaterial(findMaterialById(productId, materialId));
	}
	
	
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
	//Many to Many relationship, Workspace service methods
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	@Autowired
	private WorkspaceDao workspaceDao;
	
	
	private void copyWorkspaceFields(Workspace workspace, WorkshopWorkspace workshopWorkspace) {
		workspace.setWorkspaceName(workshopWorkspace.getWorkspaceName());
		workspace.setWorkspaceId(workshopWorkspace.getWorkspaceId());
		workspace.setWorkspaceTools(workshopWorkspace.getWorkspaceTools());
		workspace.setWorkspaceProductivity(workshopWorkspace.getWorkspaceProductivity());
		workspace.setWorkspaceStorage(workshopWorkspace.getWorkspaceStorage());
	}
	
	private Workspace findOrCreateWorkspace(Long productId, Long workspaceId) {
		if(Objects.isNull(workspaceId)) {
			return new Workspace();
		}
		return findWorkspaceById(productId, workspaceId);
	}

	private Workspace findWorkspaceById(Long productId, Long workspaceId) {
		Workspace workspace = workspaceDao.findById(workspaceId).orElseThrow(() -> new NoSuchElementException("Workspace not found: ID " + workspaceId));
		
		boolean found = false;
		for(Product product : workspace.getProducts()) {
			if(product.getProductId() == productId) {
				found = true;
				break;
			}
		}
		if(!found) {
			throw new IllegalArgumentException("Product: ID " + productId + " not made in workspace: ID " + workspaceId);
		}
		return workspace;
	}
	
	@Transactional
	public WorkshopWorkspace saveWorkspace(Long productId, WorkshopWorkspace workshopWorkspace) {
		Product product = findProductById(productId);
		Long workspaceId = workshopWorkspace.getWorkspaceId();
		Workspace workspace = findOrCreateWorkspace(productId, workspaceId);
		copyWorkspaceFields(workspace, workshopWorkspace);
		workspace.getProducts().add(product);
		product.getWorkspaces().add(workspace);
		Workspace dbWorkspace = workspaceDao.save(workspace);
		return new WorkshopWorkspace(dbWorkspace);
	}
	
	
	@Transactional(readOnly = false)
	public void deleteWorkspaceById(Long productId, WorkshopWorkspace workshopWorkspace, Long workspaceId) {
		Product product = findProductById(productId);
		Workspace workspace = findWorkspaceById(productId, workspaceId);
		//calls the Product entity to remove the child entity to permit deletion
		product.getWorkspaces().remove(workspace);
		//calls the Dao to delete specified variable
		workspaceDao.delete(workspace);
	}
	
	//I do not see a need for a retrieve all method, as workspaces will not be forgotten and will be easily remembered.
	@Transactional(readOnly = true)
	public WorkshopWorkspace retrieveWorkspaceById(Long productId, Long workspaceId) {

		return new WorkshopWorkspace(findWorkspaceById(productId, workspaceId));
	}
	
	
	
	
	//last updated 9/2
	//10:10PM
}
