package com.dilutioncalculator1.admin.dilutioncalculator1;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.math.BigDecimal;
import java.util.Arrays;


/**
 * Created by admin on 12/08/2017.
 */

public class MainFragment extends Fragment { // Fragment from support lib

    private static final String TAG = "MainFragment";

    Button mCalculateButton;
    Button mResetButton;
    EditText mInputC1;
    EditText mInputC2;
    EditText mInputV1;
    EditText mInputV2;
    EditText[] mInputEdits = {mInputC1, mInputC2, mInputV1, mInputV2};
    TextView mOutputVar;
    TextView mOutputAnswer;
    TextView mOutputUnits;
    String mOutputString = null;
    Float mOutputFloat = 0.0f;


    @Override
    public void onCreate(Bundle savedInstanceState) { // all Fragment lifecycle methods must be public
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);     // pass false to add view in the activity


        mInputEdits[0] = (EditText) v.findViewById(R.id.C_1_input);
        mInputEdits[0].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (getActivity() == null) {
                    return;
                }

                // ...
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mInputEdits[1] = (EditText) v.findViewById(R.id.C_2_input);
        mInputEdits[1].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (getActivity() == null) {
                    return;
                }

                // ...
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mInputEdits[2] = (EditText) v.findViewById(R.id.V_1_input);
        mInputEdits[2].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (getActivity() == null) {
                    return;
                }

                // ...
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mInputEdits[3] = (EditText) v.findViewById(R.id.V_2_input);
        mInputEdits[3].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (getActivity() == null) {
                    return;
                }

                // ...
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mOutputVar = (TextView) v.findViewById(R.id.ans_output_var);
        mOutputVar.setText("");

        mOutputAnswer = (TextView) v.findViewById(R.id.ans_output);
        mOutputUnits = (TextView) v.findViewById(R.id.ans_units);

        mCalculateButton = (Button) v.findViewById(R.id.buttonCalculate);
        mCalculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the input as String (reset onClick)
                String C1String = "";
                String C2String = "";
                String V1String = "";
                String V2String = "";
                String[] inputStrings = {C1String, C2String, V1String, V2String};

                for(int j=0; j < inputStrings.length; j++) {
                        inputStrings[j] = mInputEdits[j].getText().toString();
                }

                // convert input String to float
                float C1Float = 0.0f;
                float C2Float = 0.0f;
                float V1Float = 0.0f;
                float V2Float = 0.0f;
                float[] inputFloats = {C1Float, C2Float, V1Float, V2Float};    // "float" not "Float"

                for(int k = 0; k < inputFloats.length; k++) {
                    if(!TextUtils.isEmpty(inputStrings[k])) {
                        inputFloats[k] = Float.valueOf(inputStrings[k]);
                    }
                }

                // x = ?
                // use simple dilution equation: C1 * V1 = C2 * V2
                if(TextUtils.isEmpty(inputStrings[0])
                        && !(TextUtils.isEmpty(inputStrings[1]))
                        && !(TextUtils.isEmpty(inputStrings[2]))
                        && !(TextUtils.isEmpty(inputStrings[3]))) { // x = C1
                    mOutputFloat = (inputFloats[1] * inputFloats[3]) / inputFloats[2];
                    mOutputVar.setText(getResources().getString(R.string.C_1));
                    mOutputUnits.setText(getResources().getString(R.string.unit_conc));
                    setOutputHelper(0);
                } else if(TextUtils.isEmpty(inputStrings[1])
                        && !(TextUtils.isEmpty(inputStrings[0]))
                        && !(TextUtils.isEmpty(inputStrings[2]))
                        && !(TextUtils.isEmpty(inputStrings[3]))) { // x = C2
                    mOutputFloat = (inputFloats[0] * inputFloats[2]) / inputFloats[3];
                    mOutputVar.setText(getResources().getString(R.string.C_2));
                    mOutputUnits.setText(getResources().getString(R.string.unit_conc));
                    setOutputHelper(1);
                } else if(TextUtils.isEmpty(inputStrings[2])
                        && !(TextUtils.isEmpty(inputStrings[1]))
                        && !(TextUtils.isEmpty(inputStrings[0]))
                        && !(TextUtils.isEmpty(inputStrings[3]))) { // x = V1
                    mOutputFloat = (inputFloats[1] * inputFloats[3]) / inputFloats[0];
                    mOutputVar.setText(getResources().getString(R.string.V_1));
                    mOutputUnits.setText(getResources().getString(R.string.unit_vol));
                    setOutputHelper(2);
                } else if(TextUtils.isEmpty(inputStrings[3])
                        && !(TextUtils.isEmpty(inputStrings[0]))
                        && !(TextUtils.isEmpty(inputStrings[1]))
                        && !(TextUtils.isEmpty(inputStrings[2]))) { // x = V2
                    mOutputFloat = (inputFloats[0] * inputFloats[2]) / inputFloats[1];
                    mOutputVar.setText(getResources().getString(R.string.V_2));
                    mOutputUnits.setText(getResources().getString(R.string.unit_vol));
                    setOutputHelper(3);
                }

