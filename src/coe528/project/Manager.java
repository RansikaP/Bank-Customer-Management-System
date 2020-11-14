package coe528.project;

import java.io.*;

/**
 *
 * @author Ransika Perera
 */
public class Manager extends User{
    /**
     *  OVERVIEW: Manager is a immutable child of the user class that cannot
     *            be instantiated.
     *            It only contains static methods since there are no instances
     *            of it.
     */
    
    private String username, password;
    private int role;
    private static Manager instance = null;
    public final String folderPath = "customers\\";
    
    /**
     * constructors
     * EFFECTS: Initializes this to be empty but it is private so it can
     *          never be instantiated.
     */
    private Manager() {
        this.username = "admin";
        this.password = "admin";
        this.role = 1;
    }
    
    /**
     * Effects: Returns the single Manager object, if there is none, one is 
     *          created
     * @return 
     */
    public static Manager getInstance() {
        if (instance == null)
            instance = new Manager();
        return instance;
    }
    
    /**
     * REQUIRES: user and pass are not null pointers
     * EFFECTS: Takes the input and creates new customer a file with name user 
     *          if it does not already exist. Then prints user, pass, "0",
     *          "1000" and "0", to indicate the customers username, password,
     *          role, initial account balance and customer level.
     * @param user
     * @param pass 
     */
    public void addCusomter(String user, String pass) {
        String filePath = folderPath + user + ".txt";
        PrintWriter pw = null;
        Writer fw;
        
        File check = new File(filePath);
               
        if (check.exists())
            System.out.println("Already exisits");
        else {   
            try {
                fw = new FileWriter(filePath, true);
                pw = new PrintWriter(fw);
            } catch (IOException ex) {             //print error in app
                ex.printStackTrace(System.err);
            }
            
            pw.println(user);
            pw.println(pass);
            pw.println("0");
            pw.println("1000");
            pw.println("0");        
            pw.close();
        }
    }
    
    /**
     * REQUIRES: user is not a null pointer
     * EFFECTS: Deletes the customer file with the name user from the database
     * @param user 
     * @throws java.lang.Exception 
     */
    public void deleteCustomer(String user) throws Exception {
        String filePath = folderPath + user + ".txt";        
        File deleteFile = new File(filePath);
        if (deleteFile.exists())
            deleteFile.delete();
        else 
            throw new Exception();
    }
    
    /**
     * AF(c) = {username = "admin", password = "admin", role = 1} 
     * @return 
     */
    @Override
    public String toString() {
        return username + ", " + password + ", " + role;
    }
    
    /**
     * Rep Invariant = folderPath != null && username = "admin" && password = "admin" && role = 1 
     * @return 
     */
    public boolean repOk() {
        return folderPath != null && username.equals("admin") && password.equals("admin")  && role == 1;
    }
}
