package com.synergyapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextWatcher {
    EditText childTicketsCount;
    TextView childTicketsPrice;

    EditText adultTicketsCount;
    EditText adultTicketsPrice;

    EditText retireTicketsCount;
    TextView retireTicketsPrice;

    double summaryValue = 0.0;
    TextView summary;

    ImageButton calculate;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        childTicketsCount = findViewById(R.id.child_ticket_count);
        childTicketsPrice = findViewById(R.id.child_ticket_price);

        adultTicketsCount = findViewById(R.id.adult_ticket_count);
        adultTicketsPrice = findViewById(R.id.adult_ticket_price);
        adultTicketsPrice.addTextChangedListener(this);

        retireTicketsCount = findViewById(R.id.retire_ticket_count);
        retireTicketsPrice = findViewById(R.id.retire_ticket_price);

        summary = findViewById(R.id.result);

        calculate = findViewById(R.id.calculate);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double adultPrice = calculateCategorySummary(adultTicketsCount.getText(), Integer.parseInt(adultTicketsPrice.getText().toString()));
                double childPrice = calculateCategorySummary(childTicketsCount.getText(), adultPrice * .5);
                double retirePrice = calculateCategorySummary(retireTicketsCount.getText(), adultPrice * .7);

                summaryValue = adultPrice + childPrice + retirePrice;
                summary.setText(String.format(Locale.getDefault(),"Всего: %.1f", summaryValue));
            }
        });
    }

    private <T extends Number> double calculateCategorySummary(Editable count, T price) {
        return Double.parseDouble(count.toString()) * price.doubleValue();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        int value = Integer.parseInt(s.toString());
        if (s.length() == 0) {
            childTicketsPrice.setText("50% скидка");
            retireTicketsPrice.setText("30% скидка");
        } else {
            childTicketsPrice.setText(String.valueOf(value * .5));
            retireTicketsPrice.setText(String.valueOf(value * .7));
        }
    }
}