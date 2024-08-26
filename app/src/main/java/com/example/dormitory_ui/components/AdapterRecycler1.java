package com.example.dormitory_ui.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dormitory_ui.R;
import com.example.dormitory_ui.models.Contract1;
import java.util.List;

public class AdapterRecycler1 extends RecyclerView.Adapter<AdapterRecycler1.MyView> {
    private final Context context;
    private final List<Contract1> contractLs;
    private OnItemClickListener onItemClickListener;
    
    public AdapterRecycler1(Context context, List<Contract1> contractLs) {
        this.context = context;
        this.contractLs = contractLs;
    }

    public class MyView extends RecyclerView.ViewHolder {
        private final TextView textStName;
        private final TextView textStGender;
        private final TextView textStClass;
        private final TextView textStDepartment;
        private final TextView textStNationality;

        public MyView(@NonNull View itemView) {
            super(itemView);
            textStName = itemView.findViewById(R.id.id_stName);
            textStClass = itemView.findViewById(R.id.id_stClass);
            textStDepartment = itemView.findViewById(R.id.id_stDepartment);
            textStGender = itemView.findViewById(R.id.id_stGender);
            textStNationality = itemView.findViewById(R.id.id_stNationality);

            // Set click listener on the entire item view
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                    onItemClickListener.onItemClick(contractLs.get(position));
                }
            });
        }
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler_1, parent, false);
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, int position) {
        Contract1 contract = contractLs.get(position);

        if (contract.getStudentDTO() != null) {
            holder.textStName.setText(contract.getStudentDTO().getStudentName());
            holder.textStClass.setText(contract.getStudentDTO().getStudentClass());
            holder.textStDepartment.setText(contract.getStudentDTO().getDepartmentOfStudent());
            holder.textStGender.setText(contract.getStudentDTO().isStudentSex() ? "Nam" : "Nữ");
            holder.textStNationality.setText(contract.getStudentDTO().getNationality());
        } else {
            holder.textStName.setText("");
            holder.textStClass.setText("");
            holder.textStDepartment.setText("");
            holder.textStGender.setText("");
            holder.textStNationality.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return contractLs != null ? contractLs.size() : 0;
    }
    
    public interface OnItemClickListener {
        void onItemClick(Contract1 contract);
    }

    // Set the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
