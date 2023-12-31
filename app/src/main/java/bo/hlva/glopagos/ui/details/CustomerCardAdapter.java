package bo.hlva.glopagos.ui.details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import bo.hlva.glopagos.data.model.Day;
import bo.hlva.glopagos.databinding.ItemCustomerCardBinding;
import java.util.ArrayList;

public class CustomerCardAdapter extends RecyclerView.Adapter<CustomerCardAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Day> listItems;
    private int amountDay;
    
    private OnDayListener listener;

    public CustomerCardAdapter(Context context,OnDayListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void submitList(ArrayList<Day> list, int amountDay) {
        this.listItems = list;
        this.amountDay = amountDay;
        notifyDataSetChanged();
    }

    @Override
    public CustomerCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemCustomerCardBinding binding =
                ItemCustomerCardBinding.inflate(
                        LayoutInflater.from(parent.getContext()), parent, false);
        return new CustomerCardAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CustomerCardAdapter.ViewHolder holder, int position) {

        Day day = listItems.get(position);

        holder.binding.itemNumber.setText(String.valueOf(day.getNumber()));
        holder.binding.itemDate.setText(day.getDate());

        holder.binding.itemAmount.setText(String.valueOf(day.getAmount()) + "bs");

        if (day.getAmount() >= amountDay) {
            holder.binding.itemEdit.setVisibility(View.GONE);
        }
        
        holder.binding.itemEdit.setOnClickListener(view ->{
            listener.onEditDay(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemCustomerCardBinding binding;

        public ViewHolder(ItemCustomerCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    
    public interface OnDayListener{
        void onEditDay(int position);
    }
}
