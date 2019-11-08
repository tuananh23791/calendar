package com.qed.datepickerdialog;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.HashMap;

public class ViewPagerPickerAdapter extends FragmentStatePagerAdapter {
    HashMap<WeekDays, Boolean> hashMapEnableDays = new HashMap<WeekDays, Boolean>();
    HashMap<String, Boolean> calendarListDateDisable = new HashMap<String, Boolean>();

    public ViewPagerPickerAdapter(@NonNull FragmentManager fm, HashMap<WeekDays, Boolean> hashMapEnableDays
            , HashMap<String, Boolean> calendarListDateDisable) {
        super(fm);
        this.hashMapEnableDays = hashMapEnableDays;
        this.calendarListDateDisable = calendarListDateDisable;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return PickerViewFragment.newInstance(hashMapEnableDays, position, calendarListDateDisable);
    }

    @Override
    public int getCount() {
        return 2400;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int virtualPosition = position;
        return super.instantiateItem(container, virtualPosition);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        int virtualPosition = position;
        super.destroyItem(container, virtualPosition, object);
    }
}
