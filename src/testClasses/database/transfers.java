package testClasses.database;

import dataModel.Money;
import dataModel.models.Currency;
import dataModel.models.Transfer;
import database.databaseController.DatabaseBankAccountController;
import database.databaseController.DatabaseController;
import database.databaseController.DatabaseTransfersController;

import java.util.List;

/**
 * Created by Nikolay on 12/30/2016.
 */
public class transfers {
    public static void main(String[] args){
        DatabaseController.open();
        DatabaseBankAccountController dbBankAccount = new DatabaseBankAccountController();
        DatabaseTransfersController dbTransfers = new DatabaseTransfersController();
        Money amount1 = dbBankAccount.getAmount(1);
        Money amount2 = dbBankAccount.getAmount(2);
        System.out.println("Bank Account 1 "+amount1.getAmount());
        System.out.println("Bank Account 2 "+amount2.getAmount());
        amount1 = dbBankAccount.getAmount(1);
        amount2 = dbBankAccount.getAmount(2);
        System.out.println("Bank Account 1 "+amount1.getAmount());
        System.out.println("Bank Account 2 "+amount2.getAmount());
        List<Transfer> transfers = dbTransfers.getHistory(1);
        System.out.println("All transfers on bank account with id 1:");
        for(int i = 0;i < transfers.size();i++){
            System.out.println(transfers.get(i).toString());
        }
        dbTransfers.closeTransferContoller();
        dbBankAccount.closeBankAccountController();
        DatabaseController.close();
    }
}
