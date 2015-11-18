package net.simonvoid.provider.impl;

import static org.testng.Assert.assertNotNull;

import net.simonvoid.provider.NameProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by stephan on 18.11.2015.
 */
public class NameProviderImplTest {
    private NameProvider nameProvider;

    @BeforeMethod
    public void setup() {
        nameProvider = new NameProviderImpl();
    }

    @Test
    public void testNameNotNull() {
        String name = nameProvider.getName();

        assertNotNull(name, "nameProvider.getName()");
    }
}
