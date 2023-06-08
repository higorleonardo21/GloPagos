package bo.hlva.glopagos.ui.details;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import bo.hlva.glopagos.R;
import bo.hlva.glopagos.data.model.Customer;
import bo.hlva.glopagos.data.model.Day;
import bo.hlva.glopagos.databinding.ActivityDetailsBinding;
import bo.hlva.glopagos.ui.home.CustomersViewModel;
import bo.hlva.glopagos.utils.MathUtils;
import bo.hlva.glopagos.utils.Utils;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
// import com.itsaky.androidide.logsender.LogSender;

public class DetailsActivity extends AppCompatActivity {

    private ActivityDetailsBinding binding;
    private CustomersViewModel customerViewModel;
    private CustomerCardAdapter customerCardAdapter;
    private ProgressDialog progressDialog;

    // model class
    private Customer customer;

    // select day
    private int selectNumberDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // log AndroidIDE
        //  LogSender.startLogging(this);
        super.onCreate(savedInstanceState);

        // inflate views binding
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // init viewmodel
        CustomersViewModel.Factory factory =
                new CustomersViewModel.Factory(getApplicationContext());
        customerViewModel = new ViewModelProvider(this, factory).get(CustomersViewModel.class);

        // show dialog progress
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando Lista, Esperar..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // get customer from id
        String id = getIntent().getExtras().getString("id");
        customerViewModel
                .getItemCustomer(id)
                .observe(
                        this,
                        model -> {
                            this.customer = model;

                            // settitle actionbar
                            getSupportActionBar()
                                    .setTitle(customer.getName() + " " + customer.getLastName());

                            ArrayList<Day> list = Utils.getOrganizedList(customer.getDays());
                            customerCardAdapter.submitList(list);

                            progressDialog.hide();
                        });

        setupViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO: Implement this method
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO: Implement this method
        switch (item.getItemId()) {
            case R.id.menu_item_details_about:
                showDialogDetails();
                return true;

            case R.id.menu_item_details_delete:
                showDialogDelete();
                return true;
            case R.id.menu_item_details_call:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + customer.getPhone()));
                startActivity(intent);
                return true;
            case R.id.menu_item_details_cash:
                showDialogInfo();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViews() {
        // actionbar
        setSupportActionBar(binding.toolbar);

        customerCardAdapter = new CustomerCardAdapter(this);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerview.setAdapter(customerCardAdapter);

        // fab
        binding.fab.setOnClickListener(
                view -> {
                    showDialogSelectNumber();
                });
    }

    private void showDialogSelectNumber() {

        String[] items = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Selecciona Cantidad");
        builder.setSingleChoiceItems(
                items,
                -1,
                (dialog, which) -> {
                    selectNumberDay = Integer.valueOf(items[which]);
                });
        builder.setPositiveButton(
                "Agregar",
                (dialog, which) -> {
                    for (int i = 1; i <= selectNumberDay; ++i) {

                        if (customer.getDays().size() < 24) {

                            Day day =
                                    new Day(
                                            false,
                                            20,
                                            Utils.getCurrentDate(),
                                            customer.getDays().size() + 1);
                            customer.getDays().add(day);
                            customerViewModel.updateCustomer(customer);

                        } else {
                            break;
                        }
                    }

                    ArrayList<Day> list = Utils.getOrganizedList(customer.getDays());
                    customerCardAdapter.submitList(list);
                    builder.create().dismiss();
                });
        builder.setNegativeButton(
                "Cancelar",
                (dialog, which) -> {
                    builder.create().dismiss();
                });

        builder.show();
    }

    private void showDialogDelete() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Borrar Cliente");
        builder.setMessage("¿Deseas Borrar Cliente?");
        builder.setPositiveButton(
                "Si",
                (dialog, which) -> {
                    customerViewModel.deleteCustomer(customer);
                    finish();
                    builder.create().dismiss();
                });
        builder.setNegativeButton(
                "No",
                (dialog, which) -> {
                    builder.create().dismiss();
                });

        builder.show();
    }

    private void showDialogInfo() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Informacion de Saldo");
        builder.setMessage(
                "Monto Total A Cancelar:\n"
                        + (customer.getAmount()
                                + MathUtils.calculatePercentage(customer.getAmount()))
                        + " bs"
                        + "\n\n"
                        + "Monto Retirado:\n"
                        + customer.getAmount()
                        + " bs"
                        + "\n\n"
                        + "Monto Pendiente:\n"
                        + MathUtils.getBalanceDue(customer)
                        + " bs");
        builder.show();
    }

    private void showDialogDetails() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Informacion");
        builder.setMessage(
                "Nombre:\n"
                        + customer.getName()
                        + "\n\n"
                        + "Apellidos:\n"
                        + customer.getLastName()
                        + "\n\n"
                        + "Numero C.I:\n"
                        + customer.getIdNumber()
                        + "\n\n"
                        + "Telefono:\n"
                        + customer.getPhone()
                        + "\n\n"
                        + "Monto Retirado:\n"
                        + customer.getAmount()
                        + " bs"
                        + "\n\n"
                        + "Porcentaje de Retiro:\n"
                        + customer.getPercentage()
                        + "\n\n"
                        + "Fecha de Retiro:\n"
                        + customer.getDate());

        builder.show();
    }
}
