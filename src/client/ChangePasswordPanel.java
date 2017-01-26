package client;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;
import javax.swing.GroupLayout;

import networking.connections.Client;
import networking.messages.request.ChangePasswordRequest;

/**
 * @author Martin Draganov
 */
public class ChangePasswordPanel extends JPanel {

    private JLabel oldPassLabel;
    private JPasswordField oldPassField;
    private JLabel newPassLabel;
    private JPasswordField newPassField;
    private JLabel repPassLabel;
    private JPasswordField repPassField;
    private JButton saveBtn;
    private JButton backBtn;
    private ClientDataUIHelper user;
    private Client connection;

    public ChangePasswordPanel(ClientDataUIHelper user,Client connection) {
    	this.connection = connection;
        this.user = user;
        initComponents();
    }

    private void initComponents() {
        oldPassLabel = new JLabel();
        oldPassField = new JPasswordField();
        newPassLabel = new JLabel();
        newPassField = new JPasswordField();
        repPassLabel = new JLabel();
        repPassField = new JPasswordField();
        saveBtn = new JButton();
        backBtn = new JButton();

        //---- oldPassLabel ----
        oldPassLabel.setText("Old password :");

        //---- newPassLabel ----
        newPassLabel.setText("New password :");

        //---- repPassLabel ----
        repPassLabel.setText("Repeat password :");

        //---- saveBtn ----
        saveBtn.setText("Save");
        
        saveBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// get data from oldPassField - PasswordField, newPassField - PasswordField, repPassField - PasswordField
				try {
					connection.send(new ChangePasswordRequest(oldPassField.getText(), newPassField.getText()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				setVisible(false);
			}
		});

        //---- backBtn ----
        backBtn.setText("Back");
        backBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) getParent().getParent().getLayout(); 	//first parent is the current panel holding the button, we want the main panel
                setVisible(false);
                cl.show(getParent().getParent(), "mainCard");
            }
        });

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(98, 98, 98)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                .addComponent(oldPassLabel, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                                                .addComponent(newPassLabel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(repPassLabel, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))
                                        .addGap(77, 77, 77))
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(140, 140, 140)
                                        .addComponent(saveBtn)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup()
                                .addComponent(backBtn)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(oldPassField, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                                        .addComponent(newPassField, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                                        .addComponent(repPassField, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)))
                        .addGap(86, 86, 86))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(oldPassLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                .addComponent(oldPassField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(newPassLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                .addComponent(newPassField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(repPassLabel, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                .addComponent(repPassField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup()
                                .addComponent(saveBtn, GroupLayout.Alignment.TRAILING)
                                .addComponent(backBtn, GroupLayout.Alignment.TRAILING))
                        .addGap(58, 58, 58))
        );

    }

}
