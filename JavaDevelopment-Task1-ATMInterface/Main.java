import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Bank bank = new Bank();

        ATM atm = new ATM(bank);

        System.out.println(
                "===== ATM INTERFACE ====="
        );

        if (atm.login(scanner)) {

            atm.start(scanner);
        }
        else {

            System.out.println(
                    "\nAccess Denied!"
            );
        }

        scanner.close();
    }
}