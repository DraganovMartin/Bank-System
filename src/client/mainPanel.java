/*
 * Created by JFormDesigner on Sat Jan 21 22:45:10 EET 2017
 */

package client;

import java.awt.*;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author Martin Draganov
 */
public class mainPanel extends JPanel {
	
	    private JPanel panelMain;
	    private JLabel helloUserLabel;
	    private JLabel helloLabel;
	    private JLabel label1;
	    private JLabel accessLabel;
	    private JButton createBankAccBtn;
	    private JButton balanceCheck;
	    private JButton changePassBtn;
	    private JButton depositBtn;
	    private JButton transferBtn;
	    private JButton transactionBtn;
	    private JButton withdrawBtn;
	    private JButton logoutBtn;
	    //private JButton backBtn;
	    private ClientDataUIHelper user;
	    
    public mainPanel(ClientDataUIHelper user) {
    	this.user = user;
        initComponents();
    }

    private void initComponents() {
        panelMain = new JPanel();
        helloUserLabel = new JLabel();
        helloLabel = new JLabel();
        label1 = new JLabel();
        accessLabel = new JLabel();
        createBankAccBtn = new JButton();
        balanceCheck = new JButton();
        changePassBtn = new JButton();
        depositBtn = new JButton();
        transferBtn = new JButton();
        transactionBtn = new JButton();
        withdrawBtn = new JButton();
        logoutBtn = new JButton();
        //backBtn = new JButton();

        //======== panel1 ========
        {

            this.setLayout(new CardLayout());

            //======== panelMain ========
            {

                //---- helloLabel ----
                helloLabel.setText("Hello");
                helloLabel.setHorizontalAlignment(SwingConstants.CENTER);
                
              //---- helloUserLabel ----
                helloUserLabel.setText(user.getUsername());
                helloUserLabel.setHorizontalAlignment(SwingConstants.CENTER);

                //---- label1 ----
                label1.setText("Access mode : ");

                //---- createBankAccBtn ----
                createBankAccBtn.setText("Create Account");

                //---- balanceCheck ----
                balanceCheck.setText("Balance Info");

                //---- changePassBtn ----
                changePassBtn.setText("Change Password");

                //---- depositBtn ----
                depositBtn.setText("Deposit Money");

                //---- transferBtn ----
                transferBtn.setText("Transfer");
                transferBtn.setActionCommand("Transfer ");

                //---- transactionBtn ----
                transactionBtn.setText("Transaction History");
                transactionBtn.setHorizontalAlignment(SwingConstants.LEFT);

                //---- withdrawBtn ----
                withdrawBtn.setText("Withdraw");

                //---- logoutBtn ----
                logoutBtn.setText("Logout");
                
              //---- backBtn ----
               // backBtn.setText("Back");

                GroupLayout panelMainLayout = new GroupLayout(panelMain);
                panelMain.setLayout(panelMainLayout);
                panelMainLayout.setHorizontalGroup(
                    panelMainLayout.createParallelGroup()
                        .addGroup(panelMainLayout.createSequentialGroup()
                            .addGap(29, 29, 29)
                            .addComponent(helloLabel, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addGroup(panelMainLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(transferBtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(transactionBtn, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(helloUserLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(changePassBtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(createBankAccBtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(112, 112, 112)
                            .addGroup(panelMainLayout.createParallelGroup()
                                .addGroup(panelMainLayout.createSequentialGroup()
                                    .addGap(0, 0, Short.MAX_VALUE)
                                    .addComponent(label1, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(accessLabel, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
                                    .addGap(28, 28, 28))
                                .addGroup(panelMainLayout.createSequentialGroup()
                                    .addGroup(panelMainLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(balanceCheck, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(depositBtn)
                                        .addComponent(withdrawBtn, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(logoutBtn, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE))
                                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
//                        .addGroup(GroupLayout.Alignment.TRAILING, panelMainLayout.createSequentialGroup()
//                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                                .addComponent(backBtn, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
//                                .addGap(192, 192, 192))
                );
                panelMainLayout.setVerticalGroup(
                    panelMainLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, panelMainLayout.createSequentialGroup()
                            .addGroup(panelMainLayout.createParallelGroup()
                                .addGroup(panelMainLayout.createSequentialGroup()
                                    .addGap(21, 21, 21)
                                    .addGroup(panelMainLayout.createParallelGroup()
                                        .addComponent(helloUserLabel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(helloLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(label1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(accessLabel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGap(61, 61, 61)
                                    .addGroup(panelMainLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(balanceCheck)
                                        .addComponent(createBankAccBtn))
                                    .addGap(27, 27, 27)
                                    .addGroup(panelMainLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(depositBtn)
                                        .addComponent(changePassBtn))
                                    .addGap(37, 37, 37)
                                    .addComponent(transferBtn))
                                .addGroup(panelMainLayout.createSequentialGroup()
                                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(withdrawBtn)))
                            .addGap(31, 31, 31)
                            .addGroup(panelMainLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(transactionBtn)
                                .addComponent(logoutBtn))
                            .addGap(50, 100, 200))
//                            .addComponent(backBtn)
//                            .addGap(30, 30, 30))
                );
            }
            this.add(panelMain, "card1");
        }
       
    }
}
