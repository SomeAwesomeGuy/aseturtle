/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Login2.java
 *
 * Created on Nov 4, 2010, 7:53:09 AM
 */

package turtleturtleup;

/**
 *
 * @author blinnc
 */
public class AdminFrame extends javax.swing.JFrame {

    /** Creates new form Login2 */
    public AdminFrame() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backgroundPanel = new javax.swing.JPanel();
        turtleLabel = new javax.swing.JLabel();
        okButton = new javax.swing.JButton();
        lockServerCheckBox = new javax.swing.JCheckBox();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        backgroundPanel.setBackground(new java.awt.Color(210, 255, 238));

        turtleLabel.setFont(new java.awt.Font("Constantia", 0, 18)); // NOI18N
        turtleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        turtleLabel.setText("Admin Page");

        okButton.setText("OK");

        lockServerCheckBox.setText("Lock Server");
        lockServerCheckBox.setOpaque(false);

        cancelButton.setText("Cancel");

        javax.swing.GroupLayout backgroundPanelLayout = new javax.swing.GroupLayout(backgroundPanel);
        backgroundPanel.setLayout(backgroundPanelLayout);
        backgroundPanelLayout.setHorizontalGroup(
            backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(turtleLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundPanelLayout.createSequentialGroup()
                .addContainerGap(299, Short.MAX_VALUE)
                .addComponent(okButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelButton)
                .addContainerGap())
            .addGroup(backgroundPanelLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(lockServerCheckBox)
                .addContainerGap(310, Short.MAX_VALUE))
        );
        backgroundPanelLayout.setVerticalGroup(
            backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(turtleLabel)
                .addGap(18, 18, 18)
                .addComponent(lockServerCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 284, Short.MAX_VALUE)
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(okButton))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backgroundPanel;
    javax.swing.JButton cancelButton;
    javax.swing.JCheckBox lockServerCheckBox;
    javax.swing.JButton okButton;
    private javax.swing.JLabel turtleLabel;
    // End of variables declaration//GEN-END:variables

}
