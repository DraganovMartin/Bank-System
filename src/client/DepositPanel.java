
package client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import dataModel.models.Currency;

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
    private JComboBox currencyBox;
    
    
    public DepositPanel(ClientDataUIHelper user) {
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
        currencyBox = new JComboBox<>();

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
				// TODO get info from accountTF - JTextField,  moneyTF - JTextField, amountTF - JTextField, currencyBox - JComboBox
				
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
        
      //---- currencyBox ---- THIS IS WHERE IS THE PROBLEM
        String list[] = new String[user.currencyConverter.getSupportedCurrencies().length];
        for (int i = 0; i < list.length; i++) {
			Currency[] c = user.currencyConverter.getSupportedCurrencies();
			list[i] = c[i].getSymbol();
		}
        currencyBox.setModel(new DefaultComboBoxModel<>(list));
        
        
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
                                .addComponent(moneyLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                            .addComponent(currencyBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addComponent(backBtn, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(11, Short.MAX_VALUE))
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
                        .addGroup(layout.createParallelGroup()
                            .addComponent(currencyBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(moneyTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                    .addGap(63, 63, 63)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(saveBtn)
                        .addComponent(backBtn))
                    .addContainerGap(139, Short.MAX_VALUE))
        );
      
    }

}
