package test;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class BankTest {

    private Bank bank;
    private User user1;
    private User user2;
    private Account acc1;
    private Account acc2;

    @BeforeEach
    void setup() {
        bank = new Bank();

        user1 = new User("ahmet", "1234");
        user2 = new User("utku", "4321");

        acc1 = new SavingsAccount("SA-1001", 1000, 0.05);
        acc2 = new CheckingAccount("CA-2001", 500, 200);

        user1.addAccount(acc1);
        user2.addAccount(acc2);

        bank.addAccount(acc1);
        bank.addAccount(acc2);
    }

    // ================= DEPOSIT =================
    @Test
    void testDeposit() {
        boolean result = bank.deposit(acc1, 500);

        assertTrue(result);
        assertEquals(1500, acc1.getBalance());
    }

    // ================= WITHDRAW =================
    @Test
    void testWithdrawSuccess() {
        boolean result = bank.withdraw(acc1, 300);

        assertTrue(result);
        assertEquals(700, acc1.getBalance());
    }

    @Test
    void testWithdrawFailInsufficientBalance() {
        boolean result = bank.withdraw(acc1, 2000);

        assertFalse(result);
        assertEquals(1000, acc1.getBalance());
    }

    // ================= TRANSFER =================
    @Test
    void testTransferSuccess() {
        boolean result = bank.transfer(acc1, acc2, 400);

        assertTrue(result);
        assertEquals(600, acc1.getBalance());
        assertEquals(900, acc2.getBalance());
    }

    @Test
    void testTransferFailInsufficientBalance() {
        boolean result = bank.transfer(acc1, acc2, 5000);

        assertFalse(result);
        assertEquals(1000, acc1.getBalance());
        assertEquals(500, acc2.getBalance());
    }

    // ================= TRANSACTION LOG =================
    @Test
    void testTransactionCreatedOnDeposit() {
        bank.deposit(acc1, 200);

        assertEquals(1, bank.getTransactions().size());
        assertEquals(TransactionType.DEPOSIT,
                bank.getTransactions().get(0).getType());
    }

    @Test
    void testTransactionCreatedOnTransfer() {
        bank.transfer(acc1, acc2, 100);

        assertEquals(1, bank.getTransactions().size());
        assertEquals(TransactionType.TRANSFER,
                bank.getTransactions().get(0).getType());
    }
}
