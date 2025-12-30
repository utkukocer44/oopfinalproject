package model;

public class SavingsAccount extends Account {

    private double interestRate;

    // Constructor
    public SavingsAccount(String accountNumber, double balance, double interestRate) {
        super(accountNumber, balance);
        this.interestRate = interestRate;
    }

    // POLYMORPHISM: override withdraw
    @Override
    public void withdraw(double amount) {
        // Savings accounts cannot withdraw more than balance
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        }
    }

    // Interest calculation
    public double calculateInterest() {
        return balance * interestRate;
    }
}
