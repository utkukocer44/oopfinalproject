import model.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Bank bank = new Bank();
        AuthService authService = new AuthService();
        Scanner sc = new Scanner(System.in);

        // === LOAD USERS FROM CSV ===
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

        // === MENU ===
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

                case 5:
                    System.out.println("👋 Çıkış yapıldı");
                    bank.exportTransactionsToCSV("transactions.csv");
                    break;

                default:
                    System.out.println("⚠️ Bu adım henüz aktif değil");
            }

        } while (choice != 5);
    }
}
