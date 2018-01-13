package org.anderes.edu.hexagonal.cookbook.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class NutritiveValueDomanObjectTest {

    @Test
    public void shouldBeCorrectConstructor() {
        final NutritiveValueDomanObject value = createNutritiveValue();
        
        assertThat(value.getId(), is("00000000-aaaa-11111111-bbbb-22222222-cccc-FFFFFFFFFFFF"));
        assertThat(value.getCarb(), is(50));
        assertThat(value.getFat(), is(100));
        assertThat(value.getProtein(), is(200));
        assertThat(value.getCalorie(), is(300));
        
    }

    @Test
    public void shouldBeEquals() {
        final NutritiveValueDomanObject value_1 = createNutritiveValue();
        final NutritiveValueDomanObject value_2 = createNutritiveValue();
        
        assertNotSame(value_1, value_2);
        assertThat(value_1, is(value_2));
    }
    
    private NutritiveValueDomanObject createNutritiveValue() {
        return new NutritiveValueDomanObject("00000000-aaaa-11111111-bbbb-22222222-cccc-FFFFFFFFFFFF", 300, 200, 100, 50);
    }
}
