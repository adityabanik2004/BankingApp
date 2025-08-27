// 1. Custom Exceptions
class InsufficientBalanceException extends Exception {
public InsufficientBalanceException(String msg) {
super(msg);
}
}
class AccountNotFoundException extends Exception {
public AccountNotFoundException(String msg) {
super(msg);
}
}
// 2. Interface
interface BankOperations {
void deposit(int accNo, double amount) throws AccountNotFoundException;
void withdraw(int accNo, double amount) throws AccountNotFoundException, InsufficientBalanceException;
void createDemandDraft(int accNo, String payee, double amount) throws AccountNotFoundException, InsufficientBalanceException;
void viewAccounts();
}
// 3. Account Class
class Account {
int accNo;
String holderName;
double balance;
Account(int accNo, String holderName, double balance) {
this.accNo = accNo;
this.holderName = holderName;
this.balance = balance;
}
public String toString() {
return "Account No: " + accNo + ", Name: " + holderName + ", Balance: ₹" + balance;
}
}
// 4. Bank Implementation
class Bank implements BankOperations {
Account[] accounts;
int count;
Bank(int size) {
accounts = new Account[size];
count = 0;
}
void addAccount(Account acc) {
if (count < accounts.length) {
accounts[count++] = acc;
} else {
System.out.println(" Cannot add more accounts.");
}
}
private Account findAccount(int accNo) throws AccountNotFoundException {
for (int i = 0; i < count; i++) {
if (accounts[i].accNo == accNo) return accounts[i];
}
throw new AccountNotFoundException("Account " + accNo + " not found!");
}
@Override
public void deposit(int accNo, double amount) throws AccountNotFoundException {
Account acc = findAccount(accNo);
acc.balance += amount;
System.out.println(" Deposited ₹" + amount + " into " + acc.holderName + "'s account.");
}
@Override
public void withdraw(int accNo, double amount) throws AccountNotFoundException, InsufficientBalanceException {
Account acc = findAccount(accNo);
if (acc.balance >= amount) {
acc.balance -= amount;
System.out.println(" Withdrawn ₹" + amount + " from " + acc.holderName + "'s account.");
} else {
throw new InsufficientBalanceException(" Insufficient Balance in account " + accNo);
}
}
@Override
public void createDemandDraft(int accNo, String payee, double amount) throws AccountNotFoundException, InsufficientBalanceException {
Account acc = findAccount(accNo);
if (acc.balance >= amount) {
acc.balance -= amount;
System.out.println(" Demand Draft created for ₹" + amount + " in favor of " + payee + " from " + acc.holderName + "'s account.");
} else {
throw new InsufficientBalanceException(" Insufficient Balance to create DD from account " + accNo);
}
}
@Override
public void viewAccounts() {
System.out.println("\\n--- Account Details ---");
for (int i = 0; i < count; i++) {
System.out.println(accounts[i]);
}
}
}
// 5. Main Application
class BankingApp {
public static void main(String[] args) {
java.util.Scanner sc = new java.util.Scanner(System.in);
Bank bank = new Bank(10);
// Pre-loaded accounts
bank.addAccount(new Account(101, "Alice", 20000));
bank.addAccount(new Account(102, "Bob", 15000));
int choice;
do {
System.out.println("\\n===== Banking Transaction Menu =====");
System.out.println("1. View Accounts");
System.out.println("2. Deposit");
System.out.println("3. Withdraw");
System.out.println("4. Create Demand Draft");
System.out.println("5. Exit");
System.out.print("Enter your choice: ");
choice = sc.nextInt();
try {
switch (choice) {
case 1:
bank.viewAccounts();
break;
case 2:
System.out.print("Enter Account No: ");
int dAcc = sc.nextInt();
System.out.print("Enter Amount: ");
double dAmt = sc.nextDouble();
bank.deposit(dAcc, dAmt);
break;
case 3:
System.out.print("Enter Account No: ");
int wAcc = sc.nextInt();
System.out.print("Enter Amount: ");
double wAmt = sc.nextDouble();
bank.withdraw(wAcc, wAmt);
break;
case 4:
System.out.print("Enter Account No: ");
int ddAcc = sc.nextInt();
sc.nextLine();
System.out.print("Enter Payee Name: ");
String payee = sc.nextLine();
System.out.print("Enter Amount: ");
double ddAmt = sc.nextDouble();
bank.createDemandDraft(ddAcc, payee, ddAmt);
break;
case 5:
System.out.println(" Exiting Banking App...");
break;
default:
System.out.println(" Invalid choice!");
}
} catch (Exception e) {
System.out.println(" Error: " + e.getMessage());
}
} while (choice != 5);
sc.close();
}
}