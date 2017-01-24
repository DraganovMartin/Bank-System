package client;

import javax.swing.JTable;

import dataModel.CurrencyConverter;
import dataModel.ProfileData;

/**
 * @author Martin Draganov
 */
public class ClientDataUIHelper extends ProfileData {
    private String username;
    private char[] pass;
    private JTable balanceTable;
    private JTable transferTable;
    
	public ClientDataUIHelper(Balance balance, TransferHistory transferHistory, CurrencyConverter currencyConverter,String username) {
        super(balance, transferHistory, currencyConverter);
        if(!username.isEmpty()) {
            this.username = username;
        }
    	this.balanceTable = this.getBalanceTable();
    	this.transferTable = this.getTransferHistoryTable();
        
       
    }

    public String getUsername() {
        return username;
    }
    
    public void setUsetname(String username){
    	if(!username.isEmpty() && username != null){
    		this.username = username;
    	}
    	
    }
    
    public void setPass(char[] pass) {
 		this.pass = pass;
 	}
    
    public char[] getPass() {
		return pass;
	}


}
