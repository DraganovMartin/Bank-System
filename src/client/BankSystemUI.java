/**
 * @author Martin Draganov 
 */

package client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;


public class BankSystemUI {
	
	private JFrame BankSystemUI;
    private JPanel LoginPanel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField usernameTF;
    private JTextField passwordTF;
    private JButton loginBtn;
    private JButton registerBtn;
	
    public BankSystemUI() {
        initComponents();
    }

    /**
     * Initializing components, handling events and exceptions
     * @author Martin Draganov
     */
    private void initComponents() {
        
        BankSystemUI = new JFrame();
        LoginPanel = new JPanel();
        usernameLabel = new JLabel();
        passwordLabel = new JLabel();
        usernameTF = new JTextField();
        passwordTF = new JTextField();
        loginBtn = new JButton();
        registerBtn = new JButton();

        //======== BankSystemUI ========
        {
            BankSystemUI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            BankSystemUI.setTitle("Banking System");
            Container BankSystemUIContentPane = BankSystemUI.getContentPane();

            //======== LoginPanel ========
            {

                //---- usernameLabel ----
                usernameLabel.setText("Please enter username :");
                usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);

                //---- passwordLabel ----
                passwordLabel.setText("Please enter password :");
                passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);

                //---- loginBtn ----
                /**
                 *  If login data is correct, replaces current JPanel with LogedPanel 
                 */
                loginBtn.setText("Login");
                loginBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                });

                //---- registerBtn ----
                /**
                 *  If login data is correct, replaces current JPanel with RegisteredPanel 
                 */
                registerBtn.setText("Register");
                registerBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                });

                	
                GroupLayout LoginPanelLayout = new GroupLayout(LoginPanel);
                LoginPanel.setLayout(LoginPanelLayout);
                LoginPanelLayout.setHorizontalGroup(
                    LoginPanelLayout.createParallelGroup()
                        .addGroup(LoginPanelLayout.createSequentialGroup()
                            .addGroup(LoginPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addGroup(LoginPanelLayout.createSequentialGroup()
                                    .addGap(133, 133, 133)
                                    .addGroup(LoginPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addGroup(GroupLayout.Alignment.LEADING, LoginPanelLayout.createSequentialGroup()
                                            .addComponent(usernameLabel, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                                            .addGap(18, 18, 18)
                                            .addComponent(usernameTF, GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE))
                                        .addGroup(LoginPanelLayout.createSequentialGroup()
                                            .addComponent(passwordLabel, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                                            .addGap(18, 18, 18)
                                            .addComponent(passwordTF, GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE))))
                                .addGroup(GroupLayout.Alignment.LEADING, LoginPanelLayout.createSequentialGroup()
                                    .addContainerGap(169, Short.MAX_VALUE)
                                    .addComponent(loginBtn, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
                                    .addComponent(registerBtn, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 98, Short.MAX_VALUE)))
                            .addGap(103, 103, 103))
                );
                LoginPanelLayout.setVerticalGroup(
                    LoginPanelLayout.createParallelGroup()
                        .addGroup(LoginPanelLayout.createSequentialGroup()
                            .addGap(161, 161, 161)
                            .addGroup(LoginPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(usernameTF)
                                .addComponent(usernameLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(35, 35, 35)
                            .addGroup(LoginPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(passwordTF)
                                .addComponent(passwordLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(LoginPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addGroup(LoginPanelLayout.createSequentialGroup()
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(loginBtn, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
                                .addGroup(LoginPanelLayout.createSequentialGroup()
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(registerBtn, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)))
                            .addContainerGap(158, Short.MAX_VALUE))
                );
            }

            GroupLayout BankSystemUIContentPaneLayout = new GroupLayout(BankSystemUIContentPane);
            BankSystemUIContentPane.setLayout(BankSystemUIContentPaneLayout);
            BankSystemUIContentPaneLayout.setHorizontalGroup(
                BankSystemUIContentPaneLayout.createParallelGroup()
                    .addComponent(LoginPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            BankSystemUIContentPaneLayout.setVerticalGroup(
                BankSystemUIContentPaneLayout.createParallelGroup()
                    .addComponent(LoginPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            BankSystemUI.pack();
            // Starting window at center of the screen
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            BankSystemUI.setLocation(dim.width/2-BankSystemUI.getSize().width/2, dim.height/2-BankSystemUI.getSize().height/2);
            BankSystemUI.setVisible(true);
        }
        
    }
    
}
