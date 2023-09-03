package work.shop.controller.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import work.shop.entity.Workspace;

@Data
@NoArgsConstructor
public class WorkshopWorkspace {
	
	private Long workspaceId;
	private String workspaceName;
	private String workspaceProductivity;
	private String workspaceTools;
	private String workspaceStorage;
	
	public WorkshopWorkspace(Workspace workspace) {
		workspaceId = workspace.getWorkspaceId();
		workspaceName = workspace.getWorkspaceName();
		workspaceProductivity = workspace.getWorkspaceProductivity();
		workspaceTools = workspace.getWorkspaceProductivity();
		workspaceStorage = workspace.getWorkspaceStorage();
	}
	//Last updated 9/1
	//6:59PM
}
