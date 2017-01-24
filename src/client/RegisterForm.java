
package client;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import networking.connections.Client;
import networking.messages.request.RegisterRequest;

/**
 * @author Martin Draganov
 */
public class RegisterForm extends JPanel {
	
	 	private JLabel firstNameLabel;
	    private JTextField firstNameTF;
	    private JLabel lastNameLabel;
	    private JTextField lastNameTF;
	    private JLabel usernameLaebl;
	    private JTextField usernameTF;
	    private JLabel passwordLabel;
	    private JPasswordField passwordField; 
	    private JButton saveBtn;
	    private JButton backBtn;
	    private Client connection;
	    
    public RegisterForm(Client connection) {
    	this.connection = connection;
        initComponents();
    }

    private void initComponents() {
      
        firstNameLabel = new JLabel();
        firstNameTF = new JTextField();
        lastNameLabel = new JLabel();
        lastNameTF = new JTextField();
        usernameLaebl = new JLabel();
        usernameTF = new JTextField();
        passwordLabel = new JLabel();
        passwordField = new JPasswordField();
        saveBtn = new JButton();
        backBtn = new JButton();

       
        {
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

            //---- saveBtn ----
            saveBtn.setText("Save");
            saveBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						connection.send(new RegisterRequest(usernameTF.getText(), passwordLabel.getText(), firstNameTF.getText(), lastNameTF.getText()));
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
                    //CardLayout cl = (CardLayout)getParent().getParent().getLayout(); 	//first parent is the current panel holding the button, we want the main panel
                    setVisible(false);
                }
            });

            GroupLayout RegisterPanelLayout = new GroupLayout(this);
            this.setLayout(RegisterPanelLayout);
            RegisterPanelLayout.setHorizontalGroup(
                RegisterPanelLayout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, RegisterPanelLayout.createSequentialGroup()
                        .addGroup(RegisterPanelLayout.createParallelGroup()
                            .addGroup(RegisterPanelLayout.createSequentialGroup()
                                .addGap(141, 141, 141)
                                .addGroup(RegisterPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addComponent(usernameLaebl, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                                    .addComponent(firstNameLabel, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                                    .addComponent(lastNameLabel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(passwordLabel, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)))
                            .addGroup(RegisterPanelLayout.createSequentialGroup()
                                .addContainerGap(199, Short.MAX_VALUE)
                                .addComponent(saveBtn, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)))
                        .addGap(48, 48, 48)
                        .addGroup(RegisterPanelLayout.createParallelGroup()
                            .addGroup(RegisterPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(firstNameTF, GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                                .addComponent(lastNameTF, GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                                .addComponent(usernameTF, GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                                .addComponent(passwordField, GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))
                            .addComponent(backBtn, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
                        .addGap(135, 135, 135))
            );
            RegisterPanelLayout.setVerticalGroup(
                RegisterPanelLayout.createParallelGroup()
                    .addGroup(RegisterPanelLayout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addGroup(RegisterPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(firstNameLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                            .addComponent(firstNameTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(RegisterPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(lastNameLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                            .addComponent(lastNameTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(RegisterPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(usernameLaebl)
                            .addComponent(usernameTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(RegisterPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(passwordLabel, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                            .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(107, 107, 107)
                        .addGroup(RegisterPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(saveBtn)
                            .addComponent(backBtn))
                        .addContainerGap(104, Short.MAX_VALUE))
            );
        }
       
    }

}
