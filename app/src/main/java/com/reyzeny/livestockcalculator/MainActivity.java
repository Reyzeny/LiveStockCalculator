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
        //Crop harvest date
        //and  planting date
        //days to maturity
    }

    private void initComponents() {
        tvPlantationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Toast.makeText(MainActivity.this, "year is " + String.valueOf(year) + " month is " + String.valueOf(month) + " day is " + String.valueOf(day), Toast.LENGTH_LONG).show();
                        int display_month = month + 1;
                        Toast.makeText(MainActivity.this, "display month is " + String.valueOf(display_month), Toast.LENGTH_SHORT).show();
                        plantationCalendar.set(Calendar.YEAR, year);
                        plantationCalendar.set(Calendar.MONTH, month);
                        plantationCalendar.set(Calendar.DAY_OF_MONTH, day);
                        String plantation_date = String.valueOf(plantationCalendar.get(Calendar.DAY_OF_MONTH)) +"-"+String.valueOf(display_month)+"-"+String.valueOf(plantationCalendar.get(Calendar.YEAR));
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
        Toast.makeText(this, "Calculating harvest date", Toast.LENGTH_SHORT).show();
        int maturity_days = edtMaturityDays.getText().toString().isEmpty() ? 0 : Integer.parseInt(edtMaturityDays.getText().toString());
        System.out.println("maturity day is " + maturity_days);
        Toast.makeText(this, "maturity day is " + String.valueOf(maturity_days), Toast.LENGTH_SHORT).show();
        if (plantationDateSet && maturity_days > 0) {
            long maturity_days_in_milliseconds = 86400 * 1000 * maturity_days;
            harvestCalendar.setTimeInMillis(plantationCalendar.getTimeInMillis() + maturity_days_in_milliseconds);
            int day = harvestCalendar.get(Calendar.DAY_OF_MONTH);
            int month = harvestCalendar.get(Calendar.MONTH);
            int year = harvestCalendar.get(Calendar.YEAR);
            String harvest_date = String.valueOf(day)+"-"+String.valueOf(++month)+"-"+String.valueOf(year);
            Toast.makeText(this, "harvest date is " + harvest_date, Toast.LENGTH_SHORT).show();
            tvHarvestDate.setText(harvest_date);
        }else {
            tvHarvestDate.setText("");
            Toast.makeText(this, "making it empty", Toast.LENGTH_SHORT).show();
        }
    }
}
