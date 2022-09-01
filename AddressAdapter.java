package com.example.sizzlingbites.ui.user.home.apdater;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sizzlingbites.R;
import com.example.sizzlingbites.ui.user.home.model.AddressModel;

import java.util.ArrayList;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    Context context;
    ArrayList<AddressModel> addressModels;
    SelectedAddress selectedAddress;

    private RadioButton isSelectedbtn;

    public AddressAdapter(Context context, ArrayList<AddressModel> addressModels, SelectedAddress selectedAddress) {
        this.context = context;
        this.addressModels = addressModels;
        this.selectedAddress = selectedAddress;
    }

    @NonNull
    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create layout inflater
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtAddress.setText(addressModels.get(position).getUserAddress());
        holder.isSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (AddressModel address : addressModels) {
                    address.setSelected(false);
                }
                addressModels.get(position).setSelected(false);
                if (isSelectedbtn != null) {
                    isSelectedbtn.setChecked(false);
                }
                isSelectedbtn = (RadioButton) view;
                isSelectedbtn.setChecked(true);
                //isSelectedbtn.setAddress(addressModels.get(position).getUserAddress());
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressModels.size();// return number of records
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //init layout views
        TextView txtAddress;
        RadioButton isSelected;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //init ui views
            this.txtAddress = itemView.findViewById(R.id.txtAddress);
            this.isSelected = itemView.findViewById(R.id.select_address);
        }
    }

    public interface SelectedAddress {
        void setAddress(String address);
    }
}
