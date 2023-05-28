package bo.hlva.glopagos.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.*;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import bo.hlva.glopagos.R;
import bo.hlva.glopagos.data.model.Customer;
import bo.hlva.glopagos.databinding.ActivityMainBinding;
import bo.hlva.glopagos.ui.AddCustomerActivity;
import bo.hlva.glopagos.ui.details.DetailsActivity;
// import com.itsaky.androidide.logsender.LogSender;

public class MainActivity extends AppCompatActivity
        implements CustomersAdapter.OnItemClickListener, SearchView.OnQueryTextListener {

    private ActivityMainBinding binding;
    private CustomersAdapter customersAdapter;
    private CustomersViewModel customersViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Remove this line if you don't want AndroidIDE to show this app's logs
        //  Logsend.startLogging(this);
        super.onCreate(savedInstanceState);
        // Inflate and get instance of binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // set content view to binding's root
        setContentView(binding.getRoot());

        // init viewmodel
        CustomersViewModel.Factory factory =
                new CustomersViewModel.Factory(getApplicationContext());
        customersViewModel = new ViewModelProvider(this, factory).get(CustomersViewModel.class);

        setupViews();
    }

    @Override
    protected void onResume() {
        super.onResume();

        customersViewModel
                .getListCustomers()
                .observe(
                        this,
                        list -> {
                            customersAdapter.updatesListCustomers(list);
                        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO: Implement this method
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // search view
        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    private void setupViews() {
        // Setup toolbar
        setSupportActionBar(binding.toolbar);

        // click fab
        binding.fab.setOnClickListener(
                v -> {
                    Intent intent = new Intent(this, AddCustomerActivity.class);
                    activityResult.launch(intent);
                });

        // recyclerview
        customersAdapter = new CustomersAdapter(this);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerview.setHasFixedSize(true);
        binding.recyclerview.setAdapter(customersAdapter);

        customersViewModel
                .getListCustomers()
                .observe(
                        this,
                        list -> {
                            customersAdapter.updatesListCustomers(list);
                        });
    }

    private ActivityResultLauncher<Intent> activityResult =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {

                            if (result.getResultCode() == Activity.RESULT_OK) {

                                Customer customer =
                                        (Customer)
                                                result.getData().getSerializableExtra("customer");

                                customersViewModel.addCustomer(customer);

                                customersViewModel
                                        .getListCustomers()
                                        .observe(
                                                MainActivity.this,
                                                list -> {
                                                    customersAdapter.updatesListCustomers(list);
                                                });
                            }
                        }
                    });

    @Override
    public void onItemClick(Customer customer) {

        String idCustomer = String.valueOf(customer.getId());

        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("id", idCustomer);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String nextText) {

        String textFilter = "%" + nextText + "%";

        customersViewModel
                .getFilterList(textFilter)
                .observe(
                        this,
                        list -> {
                            customersAdapter.updatesListCustomers(list);
                        });

        return true;
    }
}
