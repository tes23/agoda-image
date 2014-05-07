package com.agoda.image.calculator.constants;

/**
 * Created with IntelliJ IDEA.
 * User: tes
 * Date: 5/2/14
 * Time: 7:04 PM
 * To change this template use File | Settings | File Templates.
 */
public enum AttributeSelector {
    BOOKING_IMAGE_URL("href", "/max300/", "/max1024x768/"),;

    private String selector;
    private String expectedSubDir;  //expected sub directory which needs to be changed with @newSubDir
    private String newSubDir;

    private AttributeSelector(String selector, String expectedSubDir, String newSubDir) {
        this.selector = selector;
        this.expectedSubDir = expectedSubDir;
        this.newSubDir = newSubDir;
    }

    public String getSelector() {
        return selector;
    }

    public String getExpectedSubDir() {
        return expectedSubDir;
    }

    public String getNewSubDir() {
        return newSubDir;
    }
}
