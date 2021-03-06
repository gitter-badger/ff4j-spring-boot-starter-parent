package org.ff4j.spring.boot.constants;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
public class FeatureConstantsTest {
    @Test
    public void constructorInvocation() {
        try {
            final Constructor<FeatureConstants> c = FeatureConstants.class.getDeclaredConstructor();
            c.setAccessible(true);
            final FeatureConstants newInstance = c.newInstance();
            assertThat(newInstance).isNull();
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            assertThat(e).hasCauseExactlyInstanceOf(UnsupportedOperationException.class);
        }
    }

    @Test
    public void constantValuesTest() {
        assertThat(FeatureConstants.PARAM_GROUP).isEqualTo("groupName");
        assertThat(FeatureConstants.PARAM_ROLE).isEqualTo("role");
        assertThat(FeatureConstants.PARAM_NAME).isEqualTo("name");
        assertThat(FeatureConstants.PARAM_VALUE).isEqualTo("value");
        assertThat(FeatureConstants.PATH_PARAM_UID).isEqualTo("{uid}");
        assertThat(FeatureConstants.PATH_PARAM_ROLE).isEqualTo("{role}");
        assertThat(FeatureConstants.PATH_PARAM_GROUP).isEqualTo("{groupName}");
        assertThat(FeatureConstants.PATH_PARAM_NAME).isEqualTo("{name}");
        assertThat(FeatureConstants.PATH_PARAM_VALUE).isEqualTo("{value}");
        assertThat(FeatureConstants.RESOURCE_FF4J).isEqualTo("/ff4j");
        assertThat(FeatureConstants.RESOURCE_STORE).isEqualTo("/store");
        assertThat(FeatureConstants.RESOURCE_FEATURES).isEqualTo("/features");
        assertThat(FeatureConstants.RESOURCE_GROUPS).isEqualTo("/groups");
        assertThat(FeatureConstants.RESOURCE_FF4J_STORE).isEqualTo("/ff4j/store");
        assertThat(FeatureConstants.RESOURCE_FF4J_STORE_FEATURES).isEqualTo("/ff4j/store/features");
        assertThat(FeatureConstants.RESOURCE_FF4J_STORE_GROUPS).isEqualTo("/ff4j/store/groups");
        assertThat(FeatureConstants.RESOURCE_PROPERTY_STORE).isEqualTo("/propertyStore");
        assertThat(FeatureConstants.RESOURCE_PROPERTIES).isEqualTo("/properties");
        assertThat(FeatureConstants.RESOURCE_PROPERTIES_STORE_PROPERTIES).isEqualTo("/ff4j/propertyStore/properties");
        assertThat(FeatureConstants.RESOURCE_FF4J_PROPERTY_STORE).isEqualTo("/ff4j/propertyStore");
        assertThat(FeatureConstants.RESOURCE_CLEAR_CACHE).isEqualTo("/clearCache");
        assertThat(FeatureConstants.RESOURCE_FF4J_MONITORING).isEqualTo("/ff4j/monitoring");
    }
}
