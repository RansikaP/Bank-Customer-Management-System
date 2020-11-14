package coe528.project;

/**
 *
 * @author Ransika Perera
 */
public class Customer extends User {
    private String username, password;
    private double bankAccount;
    private CustomerState level, platinum, gold, silver; 
    
    public Customer(String user, String pass, double bankAccount, int level) {
        this.username = user;
        this.password = pass;
        this.bankAccount = bankAccount; 
        this.role = 0;
        platinum = new Platinum(this);
        gold = new Gold(this);
        silver = new Silver(this);
        switch (level) {
            case 1:
                this.level = gold;
                break;
            case 0:
                this.level = platinum;
                break;
            default:
                this.level = silver;;
        }
    }
    
    public void setCustomerState(CustomerState newCustomerState) {
        level = newCustomerState;
    }
    
    public void deposit(double amount) {
        if (amount > 0) {
            bankAccount += amount;
            level.checkLevel();
        } else
            throw new IllegalArgumentException();
    }
    
    public void withdraw(double amount) {        
        if (amount > 0 && amount <= bankAccount) {
            bankAccount -= amount;
            level.checkLevel();
        } else
            throw new IllegalArgumentException();
    }
    
    public double getBalance() {
        return bankAccount;
    }
    
    
    
    public void onlinePurchase(double amount) {
        level.onlinePurchase(amount);
    }
    
    public int getLevel() {
        if (level == gold)
            return 1;
        else if (level == platinum)
            return 0;
        else 
            return 2;
    }
    
    public CustomerState silverState() { return silver; }
    public CustomerState goldState() { return gold; }
    public CustomerState platinumState() { return platinum; }
}
