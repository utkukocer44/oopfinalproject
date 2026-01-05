import model.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Bank bank = new Bank();
        AuthService authService = new AuthService();
        Scanner sc = new Scanner(System.in);

        // === LOAD DATA ===
        authService.loadUsersFromCSV("src/data/users.csv");
        bank.loadAccountsFromCSV("src/data/accounts.csv", authService);

        // === LOGIN ===
        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        User loggedUser = authService.login(username, password);

        if (loggedUser == null) {
            System.out.println("❌ Hatalı giriş!");
            return;
        }

        System.out.println("✅ Hoş geldin " + loggedUser.getUsername());

        int choice;

        do {
            System.out.println("\n--- BANK MENU ---");
            System.out.println("1. Hesapları Görüntüle");
            System.out.println("2. Para Yatır");
            System.out.println("3. Para Çek");
            System.out.println("4. Para Transferi");
            System.out.println("5. Çıkış");
            System.out.print("Seçim: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    if (loggedUser.getAccounts().isEmpty()) {
                        System.out.println("⚠️ Hesabınız yok");
                    } else {
                        for (Account acc : loggedUser.getAccounts()) {
                            System.out.println(
                                    acc.getAccountNumber() +
                                            " | Bakiye: " + acc.getBalance());
                        }
                    }
                    break;

                case 2:
                    System.out.print("Hesap No: ");
                    String depNo = sc.next();
                    System.out.print("Tutar: ");
                    double depAmount = sc.nextDouble();

                    Account depAcc = bank.findAccountByNumber(depNo);

                    if (depAcc != null && loggedUser.getAccounts().contains(depAcc)) {
                        bank.deposit(depAcc, depAmount);
                        System.out.println("✅ Para yatırıldı");
                    } else {
                        System.out.println("❌ Hesap bulunamadı");
                    }
                    break;

                case 3:
                    System.out.print("Hesap No: ");
                    String witNo = sc.next();
                    System.out.print("Tutar: ");
                    double witAmount = sc.nextDouble();

                    Account witAcc = bank.findAccountByNumber(witNo);

                    if (witAcc != null && loggedUser.getAccounts().contains(witAcc)) {
                        boolean success = bank.withdraw(witAcc, witAmount);
                        if (success) {
                            System.out.println("✅ Para çekildi");
                        } else {
                            System.out.println("❌ Yetersiz bakiye");
                        }
                    } else {
                        System.out.println("❌ Hesap bulunamadı");
                    }
                    break;

                case 4:
                    System.out.print("Gönderen Hesap: ");
                    String fromNo = sc.next();
                    System.out.print("Alıcı Hesap: ");
                    String toNo = sc.next();
                    System.out.print("Tutar: ");
                    double amount = sc.nextDouble();

                    Account from = bank.findAccountByNumber(fromNo);
                    Account to = bank.findAccountByNumber(toNo);

                    if (from == null || to == null) {
                        System.out.println("❌ Hesap bulunamadı");
                    } else if (!loggedUser.getAccounts().contains(from)) {
                        System.out.println("❌ Bu hesap size ait değil");
                    } else {
                        boolean success = bank.transfer(from, to, amount);
                        if (success) {
                            System.out.println("✅ Transfer başarılı");
                        } else {
                            System.out.println("❌ Yetersiz bakiye");
                        }
                    }
                    break;

                case 5:
                    bank.exportTransactionsToCSV("src/data/transactions.csv");
                    System.out.println("👋 Çıkış yapıldı");
                    break;

                default:
                    System.out.println("❌ Geçersiz seçim");
            }

        } while (choice != 5);
    }
}
