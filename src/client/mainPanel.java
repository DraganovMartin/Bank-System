
package client;

import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author Martin Draganov
 */
public class mainPanel extends JPanel {
	
	private JLabel helloUserLabel;
    private JLabel helloLabel;
    private JLabel label1;
    private JLabel accessLabel;
    private ClientDataUIHelper user = null;
	
    public mainPanel(ClientDataUIHelper user) {
        this.user = user;
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Martin Draganov
        helloUserLabel = new JLabel();
        helloLabel = new JLabel();
        label1 = new JLabel();
        accessLabel = new JLabel();

        //======== panelMain ========
        {

            // JFormDesigner evaluation mark
            this.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), this.getBorder())); this.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});


            //---- helloLabel ----
            helloLabel.setText("Hello");
            helloLabel.setHorizontalAlignment(SwingConstants.CENTER);
            
          //---- helloUserLabel ----
            helloUserLabel.setText(user.getUsername());
            helloUserLabel.setHorizontalAlignment(SwingConstants.CENTER);

            //---- label1 ----
            label1.setText("Access mode : ");

            GroupLayout panelMainLayout = new GroupLayout(this);
            this.setLayout(panelMainLayout);
            panelMainLayout.setHorizontalGroup(
                panelMainLayout.createParallelGroup()
                    .addGroup(panelMainLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(helloLabel, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(helloUserLabel, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 164, Short.MAX_VALUE)
                        .addComponent(label1, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(accessLabel, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28))
            );
            panelMainLayout.setVerticalGroup(
                panelMainLayout.createParallelGroup()
                    .addGroup(panelMainLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(panelMainLayout.createParallelGroup()
                            .addComponent(helloUserLabel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                            .addComponent(helloLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(label1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                            .addComponent(accessLabel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE))
                        .addGap(348, 348, 348))
            );
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Martin Draganov
    
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
