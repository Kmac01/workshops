package work.shop.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import work.shop.entity.Listing;

public interface ListingDao extends JpaRepository<Listing, Long> {
	
	//last updated 9/1
	//12:50AM
}
