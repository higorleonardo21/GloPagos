package bo.hlva.glopagos.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import bo.hlva.glopagos.data.model.Customer;
import java.util.List;

@Dao
public abstract interface CustomersDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Customer customer);
    
    @Update
    void update(Customer customer);
    
    @Delete
    void delete(Customer customer);
    
    
    @Query("SELECT * FROM customers WHERE id=:id")
    Customer getItemCustomer(String id);

    @Query("SELECT * FROM customers")
    List<Customer> getListCustomers();
    
    @Query("SELECT * FROM customers WHERE name LIKE:query")
    List<Customer> getFilterList(String query);
}
