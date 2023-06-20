package bo.hlva.glopagos.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import bo.hlva.glopagos.data.model.Customer;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import java.util.List;

@Dao
public abstract interface CustomersDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insert(Customer customer);

    @Update
    Completable update(Customer customer);

    @Delete
    Completable delete(Customer customer);

    @Query("SELECT * FROM customers WHERE id=:id")
    Flowable<Customer> getItemCustomer(String id);

    @Query("SELECT * FROM customers")
    Flowable<List<Customer>> getListCustomers();

    @Query("SELECT * FROM customers WHERE name LIKE:query")
    Flowable<List<Customer>> getFilterList(String query);
}
