package com.agoda.image.gui;

import com.agoda.image.calculator.downloader.BookingImageDownloader;
import com.agoda.image.calculator.FileData;
import com.agoda.image.calculator.ImageData;
import com.agoda.image.calculator.downloader.ImageDownloader;
import com.agoda.image.calculator.exceptions.AgodaImageException;

import javax.swing.*;
import java.awt.event.*;

public class ImageDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton checkButton;
    private JTextField urlTextField;
    private JLabel urlLabel;
    private JList imagesJList;
    private JLabel foundImagesTextLabel;

    private ImageDownloader imageDownloader = new BookingImageDownloader();

    public ImageDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
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
                try {
                    ImageData imageData = imageDownloader.connectAndGetAvailableImages(urlTextField.getText());
                    DefaultListModel listModel = new DefaultListModel();
                    for (FileData fileData : imageData.getFileDatas()) {
                        listModel.addElement(fileData.getImageURL());
                    }
                    imagesJList.setModel(listModel);
                } catch (AgodaImageException e1) {
                    JOptionPane.showMessageDialog(null, e1.getErrorCode().getMessage());
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        });
    }

    private void onOK() {
// add your code here
        dispose();
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
