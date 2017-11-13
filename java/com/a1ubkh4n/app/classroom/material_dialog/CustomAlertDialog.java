package com.a1ubkh4n.app.classroom.material_dialog;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.a1ubkh4n.app.classroom.R;
import com.a1ubkh4n.app.classroom.interfaces.OnAlertClick;

/**
 * Created by a1ubkh4n.
 */
public class CustomAlertDialog {
    private Context context;

    private String message = "";
    private String positiveButtonText = "";
    private String negativeButtonText = "";

    private OnAlertClick onAlertClick;

    /**
     * Set context
     * @param context__ Context
     */
    public CustomAlertDialog(Context context__) {
        context = context__;
    }

    /**
     * Set message
     * @param message__ String
     */
    public void setMessage(String message__) {
        message = message__;
    }

    /**
     * Set positive button's text
     * @param positiveButtonText__ String
     */
    public void setPositiveButtonText(String positiveButtonText__) {
        positiveButtonText = positiveButtonText__;
    }

    /**
     * Set negative button's text
     * @param negativeButtonText__ String
     */
    public void setNegativeButtonText(String negativeButtonText__) {
        negativeButtonText = negativeButtonText__;
    }

    /**
     * Set on click listener (positive and negative click)
     * @param onAlertClick
     */
    public void setOnClickListener(OnAlertClick onAlertClick) {
        this.onAlertClick = onAlertClick;
    }

    /**
     * Show relevant dialog
     */
    public void showDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //message
        builder.setMessage(message);
        //positive button
        builder.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (onAlertClick != null) onAlertClick.OnPositive();
            }
        });
        //negative button
        if (negativeButtonText != null && !negativeButtonText.equals("")) {
            builder.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (onAlertClick != null) onAlertClick.OnNegative();
                }
            });
        }
        //create and show
        final AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                        .setTextColor(context.getResources().getColor(R.color.dialogColour));
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        .setTextColor(context.getResources().getColor(R.color.dialogColour));
            }
        });
        alertDialog.show();
    }
}
