package com.example.healthcare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

        Button btnBookAppointment = findViewById(R.id.buttonAppBook);
        btnBookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Database db = new Database(getApplicationContext(), "healthcare", null, 1);
                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "");
                String date = ((EditText) findViewById(R.id.editTextDate)).getText().toString();
                String time = ((EditText) findViewById(R.id.editTextTime)).getText().toString();
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