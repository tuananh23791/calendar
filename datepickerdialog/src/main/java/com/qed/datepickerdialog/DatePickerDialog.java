package com.qed.datepickerdialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DatePickerDialog extends DialogFragment implements OnItemYearClickListener {
    private Context context;
    private HashMap<WeekDays, Boolean> listWeekDaysDisable = new HashMap<WeekDays, Boolean>();
    private ViewPager viewPager;
    private ImageView icArrowLeft;
    private ImageView icArrowRight;
    private TextView txtDate;
    private TextView txtYear;
    private TextView txtOk;
    private TextView txtCancel;
    private RecyclerView recyclerViewYear;
    private RecyclerViewYearAdapter adapter;
    private RelativeLayout layoutCalendar;
    public static String stringDateSelected;
    public static boolean isSelected;
    private List<Integer> listYear = new ArrayList<Integer>();
    private HashMap<String, Boolean> calendarListDateDisable = new HashMap<String, Boolean>();
    private OnDatePickerClickListener onDatePickerClickListener;
    int startYear;
    int endYear;

    public DatePickerDialog(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.date_picker_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        setStartEndYear();
        setFristDateSelected();
        initViewPager();
        setListYear();
        setOnClick();
    }

    private void initView() {
        viewPager = (ViewPager) getView().findViewById(R.id.viewPagerCalendar);
        icArrowLeft = (ImageView) getView().findViewById(R.id.icArrowLeft);
        icArrowRight = (ImageView) getView().findViewById(R.id.icArrowRight);
        txtDate = (TextView) getView().findViewById(R.id.txtDate);
        txtYear = (TextView) getView().findViewById(R.id.txtYear);
        txtOk = (TextView) getView().findViewById(R.id.txtOk);
        txtCancel = (TextView) getView().findViewById(R.id.txtCancel);
        recyclerViewYear = (RecyclerView) getView().findViewById(R.id.recyclerViewYear);
        layoutCalendar = (RelativeLayout) getView().findViewById(R.id.layoutCalendar);
    }

    private void setOnClick() {
        txtYear.setOnClickListener(v -> showHideListYear());
        icArrowLeft.setOnClickListener(v -> viewPager.setCurrentItem(viewPager.getCurrentItem() - 1));
        icArrowRight.setOnClickListener(v -> viewPager.setCurrentItem(viewPager.getCurrentItem() + 1));
        txtOk.setOnClickListener(v -> okClick());
        txtCancel.setOnClickListener(v -> dismiss());
    }

    private void okClick() {
        if (onDatePickerClickListener != null && isSelected) {
            Calendar calendar = DateUtils.getInstance().getCalenDarSelected();
            int days = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            onDatePickerClickListener.onOkClick(days, month, year, stringDateSelected);
            dismiss();
        }
    }

    private void initViewPager() {
        ViewPagerPickerAdapter adapter = new ViewPagerPickerAdapter(getChildFragmentManager(), listWeekDaysDisable, calendarListDateDisable);
        viewPager.setAdapter(adapter);
        moveToCalendarSelected(DateUtils.getInstance().getYearSelected());
        setTextCalendarSelected();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                float alpha = 1 - positionOffset - 0.2f == 0 ? 0 : 1 - positionOffset - 0.2f;
                icArrowLeft.setAlpha(alpha);
                icArrowRight.setAlpha(alpha);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void moveToCalendarSelected(int year) {
        viewPager.setCurrentItem(getPositionPager(year), false);
    }

    public void show() {
        FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
        ft.add(this,"fragment_dialog");
        ft.commit();
    }

    private void setTextCalendarSelected() {
        SimpleDateFormat fmtMonth = new SimpleDateFormat("EEE, MMM d");
        SimpleDateFormat fmtYear = new SimpleDateFormat("yyyy");

        Date dateSelected = DateUtils.getInstance().getDateSelected();
        txtDate.setText(fmtMonth.format(dateSelected));
        txtYear.setText(fmtYear.format(dateSelected));
    }

    private void setFristDateSelected() {
        stringDateSelected = DateUtils.getInstance().getCurrentStringDate();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ItemClickEvent event) {
        setTextCalendarSelected();
    }

    public void setOnDatePickerClickListener(OnDatePickerClickListener onDatePickerClickListener) {
        this.onDatePickerClickListener = onDatePickerClickListener;
    }

    private void setStartEndYear() {
        startYear = Calendar.getInstance().get(Calendar.YEAR) - 100;
        endYear = Calendar.getInstance().get(Calendar.YEAR) + 100;
    }

    private void setListYear() {
        for (int i = startYear; i < endYear; i++) {
            listYear.add(i);
        }

        adapter = new RecyclerViewYearAdapter(getActivity(), listYear);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewYear.setLayoutManager(linearLayoutManager);
        recyclerViewYear.setAdapter(adapter);
        linearLayoutManager.scrollToPosition(listYear.size() / 2);
        adapter.setOnItemYearClickListener(this);
    }

    private void showHideListYear() {
        layoutCalendar.setVisibility(layoutCalendar.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        recyclerViewYear.setVisibility(layoutCalendar.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }

    private int getPositionPager(int year) {
        Calendar calendar = DateUtils.getInstance().getCalenDarSelected();
        int month = calendar.get(Calendar.MONTH);
        int position = (year - startYear) * 12 + month;

        return position;
    }

    @Override
    public void onItemClick(View view, int position) {
        Calendar calendar = DateUtils.getInstance().getCalenDarSelected();
        calendar.set(Calendar.YEAR, listYear.get(position));
        showHideListYear();
        moveToCalendarSelected(calendar.get(Calendar.YEAR));
    }

    public void setListDateDisable(String... listDateDisable) {
        for (String dateDisable : listDateDisable) {
            calendarListDateDisable.put(dateDisable, false);
        }
    }

    public void setWeekDaysDisable(WeekDays... weekDaysDisable) {
        for (WeekDays weekDays : weekDaysDisable) {
            listWeekDaysDisable.put(weekDays, false);
        }
    }

    public void setSimpleDateFormat(SimpleDateFormat simpleDateFormat) {
        DateUtils.getInstance().setDateFormat(simpleDateFormat);
    }
}
