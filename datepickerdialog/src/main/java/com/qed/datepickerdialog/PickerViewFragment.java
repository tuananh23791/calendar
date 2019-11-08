package com.qed.datepickerdialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PickerViewFragment extends Fragment implements OnItemClickListener{
    public static String HASH_MAP = "hashMapEnableDays";
    public static String POSITION_PAGER = "CountMonth";
    public static String LIST_DISABLE = "listDisable";
    private RecyclerView recycleViewPicker;
    private RecyclerViewPickerAdapter adapter;
    private TextView txtMonth;
    private int countMonth;
    private int positionPager;
    List<CalendarObject> calendarObjectList = new ArrayList<CalendarObject>();
    HashMap<WeekDays, Boolean> hashMapEnableDays = new HashMap<WeekDays, Boolean>();
    HashMap<String, Boolean> calendarListDateDisable = new HashMap<String, Boolean>();

    public static PickerViewFragment newInstance(HashMap<WeekDays, Boolean> hashMapEnableDays
            , int positionPager, HashMap<String, Boolean> calendarListDateDisable) {
        PickerViewFragment registerUserFragment = new PickerViewFragment();

        Bundle args = new Bundle();
        args.putSerializable(HASH_MAP, hashMapEnableDays);
        args.putSerializable(LIST_DISABLE, (Serializable) calendarListDateDisable);
        args.putInt(POSITION_PAGER, positionPager);
        registerUserFragment.setArguments(args);

        return registerUserFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.picker_view_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reStore(savedInstanceState);
        initView(view);
        printDatesInMonth();
        initRecyclerView();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void initView(View view) {
        recycleViewPicker = view.findViewById(R.id.recycleViewPicker);
        txtMonth = view.findViewById(R.id.txtMonth);
    }

    private void initRecyclerView(){
        adapter = new RecyclerViewPickerAdapter(getActivity(), calendarObjectList);
        adapter.setOnItemClickListener(this);

        int numOfColumns = 7;
        recycleViewPicker.setLayoutManager(new GridLayoutManager(getActivity(), numOfColumns));
        recycleViewPicker.setAdapter(adapter);
    }

    private void getData(){
        Bundle bundle = this.getArguments();
        hashMapEnableDays = (HashMap<WeekDays, Boolean>) bundle.getSerializable(HASH_MAP);
        calendarListDateDisable = (HashMap<String, Boolean>) bundle.getSerializable(LIST_DISABLE);
        positionPager =  bundle.getInt(POSITION_PAGER);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        countMonth = positionPager - ((currentYear - (currentYear - 100)) * 12 + currentMonth) + 1;
    }

    private void reStore(Bundle savedInstanceState){
        if(savedInstanceState != null) {

        }
    }

    private void printDatesInMonth() {
        for (int i = 0; i<42; i++){
            calendarObjectList.add(new CalendarObject());
        }

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + countMonth;

        SimpleDateFormat fmtDay = new SimpleDateFormat("d");
        SimpleDateFormat fmtMonth = new SimpleDateFormat("MMMM  yyyy");
        SimpleDateFormat weekDays = new SimpleDateFormat("EEEE");
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(year, month - 1, 1);
        txtMonth.setText(fmtMonth.format(cal.getTime()));
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 0; i < daysInMonth; i++) {
            int weeks = (cal.get(Calendar.WEEK_OF_MONTH) - 1) * 7;
            int index = (weeks + cal.get(Calendar.DAY_OF_WEEK)) - 1;
            String fullNameDate = DateUtils.getInstance().getSimpleDateFormat().format(cal.getTime());
            calendarObjectList.get(index).setDate(fmtDay.format(cal.getTime()));
            calendarObjectList.get(index).setFullDateName(fullNameDate);
            if(calendarListDateDisable.get(fullNameDate) != null && !calendarListDateDisable.get(fullNameDate)){
                calendarObjectList.get(index).setEnable(false);
            }

            if(hashMapEnableDays.get(getWeekDays(weekDays.format(cal.getTime()))) != null && !hashMapEnableDays.get(getWeekDays(weekDays.format(cal.getTime())))){
                calendarObjectList.get(index).setEnable(false);
            }
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }

    }

    private WeekDays getWeekDays(String name){
        switch (name.toUpperCase()){
            case "MONDAY": return WeekDays.MONDAY;
            case "TUESDAY": return WeekDays.TUESDAY;
            case "WEDNESDAY": return WeekDays.WEDNESDAY;
            case "THURSDAY": return WeekDays.THURSDAY;
            case "FRIDAY": return WeekDays.FRIDAY;
            case "SATURDAY": return WeekDays.SATURDAY;
            case "SUNDAY": return WeekDays.SUNDAY;
        }

        return null;
    }

    @Override
    public void onItemClick(Date date) {
    }
}