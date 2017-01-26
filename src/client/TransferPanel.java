
package client;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import dataModel.models.Currency;

/**
 * @author Martin Draganov
 */
public class TransferPanel extends JPanel {
	
    private JLabel fromAccLabel;
    private JTextField fromAccountTF;
    private JLabel toAccLabel;
    private JTextField toAccTF;
    private JLabel amountLabel;
    private JTextField amountTF;
    private JButton saveBtn;
    private JButton backBtn;
    private ClientDataUIHelper user;
    private JComboBox currencyBox;
    
    public TransferPanel(ClientDataUIHelper user) {
    	this.user = user;
        initComponents();
    }

    private void initComponents() {
        fromAccLabel = new JLabel();
        fromAccountTF = new JTextField();
        toAccLabel = new JLabel();
        toAccTF = new JTextField();
        amountLabel = new JLabel();
        amountTF = new JTextField();
        saveBtn = new JButton();
        backBtn = new JButton();
        currencyBox = new JComboBox<>();
        
        setPreferredSize(new Dimension(565, 401));

        //---- fromAccLabel ----
        fromAccLabel.setHorizontalAlignment(SwingConstants.CENTER);
        fromAccLabel.setText("From account :");

        //---- toAccLabel ----
        toAccLabel.setHorizontalAlignment(SwingConstants.CENTER);
        toAccLabel.setText("To account :");

        //---- amountLabel ----
        amountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        amountLabel.setText("Amount :");

        //---- saveBtn ----
        saveBtn.setText("Save");
        saveBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO get info from fromAccountTF - JTextField,  toAccTF - JTextField, amountTF - JTextField, currencyBox - JComboBox
				
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
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap(134, Short.MAX_VALUE)
                            .addComponent(saveBtn))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(91, 91, 91)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(fromAccLabel, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                                .addComponent(toAccLabel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(amountLabel, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))))
                    .addGap(85, 85, 85)
                    .addGroup(layout.createParallelGroup()
                        .addComponent(backBtn)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(fromAccountTF, GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                                .addComponent(toAccTF, GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                                .addComponent(amountTF, GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(currencyBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                    .addGap(27, 27, 27))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGap(68, 68, 68)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(fromAccLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                        .addComponent(fromAccountTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(45, 45, 45)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(toAccLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                        .addComponent(toAccTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(47, 47, 47)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(amountLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                        .addComponent(amountTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(currencyBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(44, 44, 44)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(saveBtn)
                        .addComponent(backBtn))
                    .addContainerGap(53, Short.MAX_VALUE))
        );

    }

}
