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

public class LabTestDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lab_test_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView textViewLTDName = findViewById(R.id.textViewLTDName);
        TextView textViewLTDCost = findViewById(R.id.textViewLTDCost);
        EditText edLTDPackageDetails = findViewById(R.id.editTextLTDPackageDetails);

        edLTDPackageDetails.setKeyListener(null);

        Intent it = getIntent();

        textViewLTDName.setText(it.getStringExtra("text1"));
        edLTDPackageDetails.setText(it.getStringExtra("text2"));
        textViewLTDCost.setText("Total cost: $" + it.getStringExtra("text3"));

        Button btnBack = findViewById(R.id.buttonLTDBack);
        String text = it.getStringExtra("text");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(LabTestDetailsActivity.this, LabTestActivity.class);
                it.putExtra("text", text);
                startActivity(it);
            }
        });

        Button btnAddToCart = findViewById(R.id.buttonLTDAddToCart);

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "").toString();
                String product = it.getStringExtra("text1");
                float price = Float.parseFloat(it.getStringExtra("text3"));
                Database db = new Database(getApplicationContext(), "healthcare", null, 1);

                if (db.checkCart(username, product)){
                    Toast.makeText(getApplicationContext(), "Product already added", Toast.LENGTH_SHORT).show();
                } else {
                    db.addToCart(username, product, price, text);
                    Toast.makeText(getApplicationContext(), "Product added successfully", Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(LabTestDetailsActivity.this, LabTestActivity.class);
                    it.putExtra("text", text);
                    startActivity(it);
                }
            }
        });
    }
}