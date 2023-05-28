package bo.hlva.glopagos.data.repo;

import android.content.Context;
import bo.hlva.glopagos.data.database.CustomersDatabase;
import bo.hlva.glopagos.data.model.Customer;
import java.util.List;

public class CustomersRepository {

    private CustomersDatabase database;

    public CustomersRepository(Context context) {
        this.database = CustomersDatabase.getInstance(context);
    }

    public void addCustomer(Customer customer) {
        database.getCustomersDao().insert(customer);
    }
    
    public void updateCustomer(Customer customer){
        database.getCustomersDao().update(customer);
    }
    
    public void deleteCustomer(Customer customer){
        database.getCustomersDao().delete(customer);
    }
    
    public Customer getItemCustomer(String id){
        return database.getCustomersDao().getItemCustomer(id);
    }

    public List<Customer> getListCustomers() {
        return database.getCustomersDao().getListCustomers();
    }
    
    public List<Customer> getFilterList(String query){
        return database.getCustomersDao().getFilterList(query);
    }
}
