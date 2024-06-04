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

public class LabTestActivity extends AppCompatActivity {

    private String[][] packages1 =
            {
                    {"Package 1 : Full Body Checkup", "", "", "", "999"},
                    {"Package 2 : Blood Glucose Fasting", "", "", "", "299"},
                    {"Package 3 : COVID-19 Antibody - Ig6", "", "", "", "899"},
                    {"Package 4 : Thyroid Check", "", "", "", "499"},
                    {"Package 5 : Immunity Check", "", "", "", "699"},
            };

    private String[][] packages2 =
            {
                    {"Uprise-D3 1000IU Capsule", "", "", "", "50"},
                    {"HealthVit Chronium Picolinate 206mcg Capsule", "", "", "", "305"},
                    {"Vitamin B Complex Capsules", "", "", "", "448"},
                    {"Inlife Vitamin E Wheat Germ Oil Capsule", "", "", "", "539"},
                    {"Dolo 650 Tablet", "", "", "", "30"},
                    {"Crocin 650 Advance Tablet", "", "", "", "50"},
                    {"Strepsils Medicated Lozenges for Sore Throat", "", "", "", "40"},
                    {"Tata 1mg Calcium + Vitamin D3", "", "", "", "30"},
                    {"Feronia -XT Tablet", "", "", "", "130"},
            };

    private String[] package_details1 = {
        "Blood Glucose Fasting\n" +
                " Complete Hemogram\n" +
                "HbA1c\n" +
                " Iron Studies\n" +
                "Kidney Function Test\n" +
                "LDK Lactate Dehydrogenase, Serum\n" +
                "Lipid Profile\n" +
                "Liver Function Test",
        "Blood Glucose Fasting",
        "COVID-19 Antibody - 1g6",
        "Thyroid Profile-Total (T3, T4 & TSH Ultra-sensitive)",
        "Complete Hemogram\n" +
                "CRP (C Reactive Protein) Quantitative, Serum\n" +
                " Iron Studies\n" +
                "Kidney Function Test\n" +
                "Vitamin D Total-25 Hydroxy\n" +
                "Liver Function Test\n" +
                "Lipid Profile"
    };

    private String[] package_details2 = {
            "Building and keeping the bones & teeth strong\n" +

                    "Reducing Fatigue/stress and muscular pains\n" +

                    "Boosting immunity and increasing resistance against infection",

            "Chromium is an essential trace mineral that plays an important role in helping insulin regulate blood glucose",

            "Provides relief from vitamin B deficiencies\n" +

                    "Helps in formation of red blood cells\n" +

                    "Maintains healthy nervous system",

            "It promotes health as well as skin benefit. \n" +

                    "It helps reduce skin blemish and pigmentation. In" +

                    "It act as safeguard the skin from the harsh UVA and UVB sun rays.",

            "Dolo 650 Tablet helps relieve pain and fever by blocking the release of certain chemical messengers responsible for fever and pain",

            "Helps relieve fever and bring down a high temperature\n" +

                    "Suitable for people with a heart condition or high blood pressure",

            "Relieves the symptoms of a bacterial throat infection and soothes the recovery process\n" +

                    "Provides a warm and comforting feeling during sore throat",

            "Reduces the risk of calcium deficiency, Rickets, and Osteoporosis\n" +

                    "Promotes mobility and flexibility of joints",

            "Helps to reduce the iron deficiency due to chronic blood loss or Low intake of iron"
    };

    private String[][] packages;
    private String[] package_details;

    ArrayList list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lab_test);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent it = getIntent();
        String text = it.getStringExtra("text");

        if (text.equals("medicine")) {
            TextView textViewLTSubTitle = findViewById(R.id.textViewLTSubTitle);
            textViewLTSubTitle.setText("Buy Medicine");
        }

        packages = text.equals("lab") ? packages1 : packages2;
        package_details = text.equals("lab") ? package_details1 : package_details2;

        list = new ArrayList();
        for (int i=0; i < packages.length; i++) {
            HashMap item = new HashMap<String, String>();
            item.put("line1", packages[i][0]);
            item.put("line2", packages[i][1]);
            item.put("line3", packages[i][2]);
            item.put("line4", packages[i][3]);
            item.put("line5", "Total Cost: $" + packages[i][4]);
            list.add(item);
        }

        SimpleAdapter sa = new SimpleAdapter(this, list, R.layout.multi_lines,
                new String[]{"line1","line2","line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e}
        );

        ListView lst = findViewById(R.id.listViewLT);
        lst.setAdapter(sa);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(LabTestActivity.this, LabTestDetailsActivity.class);
                it.putExtra("text", text);
                it.putExtra("text1", packages[i][0]);
                it.putExtra("text2", package_details[i]);
                it.putExtra("text3", packages[i][4]);
                startActivity(it);
            }
        });

        Button btnBack = findViewById(R.id.buttonLTBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LabTestActivity.this, HomeActivity.class));
            }
        });

        Button btnGoToCart = findViewById(R.id.buttonLTCart);
        btnGoToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(LabTestActivity.this, CartLabActivity.class);
                it.putExtra("text", text);
                startActivity(it);
            }
        });
    }
}