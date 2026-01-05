package model;

import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class Bank {

    private List<Account> accounts;
    private List<Transaction> transactions;
    private int transactionCounter;

    public Bank() {
        this.accounts = new ArrayList<>();
        this.transactions = new ArrayList<>();
        this.transactionCounter = 1;
    }

    // ================= ACCOUNT =================
    public void addAccount(Account account) {
        accounts.add(account);
    }

    public Account findAccountByNumber(String accountNumber) {
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(accountNumber)) {
                return acc;
            }
        }
        return null;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    // ================= TRANSACTIONS =================
    public List<Transaction> getTransactions() {
        return transactions;
    }

    // 🔥 SADECE LOGGED USER TRANSACTIONS
    public List<Transaction> getTransactionsForUser(User user) {

        List<Transaction> result = new ArrayList<>();

        for (Transaction t : transactions) {
            for (Account acc : user.getAccounts()) {

                String accNo = acc.getAccountNumber();

                if (accNo.equals(t.getFromAccount()) ||
                        accNo.equals(t.getToAccount())) {

                    result.add(t);
                    break;
                }
            }
        }
        return result;
    }

    // ================= DEPOSIT =================
    public boolean deposit(Account account, double amount) {
        if (account == null || amount <= 0)
            return false;

        account.deposit(amount);

        transactions.add(new Transaction(
                transactionCounter++,
                TransactionType.DEPOSIT,
                amount,
                null,
                account.getAccountNumber()));
        return true;
    }

    // ================= WITHDRAW =================
    public boolean withdraw(Account account, double amount) {
        if (account == null || amount <= 0)
            return false;

        if (amount > account.getBalance())
            return false;

        account.withdraw(amount);

        transactions.add(new Transaction(
                transactionCounter++,
                TransactionType.WITHDRAW,
                amount,
                account.getAccountNumber(),
                null));
        return true;
    }

    // ================= TRANSFER =================
    public boolean transfer(Account from, Account to, double amount) {
        if (from == null || to == null || amount <= 0)
            return false;

        if (from.getBalance() < amount)
            return false;

        from.withdraw(amount);
        to.deposit(amount);

        transactions.add(new Transaction(
                transactionCounter++,
                TransactionType.TRANSFER,
                amount,
                from.getAccountNumber(),
                to.getAccountNumber()));
        return true;
    }

    // ================= CSV EXPORT =================
    public void exportTransactionsToCSV(String fileName) {

        try (FileWriter writer = new FileWriter(fileName)) {

            writer.append("TransactionId,Type,Amount,From,To,Date\n");

            for (Transaction t : transactions) {
                writer.append(t.getTransactionId() + ",");
                writer.append(t.getType().name() + ",");
                writer.append(t.getAmount() + ",");
                writer.append(t.getFromAccount() + ",");
                writer.append(t.getToAccount() + ",");
                writer.append(t.getDate().toString() + "\n");
            }

            System.out.println("✅ Transactions exported to " + fileName);

        } catch (IOException e) {
            System.out.println("❌ CSV export error: " + e.getMessage());
        }
    }

    // ================= LOAD ACCOUNTS =================
    public void loadAccountsFromCSV(String fileName, AuthService authService) {

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            reader.readLine(); // header
            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                String username = data[0];
                String accNo = data[1];
                String type = data[2];
                double balance = Double.parseDouble(data[3]);
                double extra = Double.parseDouble(data[4]);

                Account account;

                if (type.equalsIgnoreCase("SAVINGS")) {
                    account = new SavingsAccount(accNo, balance, extra);
                } else if (type.equalsIgnoreCase("CHECKING")) {
                    account = new CheckingAccount(accNo, balance, extra);
                } else {
                    continue;
                }

                User owner = authService.findUserByUsername(username);
                if (owner != null) {
                    owner.addAccount(account);
                    accounts.add(account);
                }
            }

            System.out.println("✅ Accounts loaded from " + fileName);

        } catch (Exception e) {
            System.out.println("❌ Error loading accounts CSV: " + e.getMessage());
        }
    }
}
