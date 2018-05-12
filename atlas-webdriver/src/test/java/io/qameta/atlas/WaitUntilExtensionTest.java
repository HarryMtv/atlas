package io.qameta.atlas;

import io.qameta.atlas.exception.WaitUntilException;
import io.qameta.atlas.extensions.WaitUntilExtension;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import static io.qameta.atlas.matcher.DisplayedMatcher.displayed;
import static io.qameta.atlas.testdata.ObjectFactory.mockWebElement;
import static org.mockito.Mockito.when;

/**
 * @author kurau (Yuri Kalinin)
 */
public class WaitUntilExtensionTest {

    private static final Matcher<WebElement> DISPLAYED_MATCHER = displayed();

    private static final boolean IS_DISPLAYED = true;
    private static final boolean NOT_DISPLAYED = false;

    private WebElement baseElement = mockWebElement();
    private AtlasWebElement atlasWebElement;


    @Before
    public void createAtlasElementWithExtension() {
        atlasWebElement = new Atlas()
                .extension(new WaitUntilExtension())
                .create(baseElement, AtlasWebElement.class);
    }

    @Test
    public void shouldPassOneArgumentWaitUntilMethod() {
        when(baseElement.isDisplayed()).thenReturn(IS_DISPLAYED);
        atlasWebElement.waitUntil(DISPLAYED_MATCHER);
    }

    @Test
    public void shouldPassTwoArgumentWaitUntilMethod() {
        String message = RandomStringUtils.randomAlphanumeric(10);

        when(baseElement.isDisplayed()).thenReturn(IS_DISPLAYED);
        atlasWebElement.waitUntil(message, DISPLAYED_MATCHER);
    }

    @Test(expected = WaitUntilException.class)
    public void shouldThrowExceptionInOneArgumentWaitUntilMethod() {
        when(baseElement.isDisplayed()).thenReturn(NOT_DISPLAYED);
        atlasWebElement.waitUntil(DISPLAYED_MATCHER);
    }

    @Test(expected = WaitUntilException.class)
    public void shouldThrowExceptionInTwoArgumentWaitUntilMethod() {
        String message = RandomStringUtils.randomAlphanumeric(10);

        when(baseElement.isDisplayed()).thenReturn(NOT_DISPLAYED);
        atlasWebElement.waitUntil(message, DISPLAYED_MATCHER);
    }

}