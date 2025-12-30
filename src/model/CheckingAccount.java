package model;

public class CheckingAccount extends Account {

    private double overdraftLimit;

    // Constructor
    public CheckingAccount(String accountNumber, double balance, double overdraftLimit) {
        super(accountNumber, balance);
        this.overdraftLimit = overdraftLimit;
    }

    // POLYMORPHISM: override withdraw
    @Override
    public void withdraw(double amount) {
        // Allow overdraft up to the limit
        if (amount > 0 && balance - amount >= -overdraftLimit) {
            balance -= amount;
        }
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }
}
