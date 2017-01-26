
package client;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import dataModel.CurrencyConverter;
import dataModel.Money;
import dataModel.models.Currency;
import networking.connections.Client;
import networking.messages.request.CreateBankAccountRequest;

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
    private JTextField currencyTF;
    private Client connection;
    private String accChoice;
	
    public CreateAccPanel(ClientDataUIHelper user, Client connection) {
    	this.connection = connection;
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
        currencyTF = new JTextField();


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
        
        createBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// accTypeCombobox, initMoneyTF - JTextField, currencyTF - JTextField
				Money money = Money.createMoney(new Currency(currencyTF.getText()), initMoneyTF.getText());
				try {
					connection.send(new CreateBankAccountRequest(accTypeComBox.getSelectedItem().toString(),money));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				setVisible(false);
			}
		});
        

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGap(92, 92, 92)
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(accTypeLabel, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 26, Short.MAX_VALUE))
                        .addComponent(initMoneyLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(createBtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(65, 65, 65)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(accTypeComBox, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                        .addComponent(initMoneyTF, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                        .addComponent(backBtn, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(currencyTF, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGap(94, 94, 94)
                    .addGroup(layout.createParallelGroup()
                        .addComponent(accTypeLabel)
                        .addComponent(accTypeComBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(27, 27, 27)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(initMoneyLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(initMoneyTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(currencyTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                    .addGap(75, 75, 75)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(createBtn)
                        .addComponent(backBtn))
                    .addContainerGap(91, Short.MAX_VALUE))
        );
        
    }

}
