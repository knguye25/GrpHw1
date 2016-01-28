package com.example.knguye25.computercalculator;
/*
* Assignment: HW1
* FileName Group12_HW01.zip
* Team: Khanh Nguyen, Kettyl Amoakon, Vaibhav Sapkal*/
import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    double tipPercent, result, budget;
    TextView tipDisp, priceDisp, statusDisp;
    EditText budgetDisp;
    RadioGroup memorySz, storageSz;
    CheckBox[] accessories = new CheckBox[4];
    CheckBox mouseChBx, coolChBx, caseChBx, flashChBx;
    Button calculateBtn, resetBtn;
    Switch deliverySwt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.pc);

        //switch setup
        deliverySwt = (Switch) findViewById(R.id.DeliverySwt);

        //setup budget editText
        budgetDisp = (EditText) findViewById(R.id.BudgetDisp);

        //setup the TextViews
        statusDisp = (TextView) findViewById(R.id.StatusDisp);
        priceDisp = (TextView) findViewById(R.id.priceDisp);
        priceDisp.setText(getResources().getString(R.string.PriceLbl)+new DecimalFormat("0.00").format(0));

        //redefined Checkbox
        accessories[0] = (CheckBox) findViewById(R.id.mouseChBx);
        accessories[1] = (CheckBox) findViewById(R.id.coolChBx);
        accessories[2] = (CheckBox) findViewById(R.id.flashChBx);
        accessories[3] = (CheckBox) findViewById(R.id.carryingCaseChBx);

        //radio group setups
        memorySz = (RadioGroup) findViewById(R.id.MemorySzRdGrp);
        storageSz = (RadioGroup) findViewById(R.id.StorageSzRdGrp);

        //setup tip%
        tipDisp = (TextView) findViewById(R.id.TipDisp);
        final SeekBar sb = (SeekBar) findViewById(R.id.tipSkBr);
        tipPercent = sb.getProgress() * 5;
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tipDisp.setText(progress*5+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
//                Log.d("demoSeekBar", "value=" + sb.getProgress());
                tipPercent = sb.getProgress() * 5;
            }
        });

        //calculateButton
        calculateBtn = (Button) findViewById(R.id.calculateBtn);
        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (budgetDisp.getText().toString().isEmpty())
                    budgetDisp.setError("Enter a dollar anount");
                else{
                    budget = Double.parseDouble(budgetDisp.getText().toString());
                    int Memory = 2, Size = 250, accessoriesCount = 0, Delivery = 1;
                    double totalCost;

                    if (!deliverySwt.isChecked())
                        Delivery = 0;

                    switch (memorySz.getCheckedRadioButtonId()) {
                        case R.id.Mem2GBRdBtn:
                            Memory = 2;
                            break;
                        case R.id.Mem4GBRdBtn:
                            Memory = 4;
                            break;
                        case R.id.Mem8GBRdBtn:
                            Memory = 8;
                            break;
                        case R.id.Mem16GBRdBtn:
                            Memory = 16;
                            break;
                    }

                    switch (storageSz.getCheckedRadioButtonId()) {
                        case R.id.GB250:
                            Size = 250;
                            break;
                        case R.id.GB500:
                            Size = 500;
                            break;
                        case R.id.GB750:
                            Size = 750;
                            break;
                        case R.id.TB1:
                            Size = 1000;
                            break;
                    }

                    for (CheckBox extras : accessories)
                        if (extras.isChecked())
                            accessoriesCount++;

                    totalCost = ((10 * Memory + 0.75*Size + 20*accessoriesCount) * (1+ (tipPercent/100))) + 5.95 * Delivery;
                    priceDisp.setText(getResources().getString(R.string.PriceLbl)+ new DecimalFormat("0.00").format(totalCost));
                    statusCalc(totalCost);
                }

            }
        });

        //resetButton
        resetBtn = (Button) findViewById(R.id.resetBtn);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //reseting budget input
                budgetDisp.setText("");

                //reseting the seek bar
                tipDisp.setText(R.string.defaultTipAmount);
                sb.setProgress(3);
                tipPercent = sb.getProgress() * 5;

                //reset price display
                budget = 0;
                String temp = getResources().getString(R.string.PriceLbl);
                priceDisp.setText(temp + new DecimalFormat("0.00").format(budget));

                //reset radio groups choices
                memorySz.check(R.id.Mem2GBRdBtn);
                storageSz.check(R.id.GB250);

                //reseting delivery switch
                deliverySwt.setChecked(true);

                //reseting checkboxes
                for(CheckBox cb : accessories)
                    cb.setChecked(false);

                statusDisp.setText("");
                statusDisp.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });


    }

    public void statusCalc(double totalCost){
        if (totalCost <= budget)
        {
            statusDisp.setText(getResources().getString(R.string.inBudgetDisp));
            statusDisp.setBackgroundColor(getResources().getColor(R.color.Green));
        }
        else
        {
            statusDisp.setText(getResources().getString(R.string.notInBudgetDisp));
            statusDisp.setBackgroundColor(getResources().getColor(R.color.Red));

        }

    }

}
