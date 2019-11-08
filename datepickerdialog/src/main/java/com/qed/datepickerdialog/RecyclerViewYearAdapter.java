package com.qed.datepickerdialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewYearAdapter extends RecyclerView.Adapter<RecyclerViewYearAdapter.ViewHolder>  {
    private List<Integer> listYear = new ArrayList<Integer>();
    private LayoutInflater mInflater;
    OnItemYearClickListener onItemYearClickListener;

    public RecyclerViewYearAdapter(Context context, List<Integer> listYear) {
        mInflater = LayoutInflater.from(context);
        this.listYear = listYear;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_picker_view, parent, false);
        return new RecyclerViewYearAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtDate.setText(listYear.get(position)+"");
    }

    @Override
    public int getItemCount() {
        return listYear.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtDate.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(onItemYearClickListener != null) {
                onItemYearClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public void setOnItemYearClickListener(OnItemYearClickListener onItemYearClickListener){
        this.onItemYearClickListener = onItemYearClickListener;
    }
}
