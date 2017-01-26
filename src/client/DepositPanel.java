
package client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import dataModel.Money;
import dataModel.models.Currency;
import networking.connections.Client;
import networking.messages.request.DepositRequest;

/**
 * @author Martin Draganov
 */
public class DepositPanel extends JPanel {
	
	private JLabel accLabel;
    private JTextField accountTF;
    private JLabel moneyLabel;
    private JTextField moneyTF;
    private JButton saveBtn;
    private JButton backBtn;
    private ClientDataUIHelper user;
    private JTextField currencyTF;
    private Client connection;
    
    
    public DepositPanel(ClientDataUIHelper user,Client connection) {
    	this.connection = connection;
    	this.user = user;
        initComponents();
    }

    private void initComponents() {
    	
        accLabel = new JLabel();
        accountTF = new JTextField();
        moneyLabel = new JLabel();
        moneyTF = new JTextField();
        saveBtn = new JButton();
        backBtn = new JButton();
        currencyTF = new JTextField();

        //======== this ========
        setMinimumSize(new Dimension(565, 401));
        setPreferredSize(new Dimension(565, 401));

        //---- accLabel ----
        accLabel.setText("Account :");
        accLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //---- moneyLabel ----
        moneyLabel.setText("Money :");
        moneyLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //---- saveBtn ----
        saveBtn.setText("Save");
        saveBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO get info from accountTF - JTextField,  moneyTF - JTextField, moneyTF - JTextField, currencyTF - JTextField
				Money money = Money.createMoney(new Currency(currencyTF.getText()), moneyTF.getText());
				try {
					connection.send(new DepositRequest(accountTF.getText(), money));
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
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addGap(112, 112, 112)
                            .addGroup(layout.createParallelGroup()
                                .addComponent(accLabel, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
                                .addComponent(moneyLabel, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)))
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(saveBtn, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)))
                    .addGap(68, 68, 68)
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(accountTF, GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                                .addComponent(moneyTF, GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(currencyTF, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
                        .addComponent(backBtn, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGap(101, 101, 101)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(accLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                        .addComponent(accountTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(69, 69, 69)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(moneyLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(moneyTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(currencyTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGap(2, 2, 2)))
                    .addGap(63, 63, 63)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(saveBtn)
                        .addComponent(backBtn))
                    .addContainerGap(139, Short.MAX_VALUE))
        );
      
    }

}
