package nl.unionsoft.sysstate.dao;

import java.util.List;

import nl.unionsoft.sysstate.domain.InstanceLink;

public interface InstanceLinkDao {

    
    public void create(Long instanceFromId, Long instanceToId, String name);
    
    public void delete(Long instanceFromId, Long instanceToId, String name);
    
    public List<InstanceLink> getOutgoingInstanceLinks(Long instanceFromId, String name);
    
    public List<InstanceLink> getOutgoingInstanceLinks(Long instanceFromId);
    
    public List<InstanceLink> getIncommingInstanceLinks(Long instanceFromId);

}
