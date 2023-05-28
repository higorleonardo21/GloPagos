package bo.hlva.glopagos.ui.home;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import bo.hlva.glopagos.data.model.Customer;
import bo.hlva.glopagos.data.repo.CustomersRepository;
import java.util.List;

public class CustomersViewModel extends ViewModel {

    private CustomersRepository repository;

    private MutableLiveData<Customer> _itemCustomer;
    private MutableLiveData<List<Customer>> _listCustomers;
    private MutableLiveData<List<Customer>> _listFilter;

    public CustomersViewModel(Context context) {
        repository = new CustomersRepository(context);

        _itemCustomer = new MutableLiveData<>();
        _listCustomers = new MutableLiveData<>();
        _listFilter = new MutableLiveData<>();
    }

    public void addCustomer(Customer customer) {
        repository.addCustomer(customer);
    }

    public void updateCustomer(Customer customer) {
        repository.updateCustomer(customer);
    }

    public void deleteCustomer(Customer customer) {
        repository.deleteCustomer(customer);
    }

    public LiveData<Customer> getItemCustomer(String id) {
        _itemCustomer.setValue(repository.getItemCustomer(id));
        return _itemCustomer;
    }

    public LiveData<List<Customer>> getListCustomers() {
        _listCustomers.setValue(repository.getListCustomers());
        return _listCustomers;
    }

    public LiveData<List<Customer>> getFilterList(String query) {

        _listFilter.setValue(repository.getFilterList(query));
        return _listFilter;
    }

    public static class Factory implements ViewModelProvider.Factory {

        private Context context;

        public Factory(Context context) {
            this.context = context;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {

            return (T) new CustomersViewModel(context);
        }
    }
}
