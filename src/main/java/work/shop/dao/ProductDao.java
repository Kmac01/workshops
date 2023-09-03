package work.shop.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import work.shop.entity.Product;

public interface ProductDao extends JpaRepository<Product, Long> {

	//last updated 8/29
	//1:04AM
}
