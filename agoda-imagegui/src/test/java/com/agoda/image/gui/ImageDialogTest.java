package com.agoda.image.gui;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * User: acsikor
 * Date: 2014.06.08.
 * Time: 16:59
 */
public class ImageDialogTest {
    private ImageDialog imageDialog;

    @Before
    public void setUp() throws Exception {
        imageDialog = new ImageDialog();
    }

    @Test
    public void testInitializeDialog() throws Exception {
        assertNotNull(imageDialog.getContentPane());
    }
}
