
package client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;

import networking.messageHandlers.MessageHandler;
import networking.messages.Message;

/**
 * @author Martin Draganov
 */
public class BankSystemUI extends JFrame implements MessageHandler {
    private JPanel LoginPanel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField usernameTF;
    private JPasswordField passwordTF;
    private JButton loginBtn;
    private JButton registerBtn;
    private JMenuBar mainFrameMenu;
    private JMenu aboutMenu;
    private JMenuItem devs;
    private Container BankSystemUIContentPane = null;
    private ClientDataUIHelper user = null;
    

    public BankSystemUI() {
        initComponents();
        user = new ClientDataUIHelper(null, null, null, "");
    }

    private void initComponents() {
        BankSystemUIContentPane = this.getContentPane();
        LoginPanel = new JPanel();
        usernameLabel = new JLabel();
        passwordLabel = new JLabel();
        usernameTF = new JTextField();
        passwordTF = new JPasswordField();
        loginBtn = new JButton();
        registerBtn = new JButton();
        mainFrameMenu = new JMenuBar();
        aboutMenu = new JMenu();
        devs = new JMenuItem();

        //======== mainFrame ========
        {
        	this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        	this.setTitle("Banking System");
            BankSystemUIContentPane.setLayout(new CardLayout());
            
          //======== mainFrameMenu ========
            {

                //======== aboutMenu ========
                {
                    aboutMenu.setText("About");

                    //---- devs ----
                    devs.setText("Developers");
                    aboutMenu.add(devs);
                    devs.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							JOptionPane.showMessageDialog((JMenuItem)e.getSource(), "Developed by" +"\n" +" Martin Draganov - UI" +"\n" +" Iliyan Kostov - Server" +"\n" +"Nikolay Nikolov - Database");
							
						}
					});
                }
                mainFrameMenu.add(aboutMenu);
            }

            //======== LoginPanel ========
            {

                //---- usernameLabel ----
                usernameLabel.setText("Please enter username :");
                usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);

                //---- passwordLabel ----
                passwordLabel.setText("Please enter password :");
                passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
                
                //---- loginBtn ----
                loginBtn.setText("Login");
                loginBtn.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						  	user.setUsetname(usernameTF.getText());
					        user.setPass(passwordTF.getPassword());
					        usernameTF.setText("");
					        passwordTF.setText("");
					        BankSystemUIContentPane.add(new mainPanel(user),"mainCard");
					        CardLayout cl = (CardLayout)(BankSystemUIContentPane.getLayout());
					        cl.show(BankSystemUIContentPane, "mainCard");
						
					}
				});

                //---- registerBtn ----
                registerBtn.setText("Register");
                registerBtn.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						   	BankSystemUIContentPane.add(new RegisterForm(),"registerCard");
					        CardLayout cl = (CardLayout)(BankSystemUIContentPane.getLayout());
					        cl.show(BankSystemUIContentPane, "registerCard");
						
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
                        .addGroup(LoginPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(mainFrameMenu, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(561, Short.MAX_VALUE))
                );
                LoginPanelLayout.setVerticalGroup(
                    LoginPanelLayout.createParallelGroup()
                        .addGroup(LoginPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(mainFrameMenu, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(211, 211, 211)
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
                            .addContainerGap(108, Short.MAX_VALUE))
                );
            }
            BankSystemUIContentPane.add(LoginPanel, "card1");
            this.pack();
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
            this.setVisible(true);
        }
        
    }
    
    /**
     * Handling responses from server
     */
	@Override
	public Message handle(Message message) {
		
		return null;
	}

 
}
