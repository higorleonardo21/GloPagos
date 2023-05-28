package bo.hlva.glopagos.ui.details;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.LinearLayout;
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
// import com.itsaky.androidide.logsender.LogSender;

public class DetailsActivity extends AppCompatActivity {

    private ActivityDetailsBinding binding;
    private CustomersViewModel customerViewModel;
    private CustomerCardAdapter customerCardAdapter;
    private ProgressDialog progressDialog;

    // model class
    private Customer customer;

    private boolean isActiveFab = false;

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

                            ArrayList<Day> list = customer.getDays();
                            // order list inverse
                            Collections.sort(
                                    list,
                                    new Comparator<Day>() {
                                        @Override
                                        public int compare(Day day1, Day day2) {
                                            return new Integer(day2.getNumber())
                                                    .compareTo(new Integer(day1.getNumber()));
                                        }
                                    });

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
                showDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViews() {
        // actionbar
        setSupportActionBar(binding.toolbar);

        customerCardAdapter = new CustomerCardAdapter(this);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerview.setAdapter(customerCardAdapter);

        setupFloatingButtons();
    }

    private void setupFloatingButtons() {

        binding.fabMain.setOnClickListener(
                view -> {
                    if (!isActiveFab) {
                        // show more buttons
                        binding.fabAdd.setVisibility(View.VISIBLE);
                        binding.fabAddNumber.setVisibility(View.VISIBLE);
                        isActiveFab = true;

                    } else {
                        hideButtons();
                    }
                });

        binding.fabAdd.setOnClickListener(
                view -> {
                    if (customer.getDays().size() >= 24) {

                        Toast.makeText(this, "Dias Completados", Toast.LENGTH_SHORT).show();
                    } else {

                        // get date
                        SimpleDateFormat simpleDateFormat =
                                new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                        Date date = new Date();
                        String fecha = simpleDateFormat.format(date);

                        Day day = new Day(false, 20, fecha, customer.getDays().size() + 1);
                        customer.getDays().add(day);
                        customerViewModel.updateCustomer(customer);

                        ArrayList<Day> list = customer.getDays();

                        // order list inverse
                        Collections.sort(
                                list,
                                new Comparator<Day>() {
                                    @Override
                                    public int compare(Day day1, Day day2) {
                                        return new Integer(day2.getNumber())
                                                .compareTo(new Integer(day1.getNumber()));
                                    }
                                });

                        customerCardAdapter.submitList(list);

                        Toast.makeText(
                                        DetailsActivity.this,
                                        "Se Ha Agregados 1 Item",
                                        Toast.LENGTH_SHORT)
                                .show();

                        hideButtons();
                    }
                });

        binding.fabAddNumber.setOnClickListener(
                view -> {
                    showDialogSelectNumber();
                });
    }

    private void hideButtons() {
        binding.fabAdd.setVisibility(View.GONE);
        binding.fabAddNumber.setVisibility(View.GONE);
        isActiveFab = false;
    }

    private void showDialogSelectNumber() {

        String[] items = {"2", "3", "4", "5", "6", "7", "8", "9", "10"};

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Selecciona Cantidad");
        builder.setSingleChoiceItems(
                items,
                -1,
                (dialog, which) -> {
                    Integer counter = Integer.valueOf(items[which]);

                    for (int i = 1; i <= counter; ++i) {

                        if (customer.getDays().size() < 24) {

                            // get date
                            SimpleDateFormat simpleDateFormat =
                                    new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                            Date date = new Date();
                            String fecha = simpleDateFormat.format(date);

                            Day day = new Day(false, 20, fecha, customer.getDays().size() + 1);
                            customer.getDays().add(day);
                            customerViewModel.updateCustomer(customer);

                        } else {
                            break;
                        }
                    }

                    ArrayList<Day> list = customer.getDays();

                    // order list inverse
                    Collections.sort(
                            list,
                            new Comparator<Day>() {
                                @Override
                                public int compare(Day day1, Day day2) {
                                    return new Integer(day2.getNumber())
                                            .compareTo(new Integer(day1.getNumber()));
                                }
                            });

                    customerCardAdapter.submitList(list);

                    Toast.makeText(this, " Se Han Agregados Nuevos Items", Toast.LENGTH_SHORT)
                            .show();
                    dialog.dismiss();
                    hideButtons();
                });

        builder.show();
    }

    private void showDialogDelete() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Borrar Cliente");
        builder.setMessage("¿Deseas Borrar Cliente?");
        builder.setPositiveButton(
                "Si",
                (v, view) -> {
                    customerViewModel.deleteCustomer(customer);
                    finish();
                    builder.create().dismiss();
                });
        builder.setNegativeButton(
                "No",
                (v, view) -> {
                    builder.create().dismiss();
                });

        builder.show();
    }

    private void showDialog() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Informacion de Saldo");
        builder.setMessage(
                "Monto Total A Cancelar:\n"
                        + (customer.getAmount()
                                + MathUtils.calculatePorcentaje(customer.getAmount()))
                        + " bs"
                        + "\n\n"
                        + "Monto Retirado:\n"
                        + customer.getAmount()
                        + " bs"
                        + "\n\n"
                        + "Monto Pendiente:\n"
                        + getValueAmount()
                        + " bs");
        builder.show();
    }

    private int getValueAmount() {

        int amount = customer.getAmount() + MathUtils.calculatePorcentaje(customer.getAmount());
        int day = amount / 24;
        int value = day * customer.getDays().size();

        return amount - value;
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
                        + "Fecha de Retiro:\n"
                        + "2023-05-23");

        builder.show();
    }
}
