package nl.unionsoft.sysstate.common.logic;

import java.util.List;
import java.util.Optional;

import nl.unionsoft.sysstate.common.dto.EnvironmentDto;

public interface EnvironmentLogic {

    public List<EnvironmentDto> getEnvironments();

    public EnvironmentDto getEnvironment(Long environmentId);

    public Long createOrUpdate(EnvironmentDto environment);

    public void delete(Long environmentId);

    public Optional<EnvironmentDto> getEnvironmentByName(String name);
    
    public EnvironmentDto findOrCreateEnvironment(String name);
    
    public void deleteEnvironmentsWithoutInstances();
    
}
