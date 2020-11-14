package coe528.project;

/**
 *
 * @author Ransika Perera
 */
public class Gold implements CustomerState{
    private Customer customer;
    
    public Gold(Customer newCustomer) {
        customer = newCustomer;
    }
    
    @Override
    public void onlinePurchase(double amount) {
        if (amount < 50)
            System.out.println("Purchase amount is too low");
        else {
            customer.withdraw(amount + 10);
            checkLevel();
        }  
    }

    @Override
    public void checkLevel() {
        if (customer.getBalance() < 10000) 
            customer.setCustomerState(customer.silverState());
        else if (customer.getBalance() >= 20000)
            customer.setCustomerState(customer.platinumState());
    } 
}
