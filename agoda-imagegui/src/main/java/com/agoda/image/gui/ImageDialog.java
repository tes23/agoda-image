package com.agoda.image.gui;

import com.agoda.image.calculator.downloader.BookingImageDownloader;
import com.agoda.image.calculator.FileData;
import com.agoda.image.calculator.ImageData;
import com.agoda.image.calculator.downloader.ImageDownloader;
import com.agoda.image.calculator.exceptions.AgodaImageException;
import com.agoda.image.calculator.manipulator.ImageManipulator;

import javax.swing.*;
import java.awt.event.*;

public class ImageDialog extends JDialog {
    private JPanel contentPane;
    private JButton resizeButton;
    private JButton closeButton;
    private JButton checkButton;
    private JTextField urlTextField;
    private JLabel urlLabel;
    private JList imagesJList;
    private JLabel foundImagesTextLabel;

    private ImageDownloader imageDownloader = new BookingImageDownloader();
    private ImageData imageData;
    String usedURL;

    public ImageDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(resizeButton);

        resizeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resizeButton.setEnabled(false);
                try {
                    usedURL = urlTextField.getText();
                    imageData = imageDownloader.connectAndGetAvailableImages(usedURL);
                    if (imageData == null || imageData.getFileDatas() == null || imageData.getFileDatas().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No Images found", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else {
                        DefaultListModel listModel = new DefaultListModel();
                        for (FileData fileData : imageData.getFileDatas()) {
                            listModel.addElement(fileData.getImageURL());
                        }
                        imagesJList.setModel(listModel);
                        resizeButton.setEnabled(true);
                    }
                } catch (AgodaImageException e1) {
                    showError(e1.getErrorCode().getMessage());
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        });
    }

    private void onOK() {
        //TODO: refactor, pass list of FileDatas instead of initializing ImageManipulator continuously
        if(imageData != null && imageData.getFileDatas() != null) {
            for (FileData fileData : imageData.getFileDatas()) {
                ImageManipulator manipulator = new ImageManipulator(fileData);
                try {
                    manipulator.resize();
                } catch (AgodaImageException e) {
                    showError(e.getErrorCode().getMessage());
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }

    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        ImageDialog dialog = new ImageDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
