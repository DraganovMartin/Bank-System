package client;

import dataModel.CurrencyConverter;
import dataModel.ProfileData;

/**
 * @author Martin Draganov
 */
public class ClientDataUIHelper extends ProfileData {
    private String username;

    public ClientDataUIHelper(Balance balance, TransferHistory transferHistory, CurrencyConverter currencyConverter,String username) {
        super(balance, transferHistory, currencyConverter);
        if(!username.isEmpty()) {
            this.username = username;
        }
    }

    public String getUsername() {
        return username;
    }
    
    public void setUsetname(String username){
    	if(!username.isEmpty() && username != null){
    		this.username = username;
    	}
    	
    }

}
