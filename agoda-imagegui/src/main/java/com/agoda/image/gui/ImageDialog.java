package com.agoda.image.gui;

import com.agoda.image.calculator.FileData;
import com.agoda.image.calculator.ImageData;
import com.agoda.image.calculator.constants.GlobalConstants;
import com.agoda.image.calculator.downloader.BookingImageDownloader;
import com.agoda.image.calculator.downloader.ImageDownloader;
import com.agoda.image.calculator.exceptions.AgodaImageException;
import com.agoda.image.calculator.manipulator.ImageManipulator;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class ImageDialog extends JDialog {
    private JPanel contentPane;
    private JButton resizeButton;
    private JButton closeButton;
    private JButton checkButton;
    private JTextField urlTextField;
    private JLabel urlLabel;
    private JList imagesJList;
    private JLabel foundImagesTextLabel;
    private JButton downloadToButton;
    private JTextField downloadToTextField;

    private ImageDownloader imageDownloader = new BookingImageDownloader();
    private ImageData imageData;
    private String usedURL;
    private String downloadDirectory;

    public ImageDialog() {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Booking.com - Hotel Images Downloader");
        getRootPane().setDefaultButton(closeButton);

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
                        List<FileData> fileDatas = imageData.getFileDatas();
                        listModel.addElement("=> Found images: " + fileDatas.size() + " pieces");

                        for (FileData fileData : fileDatas) {
                            listModel.addElement(fileData.getImageURL());
                        }
                        imagesJList.setModel(listModel);

                        enableResizeButtonByRelatedFields();
                    }
                } catch (AgodaImageException e1) {
                    showError(e1.getErrorCode().getMessage());
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        });
        downloadToButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = fc.showOpenDialog(ImageDialog.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    downloadDirectory = fc.getSelectedFile().getAbsolutePath();
                    downloadToTextField.setText(downloadDirectory);
                    enableResizeButtonByRelatedFields();
                }
            }
        });
        downloadToTextField.addMouseListener(getMouseListenerForDownloadToTextField());
    }

    private static void setLocationToCenter(Window window) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - window.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - window.getHeight()) / 2);
        window.setLocation(x, y);
    }

    private void onOK() {
        //TODO: refactor, pass list of FileDatas instead of initializing ImageManipulator continuously

        if (imageData != null && imageData.getFileDatas() != null) {
            try {
                List<String> invalidImages = new ArrayList<String>();
                imageDownloader.downloadImages(imageData, downloadDirectory);
                for (FileData fileData : imageData.getFileDatas()) {
                    ImageManipulator manipulator = new ImageManipulator(fileData);
                    try {
                        manipulator.resize();
                    } catch (AgodaImageException e) {
                        invalidImages.add(fileData.getFile().getName());
                    }
                }
                showInfo("All pictures have been downloaded and resized." +
                        "\nCheck the files within the folder '" + GlobalConstants.RESIZED_DIRECTORY_NAME + "'.\n\n" +
                        getInvalidImagesForShow(invalidImages));
            } catch (AgodaImageException e) {
                showError(e.getErrorCode().getMessage());
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    private String getInvalidImagesForShow(List<String> invalidImages) {
        StringBuilder builder = new StringBuilder("");
        if (!invalidImages.isEmpty()) {
            builder.append("Following pictures couldn't be resized:");
            for (String image : invalidImages) {
                builder.append("\n - ").append(image);
            }
        }
        return builder.toString();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void enableResizeButtonByRelatedFields() {
        boolean enable = StringUtils.isNotEmpty(downloadDirectory) && StringUtils.isNotEmpty(urlTextField.getText()) &&
                imageData != null && !imageData.getFileDatas().isEmpty();
        resizeButton.setEnabled(enable);
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        ImageDialog dialog = new ImageDialog();
        dialog.pack();
        setLocationToCenter(dialog);
        dialog.setVisible(true);
        System.exit(0);
    }

    private MouseListener getMouseListenerForDownloadToTextField() {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                downloadToButton.doClick();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        };
    }
}
