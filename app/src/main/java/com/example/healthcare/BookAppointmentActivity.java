package com.example.healthcare;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;
import java.util.Date;

public class BookAppointmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_appointment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText edName = findViewById(R.id.editTextAppFullName);
        EditText edAddress = findViewById(R.id.editTextAppAddress);
        EditText edContact = findViewById(R.id.editTextAppContact);
        EditText edFees = findViewById(R.id.editTextAppFees);

        edName.setKeyListener(null);
        edAddress.setKeyListener(null);
        edContact.setKeyListener(null);
        edFees.setKeyListener(null);

        Intent it = getIntent();
        String title = it.getStringExtra("title");
        String name = it.getStringExtra("name");
        String address = it.getStringExtra("address");
        String mobile = it.getStringExtra("mobile");
        String fees = it.getStringExtra("fees");

        TextView edTitle = findViewById(R.id.textViewAppTitle);
        edTitle.setText(title);
        edName.setText(name);
        edAddress.setText(address);
        edContact.setText(mobile);
        edFees.setText("Fee: $" + fees);

        Button btnBack = findViewById(R.id.buttonAppBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(BookAppointmentActivity.this, DoctorDetailsActivity.class);
                it.putExtra("title", title);
                startActivity(it);
            }
        });



        final Calendar c = Calendar.getInstance();
        final int[] mYear = {c.get(Calendar.YEAR)};
        final int[] mMonth = {c.get(Calendar.MONTH)};
        final int[] mDay = {c.get(Calendar.DAY_OF_MONTH) + 1};

        EditText editTextDate = findViewById(R.id.editTextDate);
        EditText editTextTime = findViewById(R.id.editTextTime);

        editTextDate.setKeyListener(null);
        editTextTime.setKeyListener(null);

        editTextDate.setText((mDay[0]) + "/" + (mMonth[0] + 1) + "/" + mYear[0]);

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DatePickerDialog datePickerDialog = new DatePickerDialog(BookAppointmentActivity.this,
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
                TimePickerDialog timePickerDialog = new TimePickerDialog(BookAppointmentActivity.this,
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

        Button btnBookAppointment = findViewById(R.id.buttonAppBook);
        btnBookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Database db = new Database(getApplicationContext(), "healthcare", null, 1);
                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "");
                String date = editTextDate.getText().toString();
                String time = editTextTime.getText().toString();
                if (db.checkAppointmentExists(username, title + " => " + name, address, mobile, date, time)){
                    Toast.makeText(getApplicationContext(), "Appointment already booked", Toast.LENGTH_SHORT).show();
                } else {
                    db.addOrder(username, title + " => " + name, address, mobile, 0, date, time, Float.parseFloat(fees), "appointment");
                    Toast.makeText(getApplicationContext(), "Appointment booked successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(BookAppointmentActivity.this, HomeActivity.class));
                }
            }
        });
    }
}