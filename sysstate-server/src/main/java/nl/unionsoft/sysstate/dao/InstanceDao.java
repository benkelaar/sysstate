package nl.unionsoft.sysstate.dao;

import java.util.List;
import java.util.Optional;

import nl.unionsoft.sysstate.common.dto.FilterDto;
import nl.unionsoft.sysstate.domain.Instance;

public interface InstanceDao {

    public void createOrUpdate(Instance instance);

    public List<Instance> getInstancesForProjectAndEnvironment(Long projectId, Long environmentId);

    public List<Instance> getInstancesForProjectAndEnvironment(String projectName, String environmentName);

    public List<Instance> getInstancesForProjectEnvironment(Long projectEnvironmentId);

    public List<Instance> getInstances();

    /**
     * Gets instances for a custom filter
     * 
     * @param filter
     * @return
     */
    public List<Instance> getInstances(FilterDto filter);

    /**
     * Gets instances for a predefined filter.
     * 
     * @param filterId
     * @return
     */
    public List<Instance> getInstances(Long filterId);

    public Optional<Instance> getInstance(Long instanceId);

    public Optional<Instance> getInstanceByReference(String reference);

    public void delete(Long instanceId);

    public List<Instance> getInstancesForEnvironment(Long environmentId);

}
