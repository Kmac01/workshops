package work.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WorkshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkshopApplication.class, args);
		
		//successful creation of tables.
		//project saved in GitHub private repository w18.01
		
		//successful creation of Product methods tested with ARC
		//project saved in GitHub private repository w18/02
		
		//successful implementation and testing of project. 
		//project saved in GitHub private repository w18.03
		
		//expanding scope of project. new ERD located in folder
		
		//successful creation of new entity "Workspace"
		//successful implementation of new entity "Workspace"
		//project saved in GitHub private repository w18.04
		
		//additional CRUD methods added for all tables. 
		//writing comments
		//time running out. Shipping out

		
		
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~		
		//FINAL BUILD
		//18.07
		//SHIPPING OUT
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~		

	
	
	}
	
	//leaving Hibernate to generate tables each run because I expect to expand the scope of the project when goals are met.
	
	//error: Creation of certain classes or entities creates issues that do not resolve when updated.
	//error resolve: Though updating project does not resolve issue, running project/ saving project, resolves issue.
	
	//error: Expansion of scope of project causes issues with schema.
	//error resolve: Delete schema and just let Hibernate auto rebuild.

	//error: ARC queries for material and listing return code 500 errors
	//error resolve: productId used instead of workshopId in controller layer methods
	
	//error: @RequestBody annotation for get method for jointable entities causes errors. Methods work when @RequestBody removed, but there is a need to differentiate.
	//error resolve: remove @RequestBody, but leave getmapping to include unique pathways
	
	//error: Delete method for jointable entities function unexpectedly. either method fails because entity does not allow deletion before removal, or parent entity deleted as well.
	//error resolve: product.get*CHILD_ENTITY*().remove(*VARIABLE*);
	
	
	
	//Last updated 9/2
	//10:17PM
}
