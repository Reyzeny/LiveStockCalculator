package com.reyzeny.livestockcalculator;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView tvPlantationDate, tvHarvestDate;
    EditText edtMaturityDays;
    Calendar plantationCalendar = Calendar.getInstance(), harvestCalendar = Calendar.getInstance();
    boolean plantationDateSet = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvPlantationDate = findViewById(R.id.tvPlantationDate);
        edtMaturityDays = findViewById(R.id.edtMaturityDate);
        tvHarvestDate = findViewById(R.id.tvHarvestDate);
        initComponents();
    }

    private void initComponents() {
        tvPlantationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        int display_month = month + 1;
                        plantationCalendar.set(Calendar.YEAR, year);
                        plantationCalendar.set(Calendar.MONTH, month);
                        plantationCalendar.set(Calendar.DAY_OF_MONTH, day);
                        String plantation_date = String.valueOf(String.format("%02d", plantationCalendar.get(Calendar.DAY_OF_MONTH))) +"-"+String.valueOf(String.format("%02d", display_month))+"-"+String.valueOf(plantationCalendar.get(Calendar.YEAR));
                        tvPlantationDate.setText(plantation_date);
                        plantationDateSet = true;
                        CalculateHarvestDate();
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setTitle("SELECT PLANTATION DATE");
                datePickerDialog.show();
            }
        });
        edtMaturityDays.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                CalculateHarvestDate();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    private void CalculateHarvestDate() {
        int maturity_days = edtMaturityDays.getText().toString().isEmpty() ? 0 : Integer.parseInt(edtMaturityDays.getText().toString());
        if (plantationDateSet && maturity_days > 0) {
            harvestCalendar.setTimeInMillis(plantationCalendar.getTimeInMillis());
            harvestCalendar.add(Calendar.DATE, maturity_days);
            int day = harvestCalendar.get(Calendar.DAY_OF_MONTH);
            int month = harvestCalendar.get(Calendar.MONTH);
            int year = harvestCalendar.get(Calendar.YEAR);
            String harvest_date = String.valueOf(String.format("%02d", day))+"-"+String.valueOf(String.format("%02d", ++month))+"-"+String.valueOf(year);
            tvHarvestDate.setText("Harvest Date is " + harvest_date);
        }else{
            tvHarvestDate.setText("");
        }
    }
}
