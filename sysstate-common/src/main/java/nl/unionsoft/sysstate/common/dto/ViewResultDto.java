package nl.unionsoft.sysstate.common.dto;

import java.util.ArrayList;
import java.util.List;

public class ViewResultDto {

    private final ViewDto view;
    private final List<ProjectDto> projects;
    private final List<EnvironmentDto> environments;
    private final List<InstanceDto> instances;
    private final List<ProjectEnvironmentDto> projectEnvironments;
    
    private final CountDto instanceCount;

    public ViewResultDto (ViewDto view) {
        this.view = view;
        instanceCount = new CountDto();
        projects = new ArrayList<ProjectDto>();
        environments = new ArrayList<EnvironmentDto>();
        instances = new ArrayList<InstanceDto>();
        projectEnvironments = new ArrayList<ProjectEnvironmentDto>();

    }

    public List<ProjectDto> getProjects() {
        return projects;
    }

    public List<EnvironmentDto> getEnvironments() {
        return environments;
    }

    public List<InstanceDto> getInstances() {
        return instances;
    }

    public CountDto getInstanceCount() {
        return instanceCount;
    }

    public List<ProjectEnvironmentDto> getProjectEnvironments() {
        return projectEnvironments;
    }

    public ViewDto getView() {
        return view;
    }

}
