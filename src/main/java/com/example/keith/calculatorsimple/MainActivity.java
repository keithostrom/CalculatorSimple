package com.example.keith.calculatorsimple;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

    TextView textViewObjectTextDisplay,textViewObjectTextAccum,textViewObjectOperator;
    String tempString;
    char theOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("info","MainActivity.onCreate.start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Number keys plus decimal point and negaitve sign
        Button buttonObjectKey0 = (Button) findViewById(R.id.button0);
        buttonObjectKey0.setOnClickListener(this);
        Button buttonObjectKey1 = (Button) findViewById(R.id.button1);
        buttonObjectKey1.setOnClickListener(this);
        Button buttonObjectKey2 = (Button) findViewById(R.id.button2);
        buttonObjectKey2.setOnClickListener(this);
        Button buttonObjectKey3 = (Button) findViewById(R.id.button3);
        buttonObjectKey3.setOnClickListener(this);
        Button buttonObjectKey4 = (Button) findViewById(R.id.button4);
        buttonObjectKey4.setOnClickListener(this);
        Button buttonObjectKey5 = (Button) findViewById(R.id.button5);
        buttonObjectKey5.setOnClickListener(this);
        Button buttonObjectKey6 = (Button) findViewById(R.id.button6);
        buttonObjectKey6.setOnClickListener(this);
        Button buttonObjectKey7 = (Button) findViewById(R.id.button7);
        buttonObjectKey7.setOnClickListener(this);
        Button buttonObjectKey8 = (Button) findViewById(R.id.button8);
        buttonObjectKey8.setOnClickListener(this);
        Button buttonObjectKey9 = (Button) findViewById(R.id.button9);
        buttonObjectKey9.setOnClickListener(this);
        Button buttonObjectKeyDecPt = (Button) findViewById(R.id.buttonDecPt);
        buttonObjectKeyDecPt.setOnClickListener(this);
        Button buttonObjectKeyNegative = (Button) findViewById(R.id.buttonNegative);
        buttonObjectKeyNegative.setOnClickListener(this);

        // Action keys
        Button buttonObjectKeyPlus = (Button) findViewById(R.id.buttonPlus);
        buttonObjectKeyPlus.setOnClickListener(this);
        Button buttonObjectKeyMinus = (Button) findViewById(R.id.buttonMinus);
        buttonObjectKeyMinus.setOnClickListener(this);
        Button buttonObjectKeyMult = (Button) findViewById(R.id.buttonMult);
        buttonObjectKeyMult.setOnClickListener(this);
        Button buttonObjectKeyDiv = (Button) findViewById(R.id.buttonDiv);
        buttonObjectKeyDiv.setOnClickListener(this);
        Button buttonObjectKeyCE = (Button) findViewById(R.id.buttonCE);
        buttonObjectKeyCE.setOnClickListener(this);
        Button buttonObjectKeyClr = (Button) findViewById(R.id.buttonClr);
        buttonObjectKeyClr.setOnClickListener(this);
        Button buttonObjectKeyQuit = (Button) findViewById(R.id.buttonQuit);
        buttonObjectKeyQuit.setOnClickListener(this);
        Button buttonObjectKeyEqual = (Button) findViewById(R.id.buttonEqual);
        buttonObjectKeyEqual.setOnClickListener(this);

        //Init display fields
        textViewObjectTextDisplay = (TextView) findViewById(R.id.textDisplay);
        textViewObjectTextAccum = (TextView) findViewById(R.id.textAccum);
        textViewObjectOperator = (TextView) findViewById(R.id.textViewOperator);

        doClear();
        Log.i("info","MainActivity.onCreate.end");
    }//End onCreate

    @Override
    public void onClick(View view) {
        String localString1,theKeyString;
        double valueAccum,valueDisplay,valueResult;
        boolean calcError = false;

        Log.i("info","MainActivity.onClick.start");

        switch ( view.getId()) {
            case R.id.button0:
            case R.id.button1:
            case R.id.button2:
            case R.id.button3:
            case R.id.button4:
            case R.id.button5:
            case R.id.button6:
            case R.id.button7:
            case R.id.button8:
            case R.id.button9:
            case R.id.buttonDecPt:
            case R.id.buttonNegative:
                localString1 = (String) textViewObjectTextAccum.getText();
                theKeyString = (String) ((TextView) findViewById(view.getId())).getText();
                Log.i("info","localString1= "+localString1+" theKeyString= "+theKeyString+
                        " Length="+localString1.length());
                if( view.getId() == R.id.buttonDecPt ){
                    Log.i("info","Decimal point processing, localString1= "+localString1);
                    if( localString1.contains(".")) {
                        theKeyString = "";
                    }
                }
                if( view.getId() == R.id.buttonNegative){
                    if (localString1.length() > 0) {
                        theKeyString = "";
                    }
                }
                tempString  = localString1 + theKeyString;
                Log.i("info","tempString = " + tempString);
                textViewObjectTextAccum.setText(tempString);
                break;

            case R.id.buttonEqual:
                localString1 = (String) textViewObjectTextDisplay.getText();
                valueDisplay = Double.parseDouble(localString1);
                Log.i("info","valueDisplay:"+valueDisplay+", String:"+localString1);
                theKeyString = (String) textViewObjectTextAccum.getText();
                valueAccum = Double.parseDouble(theKeyString);
                Log.i("info","valueAccum:"+valueAccum+", String:"+theKeyString);

                if(theKeyString.length() > 0){
                    // Do the math
                     valueResult = 0.0;
                    switch (theOperation){
                        case '+':
                            valueResult = valueDisplay + valueAccum;
                            break;
                        case '-':
                            valueResult = valueDisplay - valueAccum;
                            break;
                        case 'X':
                            valueResult = valueDisplay * valueAccum;
                            break;
                        case '/':
                            try {
                                valueResult = valueDisplay / valueAccum;
                            }
                            catch(ArithmeticException e){
                                calcError = true;
                            }
                            break;
                    } //End switch (theOperation)
                textViewObjectOperator.setText("");
                textViewObjectTextDisplay.setText("");
                textViewObjectTextAccum.setText(""+valueResult);
                }
                break;

            /**
             * All operation buttons except = are treated the same way:
             *  Move number from accumulator to display for storage
             *  Save the type of operation
             *  The equals button will perform the math and update the display
             */
            case R.id.buttonPlus:
            case R.id.buttonMinus:
            case R.id.buttonMult:
            case R.id.buttonDiv:
                tempString = (String) textViewObjectTextAccum.getText();
                if( tempString.length() > 0){
                    textViewObjectTextDisplay.setText(tempString);
                    textViewObjectTextAccum.setText("");
                    tempString = (String) ((TextView) findViewById(view.getId())).getText();
                    theOperation = tempString.charAt(0);
                    textViewObjectOperator.setText(""+theOperation);
                }
                break;

            case R.id.buttonCE:
                tempString = (String) textViewObjectTextAccum.getText();
                if(tempString.length()>=1){
                    textViewObjectTextAccum.setText(tempString.substring(0,tempString.length()-1));
                }
                break;

            case R.id.buttonClr:
                doClear();
                break;

            case R.id.buttonQuit:
                System.exit(0);
                break;

        }//End switch
        Log.i("info","MainActivity.onClick.end");
    }//End onClick

    private void doClear(){
        Log.i("info","MainActivity.doClear.start");
        textViewObjectTextDisplay.setText("");
        textViewObjectTextAccum.setText("");
        textViewObjectOperator.setText("");
        Log.i("info","MainActivity.doClear.end");
    }//End doClear
}//End MainActivity
