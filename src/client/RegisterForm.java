/*
 * Created by JFormDesigner on Sat Jan 21 18:34:05 EET 2017
 */

package client;

import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author Martin Draganov
 */
public class RegisterForm extends JPanel {
    public RegisterForm() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Martin Draganov
        firstNameLabel = new JLabel();
        firstNameTF = new JTextField();
        lastNameLabel = new JLabel();
        lastNameTF = new JTextField();
        usernameLaebl = new JLabel();
        usernameTF = new JTextField();
        passwordLabel = new JLabel();
        textField1 = new JTextField();

        //======== this. ========
        {

            // JFormDesigner evaluation mark
            this.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), this.getBorder())); this.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});


            //---- firstNameLabel ----
            firstNameLabel.setText("Enter your first name :");
            firstNameLabel.setHorizontalAlignment(SwingConstants.CENTER);

            //---- lastNameLabel ----
            lastNameLabel.setText("Enter your last name :");
            lastNameLabel.setHorizontalAlignment(SwingConstants.CENTER);

            //---- usernameLaebl ----
            usernameLaebl.setText("Enter username :");
            usernameLaebl.setHorizontalAlignment(SwingConstants.CENTER);

            //---- passwordLabel ----
            passwordLabel.setText("Enter password :");
            passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);

            GroupLayout thisLayout = new GroupLayout(this);
            this.setLayout(thisLayout);
            thisLayout.setHorizontalGroup(
                thisLayout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, thisLayout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(usernameLaebl, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                            .addComponent(firstNameLabel, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                            .addComponent(lastNameLabel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(passwordLabel, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE))
                        .addGap(48, 48, 48)
                        .addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(firstNameTF, GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                            .addComponent(lastNameTF, GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                            .addComponent(usernameTF, GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                            .addComponent(textField1, GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))
                        .addGap(135, 135, 135))
            );
            thisLayout.setVerticalGroup(
                thisLayout.createParallelGroup()
                    .addGroup(thisLayout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(firstNameLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                            .addComponent(firstNameTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(lastNameLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                            .addComponent(lastNameTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(usernameLaebl)
                            .addComponent(usernameTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(passwordLabel, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                            .addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(243, Short.MAX_VALUE))
            );
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Martin Draganov
    private JLabel firstNameLabel;
    private JTextField firstNameTF;
    private JLabel lastNameLabel;
    private JTextField lastNameTF;
    private JLabel usernameLaebl;
    private JTextField usernameTF;
    private JLabel passwordLabel;
    private JTextField textField1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
