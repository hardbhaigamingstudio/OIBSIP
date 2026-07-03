import java.util.ArrayList;
import java.util.Scanner;

public class ATM {

    private Bank bank;
    private Account currentAccount;
    private ArrayList<Transaction> history;

    public ATM(Bank bank) {
        this.bank = bank;
        this.history = new ArrayList<>();
    }

    public boolean login(Scanner scanner) {

        int attempts = 0;

        while (attempts < 3) {

            System.out.print("Enter User ID: ");
            String userId = scanner.nextLine();

            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine();

            Account account = bank.getAccount(userId);

            if (account != null &&
                    account.getPin().equals(pin)) {

                currentAccount = account;

                System.out.println("\nLogin Successful!");
                return true;
            }

            attempts++;
            System.out.println(
                    "Invalid credentials. Attempts left: "
                            + (3 - attempts)
            );
        }

        return false;
    }

    public void start(Scanner scanner) {

        while (true) {

            System.out.println("\n===== ATM MENU =====");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");

            System.out.print("Choose Option: ");

            int choice = Integer.parseInt(
                    scanner.nextLine()
            );

            switch (choice) {

                case 1:
                    showHistory();
                    break;

                case 2:
                    withdraw(scanner);
                    break;

                case 3:
                    deposit(scanner);
                    break;

                case 4:
                    transfer(scanner);
                    break;

                case 5:
                    System.out.println(
                            "\nThank you for using the ATM!"
                    );
                    return;

                default:
                    System.out.println("Invalid Option!");
            }
        }
    }

    private void showHistory() {

        System.out.println(
                "\n===== Transaction History ====="
        );

        if (history.isEmpty()) {
            System.out.println(
                    "No transactions found."
            );
            return;
        }

        for (Transaction t : history) {
            System.out.println(t);
        }
    }

    private void deposit(Scanner scanner) {

        System.out.print(
                "Enter Deposit Amount: "
        );

        double amount = Double.parseDouble(
                scanner.nextLine()
        );

        currentAccount.deposit(amount);

        history.add(
                new Transaction(
                        "Deposited: $" + amount
                )
        );

        System.out.println(
                "Deposit Successful!"
        );

        System.out.println(
                "Current Balance: $" +
                        currentAccount.getBalance()
        );
    }

    private void withdraw(Scanner scanner) {

        System.out.print(
                "Enter Withdraw Amount: "
        );

        double amount = Double.parseDouble(
                scanner.nextLine()
        );

        if (currentAccount.withdraw(amount)) {

            history.add(
                    new Transaction(
                            "Withdrawn: $" + amount
                    )
            );

            System.out.println(
                    "Withdrawal Successful!"
            );

            System.out.println(
                    "Current Balance: $" +
                            currentAccount.getBalance()
            );
        }
        else {
            System.out.println(
                    "Insufficient Funds"
            );
        }
    }

    private void transfer(Scanner scanner) {

        System.out.print(
                "Recipient Account ID: "
        );

        String receiverId =
                scanner.nextLine();

        Account receiver =
                bank.getAccount(receiverId);

        if (receiver == null) {

            System.out.println(
                    "Account Not Found!"
            );

            return;
        }

        System.out.print(
                "Transfer Amount: "
        );

        double amount = Double.parseDouble(
                scanner.nextLine()
        );

        if (currentAccount.withdraw(amount)) {

            receiver.deposit(amount);

            history.add(
                    new Transaction(
                            "Transferred $" +
                                    amount +
                                    " to Account " +
                                    receiverId
                    )
            );

            System.out.println(
                    "Transfer Successful!"
            );

            System.out.println(
                    "Current Balance: $" +
                            currentAccount.getBalance()
            );
        }
        else {
            System.out.println(
                    "Insufficient Funds"
            );
        }
    }
}