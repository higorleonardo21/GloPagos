package bo.hlva.glopagos.ui.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import bo.hlva.glopagos.data.model.Customer;
import bo.hlva.glopagos.databinding.ItemCustomerBinding;
import java.util.ArrayList;
import java.util.List;

public class CustomersAdapter extends RecyclerView.Adapter<CustomersAdapter.ViewHolder> {

    private List<Customer> listCustomers = new ArrayList<>();

    private OnItemClickListener onItemClickListener;

    public CustomersAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public CustomersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemCustomerBinding binding =
                ItemCustomerBinding.inflate(
                        LayoutInflater.from(parent.getContext()), parent, false);
        return new CustomersAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CustomersAdapter.ViewHolder holder, int position) {

        Customer customer = listCustomers.get(holder.getAdapterPosition());
        holder.binding.itemName.setText(customer.getName());
        holder.binding.itemLastName.setText(customer.getLastName());

        // show number
        if (customer.getDays().isEmpty()) {
            holder.binding.itemNumber.setText("0");
        } else {
            String value = String.valueOf(customer.getDays().size());
            holder.binding.itemNumber.setText(value);
        }

        // click item
        holder.binding
                .getRoot()
                .setOnClickListener(
                        view -> {
                            onItemClickListener.onItemClick(customer);
                        });
    }

    @Override
    public int getItemCount() {
        return listCustomers.size();
    }

    public void updatesListCustomers(List<Customer> list) {
        this.listCustomers = list;
        notifyDataSetChanged();
    }

    public void addCustomer(Customer customer) {
        listCustomers.add(customer);
        notifyItemInserted(listCustomers.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemCustomerBinding binding;

        public ViewHolder(ItemCustomerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Customer customer);
    }
}
