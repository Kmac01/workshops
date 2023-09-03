package work.shop.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import work.shop.entity.Listing;

@Data
@NoArgsConstructor
public class WorkshopListing {
	private Long listingId;
	private String listingName;
	private String listingPromotions;
	private String listingAdvertisers;
	
	public WorkshopListing (Listing listing) {
		listingId = listing.getListingId();
		listingName = listing.getListingName();
		listingPromotions = listing.getListingPromotions();
		listingAdvertisers = listing.getListingAdvertisers();
	}
	
	
	//last updated 8/29
	//1:04AM
}
