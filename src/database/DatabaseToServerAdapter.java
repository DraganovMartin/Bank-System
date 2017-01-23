package demo;

import dataModel.CurrencyConverter;
import dataModel.ProfileData;
import networking.messages.Update;
import networking.messages.request.*;

/**
 *
 * @author iliyan
 */
public interface DatabaseToServerAdapter {

    /**
     * Връща {@link CurrencyConverter} обект според наличните в базата данни
     * (таблицата с валутите).
     *
     * @return {@link CurrencyConverter} обект според наличните в базата данни
     * (таблицата с валутите).
     */
    public CurrencyConverter getCurrencyConverter();

    /**
     * Връща {@link ProfileData} обект, съдържащ предназначените за потребителя
     * данни (баланс по сметки, история на транзакциите и текущи валутни
     * курсове) по зададено потребителско име.
     *
     * @param username потребителското име, за което да се върнат данните.
     *
     * @return {@link ProfileData} обект по зададено потребителско име.
     */
    public ProfileData getUserProfileData(String username);

    /**
     * Обработва входящо съобщение от клиента и връща {@link Update}съобщение -
     * резултат от обреаботката.
     *
     * @param registerRequest входящото съобщение.
     *
     * @return {@link Update}съобщение - резултат от обреаботката.
     */
    public Update handleRegisterRequest(RegisterRequest registerRequest);

    /**
     * Обработва входящо съобщение от клиента и връща {@link Update}съобщение -
     * резултат от обреаботката.
     *
     * @param loginRequest входящото съобщение.
     *
     * @return {@link Update}съобщение - резултат от обреаботката.
     */
    public Update handleLoginRequest(LoginRequest loginRequest);

    /**
     * Обработва входящо съобщение от клиента и връща {@link Update}съобщение -
     * резултат от обреаботката.
     *
     * @param logoutRequest входящото съобщение.
     *
     * @return {@link Update}съобщение - резултат от обреаботката.
     */
    public Update handleLogoutRequest(LogoutRequest logoutRequest);

    /**
     * Обработва входящо съобщение от клиента и връща {@link Update}съобщение -
     * резултат от обреаботката.
     *
     * @param changePasswordRequest входящото съобщение.
     *
     * @return {@link Update}съобщение - резултат от обреаботката.
     */
    public Update handleChangePasswordRequest(ChangePasswordRequest changePasswordRequest);

    /**
     * Обработва входящо съобщение от клиента и връща {@link Update}съобщение -
     * резултат от обреаботката.
     *
     * @param balanceRequest входящото съобщение.
     *
     * @return {@link Update}съобщение - резултат от обреаботката.
     */
    public Update handleBalanceRequest(BalanceRequest balanceRequest);

    /**
     * Обработва входящо съобщение от клиента и връща {@link Update}съобщение -
     * резултат от обреаботката.
     *
     * @param transactionHistoryRequest входящото съобщение.
     *
     * @return {@link Update}съобщение - резултат от обреаботката.
     */
    public Update handleTransactionHistoryRequest(TransactionHistoryRequest transactionHistoryRequest);

    /**
     * Обработва входящо съобщение от клиента и връща {@link Update}съобщение -
     * резултат от обреаботката.
     *
     * @param currencyRatesRequest входящото съобщение.
     *
     * @return {@link Update}съобщение - резултат от обреаботката.
     */
    public Update handleCurrencyRatesRequest(CurrencyRatesRequest currencyRatesRequest);

    /**
     * Обработва входящо съобщение от клиента и връща {@link Update}съобщение -
     * резултат от обреаботката.
     *
     * @param createBankAccountRequest входящото съобщение.
     *
     * @return {@link Update}съобщение - резултат от обреаботката.
     */
    public Update handleCreateBankAccountRequest(CreateBankAccountRequest createBankAccountRequest);

    /**
     * Обработва входящо съобщение от клиента и връща {@link Update}съобщение -
     * резултат от обреаботката.
     *
     * @param depositRequest входящото съобщение.
     *
     * @return {@link Update}съобщение - резултат от обреаботката.
     */
    public Update handleDepositRequest(DepositRequest depositRequest);

    /**
     * Обработва входящо съобщение от клиента и връща {@link Update}съобщение -
     * резултат от обреаботката.
     *
     * @param withdrawRequest входящото съобщение.
     *
     * @return {@link Update}съобщение - резултат от обреаботката.
     */
    public Update handleWithdrawRequest(WithdrawRequest withdrawRequest);

    /**
     * Обработва входящо съобщение от клиента и връща {@link Update}съобщение -
     * резултат от обреаботката.
     *
     * @param transferRequest входящото съобщение.
     *
     * @return {@link Update}съобщение - резултат от обреаботката.
     */
    public Update handleTransferRequest(TransferRequest transferRequest);
}
