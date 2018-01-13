package org.anderes.edu.hexagonal.cookbook.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ImageDomainObjectTest {

    @Test
    public void shouldBeCorrectConstructor() {
        final ImageDomainObject image = createImage();
        assertThat(image.getId(), is("00000000-aaaa-11111111-bbbb-22222222-cccc-FFFFFFFFFFFF"));
        assertThat(image.getUrl(), is("pasta.png"));
        assertThat(image.getDescription().isPresent(), is(true));
        assertThat(image.getDescription().get(), is("Pasta mit roter Sauce"));
    }
    
    @Test
    public void shouldBeEquals() {
        final ImageDomainObject image_1 = createImage();
        final ImageDomainObject image_2 = createImage();
        
        assertNotSame(image_1, image_2);
        assertThat(image_1, is(image_2));
    }
    
    private ImageDomainObject createImage() {
        return new ImageDomainObject("00000000-aaaa-11111111-bbbb-22222222-cccc-FFFFFFFFFFFF", "pasta.png", "Pasta mit roter Sauce");
    }
}
