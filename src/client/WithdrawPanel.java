
package client;

import javax.swing.*;

import dataModel.models.Currency;

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
    private JComboBox currencyBox;
    private ClientDataUIHelper user;
    
    public WithdrawPanel(ClientDataUIHelper user) {
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
        currencyBox = new JComboBox();

        //---- accLabel ----
        accLabel.setText("Account :");

        //---- moneyLabel ----
        moneyLabel.setText("Money :");

        //---- saveBtn ----
        saveBtn.setText("Save");

        //---- backBtn ----
        backBtn.setText("Back");
        
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
                            .addGap(18, 18, 18)
                            .addComponent(currencyBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addComponent(backBtn))
                    .addContainerGap(18, Short.MAX_VALUE))
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
                        .addComponent(moneyTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(currencyBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(63, 63, 63)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(saveBtn)
                        .addComponent(backBtn))
                    .addContainerGap(70, Short.MAX_VALUE))
        );
    }
}