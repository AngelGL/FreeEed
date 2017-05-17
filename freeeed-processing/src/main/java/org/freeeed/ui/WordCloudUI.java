/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.freeeed.ui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import org.freeeed.analytics.WordCloudImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mark
 */
public class WordCloudUI extends javax.swing.JDialog {

    private static final Logger logger = LoggerFactory.getLogger(WordCloudUI.class);
    /**
     * A return status code - returned if Cancel button has been pressed
     */
    public static final int RET_CANCEL = 0;
    /**
     * A return status code - returned if OK button has been pressed
     */
    public static final int RET_OK = 1;

    private Frame parent;

    /**
     * Creates new form WordCloudUI
     */
    public WordCloudUI(Frame parent, boolean modal) {
        super(parent, modal);
        this.parent = parent;
        initComponents();

        // Close the dialog when Esc is pressed
        String cancelName = "cancel";
        InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
        ActionMap actionMap = rootPane.getActionMap();
        actionMap.put(cancelName, new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                doClose(RET_CANCEL);
            }
        });
    }

    /**
     * @return the return status of this dialog - one of RET_OK or RET_CANCEL
     */
    public int getReturnStatus() {
        return returnStatus;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        generateButton = new javax.swing.JButton();
        statusLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        imageWidthText = new javax.swing.JTextField();
        imageHeightText = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        topNTermsText = new javax.swing.JTextField();

        setTitle("Word cloud generator");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        generateButton.setText("Generate!");
        generateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateButtonActionPerformed(evt);
            }
        });

        statusLabel.setText("Status");

        jLabel1.setText("Image width");

        jLabel2.setText("Image height");

        imageWidthText.setText("600");

        imageHeightText.setText("600");

        jLabel3.setText("Top N terms");

        topNTermsText.setText("150");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(statusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(imageWidthText, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(imageHeightText, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 150, Short.MAX_VALUE)
                        .addComponent(generateButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(topNTermsText, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, okButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(generateButton)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(imageWidthText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imageHeightText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(topNTermsText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 180, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(okButton)
                    .addComponent(statusLabel))
                .addContainerGap())
        );

        rootPane.setDefaultButton(okButton);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        doClose(RET_OK);
    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        doClose(RET_CANCEL);
    }//GEN-LAST:event_cancelButtonActionPerformed

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        doClose(RET_CANCEL);
    }//GEN-LAST:event_closeDialog

    private void generateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateButtonActionPerformed
        generateAndOpenWordCloud();
    }//GEN-LAST:event_generateButtonActionPerformed

    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton generateButton;
    private javax.swing.JTextField imageHeightText;
    private javax.swing.JTextField imageWidthText;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton okButton;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JTextField topNTermsText;
    // End of variables declaration//GEN-END:variables

    private int returnStatus = RET_CANCEL;

    @Override
    public void setVisible(boolean b) {
        if (b) {
            setLocationRelativeTo(parent);
        }
        super.setVisible(b);
    }

    private void generateAndOpenWordCloud() {
        statusLabel.setText("Status: working, may take some time. You can close this window.");
        final int width = Integer.parseInt(imageWidthText.getText().trim());
        final int height = Integer.parseInt(imageHeightText.getText().trim());        
        final int topNTerms = Integer.parseInt(topNTermsText.getText().trim());        
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String outputFile = "output/wordcloud.png";
                    try {
                        new WordCloudImpl().generateWordCloud(outputFile, width, height, topNTerms);
                        UtilUI.openImage(parent, outputFile);
                    } catch (Exception e) {
                        logger.error("Error generating wordcloud", e);
                    }
                } catch (final Exception e) {
                    logger.error("Oops generating wordcloud image", e);

                }
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        statusLabel.setText("Status: image generated");
                    }
                });
            }
        }).start();
    }
}
