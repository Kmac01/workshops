package work.shop.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

//Originally was supposed to be Shop, but Listing fits this slot much better.
//shop will be potential join table later on
@Entity
@Data
public class Listing {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long listingId;
	private String listingName;
	private String listingPromotions;
	private String listingAdvertisers;
	
	//answering product entity's join
	//again, cascade is set to all because if a product dissappears naturally its page would as well
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "product_id")
	private Product proDuct;
	
	//last updated 8/28
	//10:33PM
}
