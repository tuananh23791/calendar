package com.qed.datepickerdialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewPickerAdapter extends RecyclerView.Adapter<RecyclerViewPickerAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    private LayoutInflater mInflater;
    private List<CalendarObject> calendarObjectList = new ArrayList<CalendarObject>();

    public RecyclerViewPickerAdapter(Context context, List<CalendarObject> calendarObjectList) {
        mInflater = LayoutInflater.from(context);
        this.calendarObjectList = calendarObjectList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_picker_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CalendarObject calendarObject = calendarObjectList.get(position);

        boolean isEnable = calendarObject.isEnable();

        holder.txtDate.setText(calendarObject.getDate());


        if(calendarObject.getFullDateName().equals(DatePickerDialog.stringDateSelected) && calendarObject.isEnable()){
            setStyleDateSelected(holder.txtDate, true);
            DatePickerDialog.isSelected = true;
        }else {
            setStyleDateSelected(holder.txtDate, false);

            if (!isEnable)
                holder.txtDate.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.gray));
            else
                holder.txtDate.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return calendarObjectList.size();
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
            CalendarObject calendarObject = calendarObjectList.get(getAdapterPosition());
            if(calendarObject.isEnable()){
                DatePickerDialog.stringDateSelected = calendarObject.getFullDateName();
                notifyDataSetChanged();

                if(onItemClickListener != null) {
                    EventBus.getDefault().post(new ItemClickEvent(DateUtils.getInstance().getDateSelected()));
                }
            }
        }
    }

    void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    private void setStyleDateSelected(TextView textView, boolean isSelected){
        textView.setBackgroundResource(isSelected ? R.drawable.circle_textview : 0);
        textView.setTextColor(textView.getContext().getResources().getColor(isSelected ? R.color.white : R.color.black));
    }
}
