package nl.unionsoft.sysstate.converter;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import nl.unionsoft.commons.converter.BidirectionalConverter;
import nl.unionsoft.commons.converter.Converter;
import nl.unionsoft.commons.converter.ListConverter;
import nl.unionsoft.sysstate.common.dto.InstanceDto;
import nl.unionsoft.sysstate.domain.Instance;
import nl.unionsoft.sysstate.domain.InstanceProperty;

import org.springframework.stereotype.Service;

@Service("instanceConverter")
public class InstanceConverter implements Converter<InstanceDto, Instance> {

    @Inject
    @Named("projectEnvironmentConverter")
    private ProjectEnvironmentConverter projectEnvironmentConverter;

    @Inject
    @Named("instancePropertiesConverter")
    private BidirectionalConverter<Map<String, String>, List<InstanceProperty>> instancePropertiesConverter;

    @Inject
    @Named("stateConverter")
    private StateConverter stateConverter;

    public InstanceDto convert(final Instance instance) {
        InstanceDto result = null;
        if (instance != null) {
            result = new InstanceDto();
            result.setId(instance.getId());
            result.setName(instance.getName());
            result.setProjectEnvironment(projectEnvironmentConverter.convert(instance.getProjectEnvironment()));
            result.setHomepageUrl(instance.getHomepageUrl());
            result.setPluginClass(instance.getPluginClass());
            result.setConfiguration(instancePropertiesConverter.convert(instance.getInstanceProperties()));
            result.setEnabled(instance.isEnabled());
            result.setTags(instance.getTags());
            result.setReference(instance.getReference());
            result.setRefreshTimeout(instance.getRefreshTimeout());
        }
        return result;
    }
}
