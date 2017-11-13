package com.a1ubkh4n.app.classroom.material_dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.a1ubkh4n.app.classroom.R;
import com.a1ubkh4n.app.classroom.interfaces.PromptListener;

/**
 * Created by a1ubkh4n on 4/3/2016.
 */
public class PromptDialog extends Dialog {
    private Context context;

    private TextInputLayout inputLayoutContent;
    private EditText content;
    private Button positiveButton;

    private PromptListener promptListener;

    private boolean isAlphanumeric = false;
    private boolean isAllCaps = false;

    public PromptDialog(Context context__) {
        super(context__);
        setContentView(R.layout.prompt_dialog);

        context = context__;

        inputLayoutContent = (TextInputLayout) findViewById(R.id.inputLayoutContent);
        content = (EditText) findViewById(R.id.content);
        positiveButton = (Button) findViewById(R.id.positiveButton);

        content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int keyCode, KeyEvent event) {
                if (keyCode == EditorInfo.IME_ACTION_DONE) {
                    promptPositive();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * Set content to edit
     * @param text Editable text
     */
    public void setContent(String text) {
        if (content != null) {
            content.setText(text);
            content.setSelection(content.getText().length());
        }
    }

    /**
     * Set positive button
     * @param value String
     */
    public void setPositiveButton(String value) {
        positiveButton.setText(value);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptPositive();
            }
        });
    }

    /**
     * Positive button click listener
     * @param promptListener PromptListener
     */
    public void setOnPositiveClickListener(PromptListener promptListener) {
        this.promptListener = promptListener;
    }

    /**
     * Check validation, then prompt as positive
     */
    private void promptPositive() {
        String input;
        if (isAllCaps) {
            input = content.getText().toString().toUpperCase();
        } else {
            input = content.getText().toString();
        }

        if (isAlphanumeric()) {
            if (isValidAlphanumeric(input)) {
                inputLayoutContent.setErrorEnabled(false);

                promptPositive(input);
            } else {
                inputLayoutContent.setError(context.getString(R.string.enterAlphanumeric));
            }
        } else {
            promptPositive(input);
        }
    }

    /**
     * Directly prompt as positive
     * @param input String
     */
    private void promptPositive(String input) {
        if (promptListener != null) {
            promptListener.OnPrompt(input);
        }
    }

    /**
     * Set all characters capital letter
     */
    public void setAllCaps() {
        content.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        isAllCaps = true;
    }

    @Override
    public void show() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        super.show();
    }

    /**
     * Validate if the input is alphanumeric
     * @param s String
     * @return Boolean
     */
    private boolean isValidAlphanumeric(String s){
        String pattern = "^[a-zA-Z0-9\\s]*$";
        if (s.matches(pattern)) {
            return true;
        }
        return false;
    }

    /**
     * Is alphanumeric validation needed
     * @return Boolean
     */
    public boolean isAlphanumeric() {
        return isAlphanumeric;
    }

    /**
     * Make alphanumeric validation obliged
     */
    public void setAlphanumeric() {
        isAlphanumeric = true;
    }
}