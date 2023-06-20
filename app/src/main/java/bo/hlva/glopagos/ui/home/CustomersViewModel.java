package bo.hlva.glopagos.ui.home;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import bo.hlva.glopagos.data.model.Customer;
import bo.hlva.glopagos.data.repo.CustomersRepository;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.List;

public class CustomersViewModel extends ViewModel {

    private CustomersRepository repository;
    private CompositeDisposable compositeDisposable;

    private MutableLiveData<Customer> _itemCustomer;
    private MutableLiveData<List<Customer>> _listCustomers;
    private MutableLiveData<List<Customer>> _listFilter;

    public CustomersViewModel(Context context) {
        repository = new CustomersRepository(context);
        compositeDisposable = new CompositeDisposable();

        _itemCustomer = new MutableLiveData<>();
        _listCustomers = new MutableLiveData<>();
        _listFilter = new MutableLiveData<>();
    }

    public void insertCustomer(Customer customer) {
        compositeDisposable.add(
                repository
                        .insertCustomer(customer)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe());
    }

    public void updateCustomer(Customer customer) {
        compositeDisposable.add(
                repository
                        .updateCustomer(customer)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe());
    }

    public void deleteCustomer(Customer customer) {
        compositeDisposable.add(
                repository
                        .deleteCustomer(customer)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe());
    }

    public LiveData<Customer> getItemCustomer(String id) {
        compositeDisposable.add(
                repository
                        .getItemCustomer(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(customer -> _itemCustomer.setValue(customer)));

        return _itemCustomer;
    }

    public LiveData<List<Customer>> getListCustomers() {
        compositeDisposable.add(
                repository
                        .getListCustomers()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(listCustomers -> _listCustomers.setValue(listCustomers)));

        return _listCustomers;
    }

    public LiveData<List<Customer>> getFilterList(String query) {
        compositeDisposable.add(
                repository
                        .getFilterList(query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(filterList -> _listFilter.setValue(filterList)));

        return _listFilter;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
        compositeDisposable.clear();
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
