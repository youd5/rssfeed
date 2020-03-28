package com.example.rssfeed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class EMICalculatorActivity extends AppCompatActivity {

    private Toolbar myToolbar;
    public static final String EXTRA_MESSAGE = "com.example.emiCalculator.MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_m_i_calculator);
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(getDrawable(R.drawable.favicon32x32));
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void calculate(View view) {
        EditText loanAmountText = (EditText) findViewById(R.id.editloanAmount);
        String loanAmount = loanAmountText.getText().toString();
        EditText roi = (EditText) findViewById(R.id.editroi);
        String rate = roi.getText().toString();
        EditText duration = (EditText) findViewById(R.id.editduration);
        String years = duration.getText().toString();

        Integer principal = (Integer.parseInt(loanAmount));
        Integer interestRate = Integer.parseInt(rate);
        Integer time = Integer.parseInt(years);

        String emi = emiCalculate( Double.valueOf(principal), Double.valueOf(interestRate), Double.valueOf(time));
        // Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.fragment1, EmiResultFragment.newInstance(emi));
        // or ft.add(R.id.your_placeholder, new FooFragment());
        ft.addToBackStack(null);
        // Complete the changes added above
        ft.commit();


        //intent.putExtra(EXTRA_MESSAGE, emi);
        //startActivity(intent);
    }
    // Function to calculate EMI
    static String emiCalculate(Double p, Double rate, Double t) {
        Double emi;
        rate = rate / (12 * 100); // one month interest
        t = t * 12; // one month period
        emi = (p * rate * (float)Math.pow(1 + rate, t))
                / (float)(Math.pow(1 + rate, t) - 1);

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_DOWN);
        return (df.format(emi));
    }
}

