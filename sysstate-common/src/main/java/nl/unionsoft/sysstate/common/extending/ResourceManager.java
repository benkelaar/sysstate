package nl.unionsoft.sysstate.common.extending;

import java.util.List;

import nl.unionsoft.sysstate.common.dto.ResourceDto;

public interface ResourceManager<T> {

    public T getResource(ResourceDto resource);
    
    public void update(ResourceDto resource);
    
    public void remove(String name);
    
    public List<ResourceDto> getDefaultResources();

}
