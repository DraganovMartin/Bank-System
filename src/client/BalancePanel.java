
package client;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * @author Martin Draganov
 */
public class BalancePanel extends JPanel {
	
	 	private JScrollPane scrollPane1;
	    private JTable balanceTable;
	    private JButton backBtn;
	    private ClientDataUIHelper user;
	    
    public BalancePanel(ClientDataUIHelper user) {
    	this.user = user;
        initComponents();
    }

    private void initComponents() {
        
        scrollPane1 = new JScrollPane();
        balanceTable = new JTable();
        backBtn = new JButton();

      //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(balanceTable);
        }

        //---- balanceTable ----
        balanceTable = user.getBalanceTable(); ///
        balanceTable.setVisible(true);
        
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
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(50, Short.MAX_VALUE)
                    .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(48, 48, 48))
                .addGroup(layout.createSequentialGroup()
                    .addGap(208, 208, 208)
                    .addComponent(backBtn, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(217, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(78, Short.MAX_VALUE)
                    .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(backBtn)
                    .addGap(37, 37, 37))
        );
       
    }
    
    public void refreshData(){
    	
    }
}
