package bo.hlva.glopagos.ui.details;

import android.app.ProgressDialog;
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
import bo.hlva.glopagos.databinding.DialogAddNumberBinding;
import bo.hlva.glopagos.ui.home.CustomersViewModel;
import bo.hlva.glopagos.utils.MathUtils;
import bo.hlva.glopagos.utils.MessageUtils;
import bo.hlva.glopagos.utils.Utils;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import com.itsaky.androidide.logsender.LogSender;

public class DetailsActivity extends AppCompatActivity
        implements CustomerCardAdapter.OnDayListener {

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
        LogSender.startLogging(this);
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

                            binding.recyclerview.setAdapter(customerCardAdapter);
                            customerCardAdapter.submitList(list, MathUtils.getAmountDay(customer));

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

        customerCardAdapter = new CustomerCardAdapter(this, this);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));

        // fab
        binding.fab.setOnClickListener(
                view -> {
                    showDialogSelectNumber();
                });

        binding.fab.setOnLongClickListener(
                view -> {
                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
                    builder.setTitle("Ingrese Cantidad");

                    // inflate view
                    DialogAddNumberBinding dialogBinding =
                            DialogAddNumberBinding.inflate(getLayoutInflater());
                    builder.setView(dialogBinding.getRoot());

                    builder.setPositiveButton(
                            "Agregar",
                            (dialog, which) -> {
                                if (!dialogBinding.edtNumber.getText().toString().isEmpty()) {
                                    int amount =
                                            Integer.valueOf(
                                                    dialogBinding.edtNumber.getText().toString());
                                    addToDatabase(false, amount);
                                }
                            });
                    builder.setNegativeButton(
                            "Cancelar",
                            (dialog, which) -> {
                                builder.create().dismiss();
                            });

                    builder.create().show();

                    return true;
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
                    addToDatabase(true, 0);
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
        builder.setMessage(MessageUtils.infoCustomer(customer));
        builder.show();
    }

    private void showDialogDetails() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Informacion");
        builder.setMessage(MessageUtils.detailsCustomer(customer));
        builder.show();
    }

    private void addToDatabase(boolean isCompleteDay, int amount) {

        if (isCompleteDay) {

            for (int i = 1; i <= selectNumberDay; ++i) {

                if (customer.getDays().size() < 24) {

                    Day day =
                            new Day(
                                    MathUtils.getAmountDay(customer),
                                    Utils.getCurrentDate(),
                                    customer.getDays().size() + 1);

                    customer.getDays().add(day);

                } else {
                    break;
                }
            }

        } else {
            if (amount > 0) {

                Day day = new Day(amount, Utils.getCurrentDate(), customer.getDays().size() + 1);

                customer.getDays().add(day);

            } else {
                Toast.makeText(this, "Cantidad no valida", Toast.LENGTH_SHORT).show();
            }
        }

        customerViewModel.updateCustomer(customer);

        ArrayList<Day> list = Utils.getOrganizedList(customer.getDays());
        customerCardAdapter.submitList(list, MathUtils.getAmountDay(customer));
    }

    @Override
    public void onEditDay(int position) {

        Day day = customer.getDays().get(position);

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Ingrese Cantidad");

        // inflate view
        DialogAddNumberBinding dialogBinding = DialogAddNumberBinding.inflate(getLayoutInflater());
        builder.setView(dialogBinding.getRoot());

        builder.setPositiveButton(
                "Agregar",
                (dialog, which) -> {
                    if (!dialogBinding.edtNumber.getText().toString().isEmpty()) {
                        int amount = Integer.valueOf(dialogBinding.edtNumber.getText().toString());

                        if (amount > 0) {
                            day.setAmount(amount);

                            customerViewModel.updateCustomer(customer);

                            ArrayList<Day> list = Utils.getOrganizedList(customer.getDays());
                            customerCardAdapter.submitList(list, MathUtils.getAmountDay(customer));

                        } else {
                            Toast.makeText(this, "Cantidad no valida", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        builder.setNegativeButton(
                "Cancelar",
                (dialog, which) -> {
                    builder.create().dismiss();
                });

        builder.create().show();
    }
}
