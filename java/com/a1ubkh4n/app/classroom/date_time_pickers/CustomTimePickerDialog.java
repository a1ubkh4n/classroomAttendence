package com.a1ubkh4n.app.classroom.date_time_pickers;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.a1ubkh4n.app.classroom.R;
import com.a1ubkh4n.app.classroom.interfaces.DateBackListener;

/**
 * Created by Created by Md.Ayub Khan on 02/12/2016..
 */
public class CustomTimePickerDialog extends Dialog {
    private Context context;
    private TimePicker timePicker;
    private Button buttonOk;

    private DateBackListener dateBackListener;

    public CustomTimePickerDialog(Context context__) {
        super(context__);
        setContentView(R.layout.time_picker);

        context = context__;

        dateBackListener = (DateBackListener) context;

        timePicker = (TimePicker) findViewById(R.id.timePicker);

        buttonOk = (Button) findViewById(R.id.buttonOk);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int minute = timePicker.getCurrentMinute();
                int hour = timePicker.getCurrentHour();

                if (dateBackListener != null)
                    dateBackListener.OnPress(minute, hour);

                dismiss();
            }
        });
    }
}
