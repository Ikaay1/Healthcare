package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class DoctorDetailsActivity extends AppCompatActivity {

    private String[][] doctor_details1 =
            {
                    {"Doctor Name : Antonio Robinson", "Hospital Address : California", "Exp : 10yrs", "Mobile N0:9036289393", "1600"},
                    {"Doctor Name : Michael Ebere", "Hospital Address : New York", "Exp : 5yrs", "Mobile N0:9057947782", "800"},
                    {"Doctor Name : Tina Bukola", "Hospital Address : Chicago", "Exp : 7yrs", "Mobile N0:9078294621", "1000"},
                    {"Doctor Name : Anita Sharon", "Hospital Address : Boston", "Exp : 4yrs", "Mobile N0:9035472826", "500"},
                    {"Doctor Name : Kenny Alexandro", "Hospital Address : New Jersey", "Exp : 9yrs", "Mobile N0:9047357872", "1200"},
            };

    private String[][] doctor_details2 =
            {
                    {"Doctor Name : Ruben Chris", "Hospital Address : California", "Exp : 10yrs", "Mobile N0:9036289393", "1600"},
                    {"Doctor Name : Andres Marquinhos", "Hospital Address : New York", "Exp : 5yrs", "Mobile N0:9057947782", "800"},
                    {"Doctor Name : Perele Madison", "Hospital Address : Chicago", "Exp : 7yrs", "Mobile N0:9078294621", "1000"},
                    {"Doctor Name : Nick David", "Hospital Address : Boston", "Exp : 4yrs", "Mobile N0:9035472826", "500"},
                    {"Doctor Name : Fede Valentine", "Hospital Address : New Jersey", "Exp : 9yrs", "Mobile N0:9047357872", "1200"},
            };

    private String[][] doctor_details3 =
            {
                    {"Doctor Name : Palie Armstrong", "Hospital Address : California", "Exp : 10yrs", "Mobile N0:9036289393", "1600"},
                    {"Doctor Name : Savath Posthe", "Hospital Address : New York", "Exp : 5yrs", "Mobile N0:9057947782", "800"},
                    {"Doctor Name : Machest Andrew", "Hospital Address : Chicago", "Exp : 7yrs", "Mobile N0:9078294621", "1000"},
                    {"Doctor Name : Kyle Bruno", "Hospital Address : Boston", "Exp : 4yrs", "Mobile N0:9035472826", "500"},
                    {"Doctor Name : Marcus Brent", "Hospital Address : New Jersey", "Exp : 9yrs", "Mobile N0:9047357872", "1200"},
            };

    private String[][] doctor_details4 =
            {
                    {"Doctor Name : Polishand Bangus", "Hospital Address : California", "Exp : 10yrs", "Mobile N0:9036289393", "1600"},
                    {"Doctor Name : Cradie Mark", "Hospital Address : New York", "Exp : 5yrs", "Mobile N0:9057947782", "800"},
                    {"Doctor Name : John Dunk", "Hospital Address : Chicago", "Exp : 7yrs", "Mobile N0:9078294621", "1000"},
                    {"Doctor Name : Palmer Coker", "Hospital Address : Boston", "Exp : 4yrs", "Mobile N0:9035472826", "500"},
                    {"Doctor Name : Coldon Mart", "Hospital Address : New Jersey", "Exp : 9yrs", "Mobile N0:9047357872", "1200"},
            };

    private String[][] doctor_details5 =
            {
                    {"Doctor Name : Ikechukwu Mgbemele", "Hospital Address : California", "Exp : 10yrs", "Mobile N0:9036289393", "1600"},
                    {"Doctor Name : Palish Peter", "Hospital Address : New York", "Exp : 5yrs", "Mobile N0:9057947782", "800"},
                    {"Doctor Name : Kayla Catherine", "Hospital Address : Chicago", "Exp : 7yrs", "Mobile N0:9078294621", "1000"},
                    {"Doctor Name : Chukanwa Poloish", "Hospital Address : Boston", "Exp : 4yrs", "Mobile N0:9035472826", "500"},
                    {"Doctor Name : Brandon Willy", "Hospital Address : New Jersey", "Exp : 9yrs", "Mobile N0:9047357872", "1200"},
            };

    private String[][] doctor_details = {};
    private ArrayList list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView textViewTitle = findViewById(R.id.textViewDDTitle);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        textViewTitle.setText(title);

        if (title.equals("Family Physicians")) {
            doctor_details = doctor_details1;
        } else if (title.equals("Dietician")) {
            doctor_details = doctor_details2;
        } else if (title.equals("Dentist")) {
            doctor_details = doctor_details3;
        } else if (title.equals("Surgeon")) {
            doctor_details = doctor_details4;
        } else {
            doctor_details = doctor_details5;
        }

        list = new ArrayList();
        for (int i=0; i < doctor_details.length; i++) {
            HashMap item = new HashMap<String, String>();
            item.put("line1", doctor_details[i][0]);
            item.put("line2", doctor_details[i][1]);
            item.put("line3", doctor_details[i][2]);
            item.put("line4", doctor_details[i][3]);
            item.put("line5", "Cons Fees: $" + doctor_details[i][4]);
            list.add(item);
        }

        SimpleAdapter sa = new SimpleAdapter(this, list, R.layout.multi_lines,
                new String[]{"line1","line2","line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e}
                );

        ListView lst = findViewById(R.id.listViewDD);
        lst.setAdapter(sa);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(DoctorDetailsActivity.this, BookAppointmentActivity.class);
                it.putExtra("title", title);
                it.putExtra("name", doctor_details[i][0]);
                it.putExtra("address", doctor_details[i][1]);
                it.putExtra("mobile", doctor_details[i][3]);
                it.putExtra("fees", doctor_details[i][4]);
                startActivity(it);
            }
        });

        Button backButton = findViewById(R.id.buttonDDBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorDetailsActivity.this, FindDoctorActivity.class));
            }
        });
    }
}