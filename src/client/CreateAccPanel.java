
package client;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import dataModel.CurrencyConverter;
import dataModel.models.Currency;

/**
 * @author Martin Draganov
 */
public class CreateAccPanel extends JPanel {
	private JPanel thisPanel;
	private JLabel accTypeLabel;
    private JComboBox<String> accTypeComBox;
    private JLabel initMoneyLabel;
    private JTextField initMoneyTF;
    private JButton backBtn;
    private JButton createBtn;
    private ClientDataUIHelper user;
    private JComboBox<String> currencyBox;
	
    public CreateAccPanel(ClientDataUIHelper user) {
    	this.user = user;
    	thisPanel = this;
        initComponents();
    }

    private void initComponents() {
        accTypeLabel = new JLabel();
        accTypeComBox = new JComboBox<>();
        initMoneyLabel = new JLabel();
        initMoneyTF = new JTextField();
        backBtn = new JButton();
        createBtn = new JButton();
        currencyBox = new JComboBox<>();


        //---- accTypeLabel ----
        accTypeLabel.setText("Choose account type :");

        //---- accTypeComBox ----
        accTypeComBox.setModel(new DefaultComboBoxModel<>(new String[] {
            "Full access",
            "Ordinary"
        }));

        //---- initMoneyLabel ----
        initMoneyLabel.setText("Initial money :");

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
        
        //---- createBtn ----
        createBtn.setText("Create");
        
        //---- currencyBox ----
        String list[] = new String[user.currencyConverter.getSupportedCurrencies().length];
        for (int i = 0; i < list.length; i++) {
			Currency[] c = user.currencyConverter.getSupportedCurrencies();
			list[i] = c[i].getSymbol();
		}
        currencyBox.setModel(new DefaultComboBoxModel<>(list));

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGap(92, 92, 92)
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(accTypeLabel, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addComponent(initMoneyLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(createBtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(65, 65, 65)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(accTypeComBox, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                        .addComponent(initMoneyTF, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                        .addComponent(backBtn, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(currencyBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(41, 41, 41))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGap(94, 94, 94)
                    .addGroup(layout.createParallelGroup()
                        .addComponent(accTypeLabel)
                        .addComponent(accTypeComBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(26, 26, 26)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(initMoneyLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                        .addComponent(initMoneyTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(currencyBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(75, 75, 75)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(createBtn)
                        .addComponent(backBtn))
                    .addContainerGap(91, Short.MAX_VALUE))
        );
        
    }

}
