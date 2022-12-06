package com.example.kalkulatorandroid;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<ItemList> ListHitung;
    private RecyclerView RecyclerViewOperasi;
    private SharedPreferenceAdapter AdapterOperasi;
    private RecyclerView.LayoutManager mLayoutManager;
    RadioGroup operasiGroup;
    RadioButton penjumlahan, pengurangan, perkalian, pembagian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();
        buildRecyclerView();
        setInsertButton();


        Button buttonDelete = findViewById(R.id.button_delete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });

        operasiGroup = findViewById(R.id.operasiGroup);
        penjumlahan = findViewById(R.id.tambahRadio);
        pengurangan = findViewById(R.id.kurangRadio);
        perkalian = findViewById(R.id.kaliRadio);
        pembagian = findViewById(R.id.bagiRadio);

        operasiGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (penjumlahan.isChecked()) {
                    Toast.makeText(MainActivity.this, "Tambah", Toast.LENGTH_SHORT).show();
                } else if (pengurangan.isChecked()) {
                    Toast.makeText(MainActivity.this, "Kurang", Toast.LENGTH_SHORT).show();
                } else if (perkalian.isChecked()) {
                    Toast.makeText(MainActivity.this, "Kali", Toast.LENGTH_SHORT).show();
                } else if (pembagian.isChecked()) {
                    Toast.makeText(MainActivity.this, "Bagi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<ItemList>>() {}.getType();
        ListHitung = gson.fromJson(json, type);

        if (ListHitung == null) {
            ListHitung = new ArrayList<>();
        }
    }

    private void buildRecyclerView() {
        RecyclerViewOperasi = findViewById(R.id.recyclerview);
        RecyclerViewOperasi.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        AdapterOperasi = new SharedPreferenceAdapter(ListHitung);

        RecyclerViewOperasi.setLayoutManager(mLayoutManager);
        RecyclerViewOperasi.setAdapter(AdapterOperasi);
    }

    private void setInsertButton() {
        Button buttonInsert = findViewById(R.id.button_insert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText line1 = findViewById(R.id.edittext_line_1);
                EditText line2 = findViewById(R.id.edittext_line_2);
                insertItem(line1.getText().toString(), line2.getText().toString());
            }
        });
    }

    private void insertItem(String line1, String line2) {
        double hasil = 0;

        if (penjumlahan.isChecked()) {
            hasil = Integer.parseInt(line1) + Integer.parseInt(line2);
        } else if (pengurangan.isChecked()) {
            hasil = Integer.parseInt(line1) - Integer.parseInt(line2);
        } else if (perkalian.isChecked()) {
            hasil = Integer.parseInt(line1) * Integer.parseInt(line2);
        } else if (pembagian.isChecked()) {
            hasil= Double.parseDouble(line1) / Double.parseDouble(line2);
        }

        String operasi = "";
        if (penjumlahan.isChecked()) {
            operasi = "+";
        } else if (pengurangan.isChecked()) {
            operasi = "-";
        } else if (perkalian.isChecked()) {
            operasi = "x";
        } else if (pembagian.isChecked()) {
            operasi = ":";
        }

        ListHitung.add(new ItemList(line1, operasi, line2, String.valueOf(hasil)));
        AdapterOperasi.notifyItemInserted(ListHitung.size());
    }

    @SuppressLint("NotifyDataSetChanged")
    private void deleteData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        ListHitung.clear();
        AdapterOperasi.notifyDataSetChanged();
    }

}