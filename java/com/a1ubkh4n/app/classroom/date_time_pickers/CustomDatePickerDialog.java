package com.a1ubkh4n.app.classroom.date_time_pickers;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.a1ubkh4n.app.classroom.R;
import com.a1ubkh4n.app.classroom.interfaces.DateBackListener;


/**
 * Created by Md.Ayub Khan on 02/12/2016.
 */
public class CustomDatePickerDialog extends Dialog {
    private Context context;
    private DatePicker datePicker;
    private Button buttonOk;

    private DateBackListener dateBackListener;

    public CustomDatePickerDialog(Context context__) {
        super(context__);
        setContentView(R.layout.date_picker);

        context = context__;

        dateBackListener = (DateBackListener) context;

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        datePicker.setCalendarViewShown(false);

        buttonOk = (Button) findViewById(R.id.buttonOk);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dayOfMonth = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();

                if (dateBackListener != null)
                    dateBackListener.OnPress(dayOfMonth, month, year);

                dismiss();
            }
        });
    }
}
