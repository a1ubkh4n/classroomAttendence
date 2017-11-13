package com.a1ubkh4n.app.classroom.date_time_pickers;

import android.app.DatePickerDialog;
import android.app.Dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.a1ubkh4n.app.classroom.interfaces.DateBackListener;

import java.util.Calendar;

/**
 * Created by Created by Md.Ayub Khan on 02/12/2016.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private DateBackListener dateBackListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dateBackListener = (DateBackListener) getActivity();

        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        if (dateBackListener != null)
            dateBackListener.OnPress(day, month, year);

        dismiss();
    }
}