                // setError(s) for all empty fields
                // (single & correct empty fields are negatively selected)
                for(int i = 0; i < mInputEdits.length; i++) {
                    if(mInputEdits[i].getText().toString().equals(""))
                            mInputEdits[i].setError("Only one variable can be empty.");
                }

                if(mOutputString != null) {
                    // uncomment the  below line to:
                    // replace all blank spaces, and trim head and tail
                    // mOutputAnswer.setText(mOutputString.replaceAll("\\s+", "").trim());

                    mOutputAnswer.setText(useScientificNotation(mOutputString.trim()));

                    // reset
                    reset();
                }
            }
        });

        mResetButton = (Button) v.findViewById(R.id.buttonReset);
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();

                // reset answer fields, too
                mOutputVar.setText("");
                mOutputUnits.setText("");
                mOutputAnswer.setText("");
            }
        });

        return v;
    }

    void reset() {
        for(int i = 0; i < mInputEdits.length; i++) {
            if(!mInputEdits[i].getText().toString().equals("")) {  // if mInputEdits' text isn't empty
                mInputEdits[i].getText().clear();
            } else if(mInputEdits[i].getError() != null){ // if mInputEdits' errorMsg isn't empty
                mInputEdits[i].setError(null);
            }
        }

        mOutputString = null;
        mOutputFloat = 0.0f;
    }

    void setOutputHelper(int i) {
        mOutputString = String.valueOf(mOutputFloat);
        mInputEdits[i].setError(null);
    }

    public String useScientificNotation(String input) {
        String output = "";

        if(isScientificNotation(input)) {  // if string input contains exponent "E"
            int inputE = input.indexOf("E");      // get position for "E"

            output = input.substring(0, inputE); // extract substring from 0 to "E"
            output += " \u00D7 10^";             // add "x 10^"
            output += input.substring(inputE + 1, input.length()); // set magnitude to SFs
        } else {
            output = input;
        }

        return output;
    }

    static boolean isScientificNotation(String input) {
        try {
            new BigDecimal(input);
        } catch (NumberFormatException e) {
            return false;
        }

        // returns true if the string contains "E"
        return input.toUpperCase().contains("E");
    }

//    int getMinSF(float[] inputArray) {
//        int minSF = Integer.MAX_VALUE;
//
//        for(int i = 0; i < inputArray.length; i++) {
//            if (inputArray[i] < minSF) {
//                minSF = Math.round(inputArray[i]);
//            }
//        }
//        return minSF;
//    }
//
//    int getNumberOfDecimals(String input) {
//        int index = input.indexOf(".");
//        return index < 0 ? 0 : input.length() - index - 1;
//        //return Math.max(0, bigDecimal.scale());
//    }

    //----------------------------------------------------------------------------------------------
    // Menu
    //----------------------------------------------------------------------------------------------

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);  // not required

        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_about:
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
                builder.setTitle(R.string.menu_about)
                        .setMessage(R.string.menu_about_msg)
                        .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
