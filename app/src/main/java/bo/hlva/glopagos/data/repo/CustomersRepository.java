package bo.hlva.glopagos.data.repo;

import android.content.Context;
import bo.hlva.glopagos.data.database.CustomersDatabase;
import bo.hlva.glopagos.data.model.Customer;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import java.util.List;

public class CustomersRepository {

    private CustomersDatabase database;

    public CustomersRepository(Context context) {
        this.database = CustomersDatabase.getInstance(context);
    }

    public Completable insertCustomer(Customer customer) {
        return database.getCustomersDao().insert(customer);
    }

    public Completable updateCustomer(Customer customer) {
        return database.getCustomersDao().update(customer);
    }

    public Completable deleteCustomer(Customer customer) {
        return database.getCustomersDao().delete(customer);
    }

    public Flowable<Customer> getItemCustomer(String id) {
        return database.getCustomersDao().getItemCustomer(id);
    }

    public Flowable<List<Customer>> getListCustomers() {
        return database.getCustomersDao().getListCustomers();
    }

    public Flowable<List<Customer>> getFilterList(String query) {
        return database.getCustomersDao().getFilterList(query);
    }
}
