package bo.hlva.glopagos.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import bo.hlva.glopagos.data.model.Customer;
import bo.hlva.glopagos.databinding.ActivityAddCustomerBinding;
import bo.hlva.glopagos.utils.Utils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
// import com.itsaky.androidide.logsender.LogSender;

public class AddCustomerActivity extends AppCompatActivity {

    private ActivityAddCustomerBinding binding;

    private String selectAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // log androide
        // LogSender.startLogging(this);
        super.onCreate(savedInstanceState);

        // binding views
        binding = ActivityAddCustomerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupViews();
    }

    private void setupViews() {
        // actionbar
        setSupportActionBar(binding.toolbar);

        onFocusChanged();

        // setup autocomplete

        String[] list = {"200", "300", "400", "500", "600", "700", "800", "900", "1000"};
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        binding.autocomplete.setAdapter(adapter);
        binding.autocomplete.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(
                            AdapterView<?> parent, View view, int position, long id) {

                        selectAmount = parent.getItemAtPosition(position).toString();
                    }
                });

        // click fab
        binding.fab.setOnClickListener(
                v -> {
                    String name = binding.edtName.getText().toString();
                    String lastName = binding.edtLastname.getText().toString();
                    String idNumber = binding.edtIdNumber.getText().toString();
                    String phone = binding.edtPhone.getText().toString();

                    if (verifyInput(name, lastName, idNumber, phone)) {
                    
                        String date = Utils.getCurrentDate();

                        Customer customer =
                                new Customer(
                                        name,
                                        lastName,
                                        idNumber,
                                        phone,
                                        date,
                                        Integer.valueOf(selectAmount));

                        showDialogSave(customer);
                    }
                });
    }

    private void showDialogSave(Customer customer) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setMessage("¿Deseas Guardar Cliente?");
        builder.setPositiveButton(
                "Guardar",
                (v, view) -> {
                    Intent intent = new Intent();
                    intent.putExtra("customer", customer);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                });
        builder.setNegativeButton(
                "No",
                (v, view) -> {
                    builder.create().dismiss();
                });

        builder.show();
    }

    private void onFocusChanged() {
        binding.edtName.setOnFocusChangeListener(
                (__, focused) -> {
                    if (focused) binding.layoutName.setError("");
                });
        binding.edtLastname.setOnFocusChangeListener(
                (__, focused) -> {
                    if (focused) binding.layoutLastname.setError("");
                });

        binding.edtIdNumber.setOnFocusChangeListener(
                (__, focused) -> {
                    if (focused) binding.layoutIdNumer.setError("");
                });

        binding.edtPhone.setOnFocusChangeListener(
                (__, focused) -> {
                    if (focused) binding.layoutPhone.setError("");
                });
    }

    private boolean verifyInput(String name, String lastName, String idNumber, String phone) {

        if (TextUtils.isEmpty(name)) {
            binding.layoutName.setError("Campo Vacio");
            return false;
        } else if (TextUtils.isEmpty(lastName)) {
            binding.layoutLastname.setError("Campo Vacio");
            return false;
        } else if (TextUtils.isEmpty(idNumber)) {
            binding.layoutIdNumer.setError("Campo Vacio");
            return false;
        } else if (TextUtils.isEmpty(phone)) {
            binding.layoutPhone.setError("Campo Vacio");
            return false;
        }

        return true;
    }
}
