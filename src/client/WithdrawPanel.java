
package client;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import dataModel.Money;
import dataModel.models.Currency;
import networking.connections.Client;
import networking.messages.request.WithdrawRequest;

/**
 * @author Martin Draganov
 */
public class WithdrawPanel extends JPanel {
	
	private JLabel accLabel;
    private JLabel moneyLabel;
    private JTextField accountTF;
    private JTextField moneyTF;
    private JButton saveBtn;
    private JButton backBtn;
    private JTextField currencyTF;
    private ClientDataUIHelper user;
    private Client connection;
    
    public WithdrawPanel(ClientDataUIHelper user,Client connection) {
    	this.connection = connection;
    	this.user = user;
        initComponents();
    }

    private void initComponents() {
        
        accLabel = new JLabel();
        moneyLabel = new JLabel();
        accountTF = new JTextField();
        moneyTF = new JTextField();
        saveBtn = new JButton();
        backBtn = new JButton();
        currencyTF = new JTextField();

        //---- accLabel ----
        accLabel.setText("Account :");

        //---- moneyLabel ----
        moneyLabel.setText("Money :");

        //---- saveBtn ----
        saveBtn.setText("Save");
        
        saveBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO get info from accountTF - JTextField,  moneyTF - JTextField, amountTF - JTextField, currencyTF - JTextField
				Money money = Money.createMoney(new Currency(currencyTF.getText()), moneyTF.getText());
				try {
					connection.send(new WithdrawRequest(accountTF.getText(), money));
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
				CardLayout cl = (CardLayout)getParent().getParent().getLayout(); 	//first parent is the current panel holding the button, we want the main panel
				setVisible(false);
				cl.show(getParent().getParent(), "mainCard");
			}
        });
       
        

        // --------------------------------------------- THIS SEGMENT IS ONLY FOR POSITIONING AND ADDING COMPONENTS
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGap(117, 117, 117)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(saveBtn)
                        .addGroup(layout.createParallelGroup()
                            .addComponent(accLabel, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
                            .addComponent(moneyLabel, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)))
                    .addGap(97, 97, 97)
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(accountTF, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                                .addComponent(moneyTF, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(currencyTF, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
                        .addComponent(backBtn))
                    .addContainerGap(39, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGap(66, 66, 66)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(accLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                        .addComponent(accountTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(67, 67, 67)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(moneyLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(moneyTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(currencyTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                    .addGap(63, 63, 63)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(saveBtn)
                        .addComponent(backBtn))
                    .addContainerGap(70, Short.MAX_VALUE))
        );
    }
}