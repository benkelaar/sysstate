package nl.unionsoft.sysstate.common.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyMetaValue {

    private final String id;
    private final String defaultValue;
    private final String title;
    private final boolean nullable;
    private final Integer order;

    private Properties properties;
    private Map<String, String> lov;

    public PropertyMetaValue(String id, String title, boolean nullable, String defaultValue, Integer order) {

        this.id = id;
        this.title = title;
        this.nullable = nullable;
        this.defaultValue = defaultValue;

        this.properties = new Properties();
        this.lov = new HashMap<>();
        this.order = order;

    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public Map<String, String> getLov() {
        return lov;
    }

    public void setLov(final Map<String, String> lov) {
        this.lov = lov;
    }

    public boolean isNullable() {
        return nullable;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(final Properties properties) {
        this.properties = properties;
    }

    public Integer getOrder() {
        return order;
    }

}
