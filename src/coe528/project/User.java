package coe528.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author Ransika Perera
 */
public abstract class User {
    String username, password;
    int role;
    static Manager m = Manager.getInstance();
    static Scanner open;
    
    public static Customer login(String user, String pass, int roleNum) {
        String filePath = m.folderPath + user + ".txt";
               
        try {
            open = new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            System.out.println("User not found");
            return null;
        }
        open.nextLine();
        
        if (pass.equals(open.nextLine())) {
            if (roleNum == open.nextInt()) 
                return new Customer(user, pass, open.nextDouble(), open.nextInt());
        }
        open.close();
        
        return null;        
    }
    
     public static void logout(String userName, double newBankAccount, int newLevel) {
        open.close();
        String tempFile = m.folderPath + "temp.txt";
        String filePath = m.folderPath + userName + ".txt";
        File oldFile = new File(filePath);
        File newFile = new File(tempFile);
        String write;
        try {
            FileWriter fw = new FileWriter(tempFile, true);
            PrintWriter pw = new PrintWriter(fw);
            Scanner s = new Scanner(new File(filePath));
            pw.println(userName);
            write = s.nextLine();
            write = s.nextLine();
            pw.println(write);
            write = s.nextLine();
            pw.println(write);
            pw.println(newBankAccount);
            pw.print(newLevel);
            s.close();
            pw.flush();
            pw.close();
            oldFile.delete();
            File dump = new File(filePath);
            newFile.renameTo(dump);
        } catch (Exception e) {
            System.out.println("File could not be updated");
        }        
    }
}
