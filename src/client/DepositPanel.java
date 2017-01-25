
package client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.GroupLayout;

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
                                .addComponent(moneyLabel, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)))
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap(180, Short.MAX_VALUE)
                            .addComponent(saveBtn, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)))
                    .addGap(68, 68, 68)
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(accountTF, GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                            .addComponent(moneyTF, GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE))
                        .addComponent(backBtn, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(81, Short.MAX_VALUE))
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
                        .addComponent(moneyTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(63, 63, 63)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(saveBtn)
                        .addComponent(backBtn))
                    .addContainerGap(140, Short.MAX_VALUE))
        );
      
    }

}
