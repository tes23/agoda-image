package com.agoda.image.calculator.constants;

/**
 * Created with IntelliJ IDEA.
 * User: tes
 * Date: 5/2/14
 * Time: 7:04 PM
 * To change this template use File | Settings | File Templates.
 */
public enum AttributumSelector {
    BOOKING_IMAGE_URL("data-resized");

    private String selector;

    private AttributumSelector(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }
}
