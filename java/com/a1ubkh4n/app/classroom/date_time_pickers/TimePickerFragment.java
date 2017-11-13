package com.a1ubkh4n.app.classroom.date_time_pickers;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import com.a1ubkh4n.app.classroom.interfaces.DateBackListener;

import java.util.Calendar;

/**
 * Created by Md.Ayub Khan on 02/12/2016.
 */
public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    private DateBackListener dateBackListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dateBackListener = (DateBackListener) getActivity();

        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (dateBackListener != null)
            dateBackListener.OnPress(minute, hourOfDay);

        dismiss();
    }
}
