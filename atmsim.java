// Import necessary for running Java code in Node.js environment
import { execSync } from 'child_process';
import { writeFileSync, mkdirSync, existsSync } from 'fs';

// Create a directory for our Java file if it doesn't exist
if (!existsSync('./java-project')) {
  mkdirSync('./java-project');
}

// Write the Java file
writeFileSync('./java-project/ATMSimulator.java', `
import java.util.Scanner;

public class ATMSimulator {
    // Initialize balance
    private static double balance = 1000.00;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        
        System.out.println("Welcome to the Basic ATM Simulator!");

        do {
            displayMenu();
            choice = getUserChoice();

            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    withdraw();
                    break;
                case 4:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println(); // Print a blank line for readability
        } while (choice != 4);

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("Please select an option:");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Exit");
        System.out.print("Enter your choice (1-4): ");
    }

    private static int getUserChoice() {
        while (!scanner.hasNextInt()) {
            System.out.println("That's not a valid option. Please enter a number between 1 and 4.");
            scanner.next(); // Consume the invalid input
        }
        return scanner.nextInt();
    }

    private static void checkBalance() {
        System.out.printf("Your current balance is: $%.2f%n", balance);
    }

    private static void deposit() {
        System.out.print("Enter the amount to deposit: $");
        double amount = getValidAmount();
        
        if (amount > 0) {
            balance += amount;
            System.out.printf("$%.2f has been deposited to your account.%n", amount);
            checkBalance();
        } else {
            System.out.println("Invalid amount. Deposit cancelled.");
        }
    }

    private static void withdraw() {
        System.out.print("Enter the amount to withdraw: $");
        double amount = getValidAmount();
        
        if (amount > 0) {
            if (amount <= balance) {
                balance -= amount;
                System.out.printf("$%.2f has been withdrawn from your account.%n", amount);
                checkBalance();
            } else {
                System.out.println("Insufficient funds. Withdrawal cancelled.");
            }
        } else {
            System.out.println("Invalid amount. Withdrawal cancelled.");
        }
    }

    private static double getValidAmount() {
        while (!scanner.hasNextDouble()) {
            System.out.println("That's not a valid amount. Please enter a number.");
            scanner.next(); // Consume the invalid input
        }
        return scanner.nextDouble();
    }
}
`);

// Compile and run the Java code
try {
    console.log("Compiling Java file...");
    execSync('javac ./java-project/ATMSimulator.java', { stdio: 'inherit' });
    
    console.log("\nRunning Java program...");
    console.log("------------------------");
    const output = execSync('java -cp ./java-project ATMSimulator', { encoding: 'utf-8', input: '1\n2\n500\n3\n200\n1\n4\n' });
    console.log(output);
} catch (error) {
    console.error("Error:", error.message);
}

// Display the Java code for reference
console.log("\nJava Source Code:");
console.log("------------------------");
console.log(execSync('cat ./java-project/ATMSimulator.java', { encoding: 'utf-8' }));
