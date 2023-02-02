package bankingsystemapplication;

import java.io.*;
import java.util.*;

class Account implements Serializable {

    //properties
    String accno;
    String name;
    double balance;

    Account() {
    }   //non-parameterised constructor as i have to achieve serialization

    Account(String a, String n, double b) //parameterised constructor
    {
        accno = a;
        name = n;
        balance = b;
    }

    public String toString() //overiding toString method for display all the details
    {
        return "Account No:" + accno + "\nName :" + name + "\nBalance :" + balance + "\n";
    }
}

public class BankingSystemApplication {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        Account acc = null;
        HashMap<String, Account> hm = new HashMap<>();      //storing acc details into hashmap.key is accno and value is acc itself

        try //whole thing in try-catch block because first time when running there is no file available.so we are throwing an exception from main method..
        {
            FileInputStream fis = new FileInputStream("C:/Users/SUBHOJIT SARKAR/MyJava/Account.txt");   //FIS always need a fileif it is not there it must throws an exception
            ObjectInputStream ois = new ObjectInputStream(fis);

            int count = ois.readInt();     //knowing how many acc are there
            for (int i = 0; i < count; i++) {
                acc = (Account) ois.readObject();
                System.out.println(acc);
                hm.put(acc.accno, acc);
            }
            fis.close();
            ois.close();

        } catch (Exception e) {

        }

        FileOutputStream fos = new FileOutputStream("C:/Users/SUBHOJIT SARKAR/MyJava/Account.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        System.out.println("Menu");

        int choice;
        String accno, name;
        double balance;

        do {
            System.out.println("1. Create Account");
            System.out.println("2. Delete Account");
            System.out.println("3. View Account");
            System.out.println("4. View  All Accounts");
            System.out.println("5. Save Accounts");
            System.out.println("6. Exit");
            System.out.println("Enter your choice ");
            choice = sc.nextInt();

            sc.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");  //ignore any new line character or carraige return

            switch (choice) {
                case 1:
                    System.out.println("Enter Details Accno, Name ,balance ");
                    accno = sc.nextLine();
                    name = sc.nextLine();
                    balance = sc.nextDouble();
                    acc = new Account(accno, name, balance);
                    hm.put(accno, acc);
                    System.out.println("Account Created for " + name);
                    break;

                case 2:
                    System.out.println("Enter Accno");
                    accno = sc.nextLine();
                    hm.remove(accno);
                    break;

                case 3:
                    System.out.println("Enter Accno");
                    accno = sc.nextLine();
                    acc = hm.get(accno);
                    System.out.println(acc);
                    break;
                case 4:
                    for (Account a : hm.values()) {
                        System.out.println(a);
                    }
                    break;

                case 5:                                         //case 5 is save and case is exit.before exit also we have to save all the acc
                case 6:
                    oos.writeInt(hm.size());

                    for (Account a : hm.values()) {
                        oos.writeObject(a);
                    }

            }

        } while (choice != 6);
        oos.flush();
        oos.close();

        fos.close();
    }

}
