package com.example.healthcare;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class CartLabActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart_lab);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent it = getIntent();

        String text = it.getStringExtra("text");

        EditText editTextTime = findViewById(R.id.editTextCLTime);

        if (text.equals("medicine")) {
            TextView textViewCLTime = findViewById(R.id.textViewCLTime);
            TextView textViewCLDate = findViewById(R.id.textViewCLDate);
            textViewCLDate.setText("Select Deliver Date");
            textViewCLTime.setVisibility(View.INVISIBLE);
            editTextTime.setVisibility(View.INVISIBLE);
        }

        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        Database db = new Database(getApplicationContext(), "healthcare", null, 1);
        ArrayList arr = db.getCartData(username, text);

        ArrayList list = new ArrayList();
        float totalAmount = 0;

        for (int i=0; i < arr.size(); i++) {
            HashMap item = new HashMap();
            String[] strings = arr.get(i).toString().split(java.util.regex.Pattern.quote("$"));
            item.put("line1", strings[0]);
            item.put("line2", "");
            item.put("line3", "");
            item.put("line4", "");
            item.put("line5", "Cost: $" + strings[1]);
            totalAmount += Float.parseFloat(strings[1]);
            list.add(item);
        }

        TextView textTotal = findViewById(R.id.textViewCLCost);
        textTotal.setText("Total Cost: $" + totalAmount);

        SimpleAdapter sa = new SimpleAdapter(this, list, R.layout.multi_lines,
                new String[]{"line1","line2","line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e}
        );

        ListView lst = findViewById(R.id.listViewCL);
        lst.setAdapter(sa);

        Button btnBack = findViewById(R.id.buttonCLBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(CartLabActivity.this, LabTestActivity.class);
                it.putExtra("text", text);
                startActivity(it);
            }
        });

        Button btnCheckout = findViewById(R.id.buttonCLCheckout);
        float finalTotalAmount = totalAmount;
        EditText editTextDate = findViewById(R.id.editTextCLDate);

        editTextDate.setKeyListener(null);
        editTextTime.setKeyListener(null);

        final Calendar c = Calendar.getInstance();
        final int[] mYear = {c.get(Calendar.YEAR)};
        final int[] mMonth = {c.get(Calendar.MONTH)};
        final int[] mDay = {c.get(Calendar.DAY_OF_MONTH) + 1};

        editTextDate.setText((mDay[0]) + "/" + (mMonth[0] + 1) + "/" + mYear[0]);

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DatePickerDialog datePickerDialog = new DatePickerDialog(CartLabActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                editTextDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                mDay[0] = dayOfMonth;
                                mMonth[0] = monthOfYear;
                                mYear[0] = year;
                            }
                        }, mYear[0], mMonth[0], mDay[0]);
                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                datePickerDialog.show();
            }
        });


        final int[] mHour = {c.get(Calendar.HOUR_OF_DAY)};
        final int[] mMinute = {c.get(Calendar.MINUTE)};

        Date date = new Date();
        c.setTime(date);
        c.add(Calendar.DATE, 1);

        editTextTime.setText((String.valueOf(mHour[0]).length() == 1 ? "0" + mHour[0] : mHour[0]) + ":" + (String.valueOf(mMinute[0]).length() == 1 ? "0" + mMinute[0] : mMinute[0]));

        editTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(CartLabActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                editTextTime.setText((String.valueOf(hourOfDay).length() == 1 ? "0" + hourOfDay : hourOfDay) + ":" + (String.valueOf(minute).length() == 1 ? "0" + minute : minute));
                                mHour[0] = hourOfDay;
                                mMinute[0] = minute;
                            }
                        }, mHour[0], mMinute[0], false);
                timePickerDialog.show();
            }
        });

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(CartLabActivity.this, LabTestBookActivity.class);
                it.putExtra("text", text);
                it.putExtra("price", "" + finalTotalAmount);
                it.putExtra("date", editTextDate.getText().toString());
                it.putExtra("time", editTextTime.getText().toString());
                startActivity(it);
            }
        });
    }
}