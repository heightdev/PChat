package com.poo.pchat.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.poo.pchat.R;

/**
 * This class represents a Custom Dialog.
 */
public class CustomDialog {

    private Dialog mDialog;
    private TextView mMessage;
    private EditText mInput;
    private Button mCancel;
    private Button mOk;

    /**
     * Disabled constructor method.
     */
    private CustomDialog(){}

    /**
     * Constructor method.
     * @param context - The context.
     */
    public CustomDialog(Context context){
        mDialog = new Dialog(context);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.custom_dialog);

        mMessage = (TextView) mDialog.findViewById(R.id.custom_dialog_message);
        mInput = (EditText) mDialog.findViewById(R.id.custom_dialog_input);

        mCancel = (Button) mDialog.findViewById(R.id.custom_dialog_cancel_bt);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mOk = (Button) mDialog.findViewById(R.id.custom_dialog_ok_bt);
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /**
     * Shows the dialog.
     */
    public void show(){
        if(mDialog != null && !mDialog.isShowing())
            mDialog.show();
    }

    /**
     * Dismiss the dialog.
     */
    public void dismiss(){
        if(mDialog != null && mDialog.isShowing())
            mDialog.dismiss();
    }

    /**
     * Changes dialog message.
     * @param txt - New message.
     */
    public void setMessage(CharSequence txt){
        if(mMessage != null && txt != null)
            mMessage.setText(txt);
    }

    /**
     * Reads the input.
     * @return - Read text.
     */
    public String readInput(){
        if(mInput != null)
            return mInput.getText().toString();

        return null;
    }

    /**
     * Sets the input text.
     * @param txt - New input text.
     */
    public void setInputText(CharSequence txt){
        if(mInput != null)
            mInput.setText(txt);
    }

    /**
     * Sets an OnClickListener to OK button.
     * @param onClick - OnClickListener.
     */
    public void setOkOnClickListener(View.OnClickListener onClick){
        if(mOk != null)
            mOk.setOnClickListener(onClick);
    }

    /**
    * Sets an OnClickListener to OK button.
    * @param onClick - OnClickListener.
    */
    public void setCancelOnClickListener(View.OnClickListener onClick){
        if(mCancel != null)
            mCancel.setOnClickListener(onClick);
    }
}
