package coe528.project;

/**
 *
 * @author Ransika Perera
 */
public class Platinum implements CustomerState{
    private Customer customer;
    
    public Platinum(Customer newCustomer) {
        customer = newCustomer;
    }
    
    @Override
    public void onlinePurchase(double amount) {
        if (amount < 50)
            System.out.println("Purchase amount is too low");
        else {
            customer.withdraw(amount);
            checkLevel();
        }  
    }
    
    @Override
    public void checkLevel() {
        if (customer.getBalance() >= 10000 && customer.getBalance() < 20000) 
            customer.setCustomerState(customer.goldState());
        else if (customer.getBalance() < 20000)
            customer.setCustomerState(customer.silverState());
    }     
}
