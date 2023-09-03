package work.shop.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import work.shop.entity.Material;

@Data
@NoArgsConstructor
public class WorkshopMaterial {
	private Long materialId;
	private String materialName;
	private String materialDangers;
	private String materialCost;
	
	public WorkshopMaterial (Material material ) {
		materialId = material.getMaterialId();
		materialName = material.getMaterialName();
		materialDangers = material.getMaterialDangers();
		materialCost = material.getMaterialCost();
	}
	
	
	//last updated 8/29
	//1:04AM
}
