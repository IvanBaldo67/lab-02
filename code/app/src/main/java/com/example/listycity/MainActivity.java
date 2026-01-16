package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> datalist;

    int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cityList = findViewById(R.id.city_list);

        String[] cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};
        datalist = new ArrayList<>();
        datalist.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, datalist);
        cityList.setAdapter(cityAdapter);


        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;
                Toast.makeText(MainActivity.this,
                        "Selected: " + datalist.get(position),
                        Toast.LENGTH_SHORT).show();
            }
        });

        //Add City function
        final Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddCityDialog();
            }
        });

        //Delete City Function
        final Button deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCity();
            }
        });
    }
    void showAddCityDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add City");

        // Setting up the input
        final EditText input = new EditText(this);
        input.setHint("Enter city name");

        // Stylizing user input
        int paddingInDp = 20;
        float scale = getResources().getDisplayMetrics().density; // scale = density of the screen
        int paddingInPx = (int) (paddingInDp * scale + 0.5f); // Converts dp to px
        input.setPadding(paddingInPx, paddingInPx, paddingInPx, paddingInPx); // Pads all 4 sides

        builder.setView(input);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String cityName = input.getText().toString().trim();
            if (!cityName.isEmpty()) {
                datalist.add(cityName);
                cityAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this,
                        cityName + " added!", // Message popup with Toast
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this,
                        "City name cannot be empty",
                        Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
        }

        // Method for Deleting the selected city
    void deleteCity(){
        if (selectedPosition != -1 && selectedPosition < datalist.size()) {
            String removedCity = datalist.get(selectedPosition);
            datalist.remove(selectedPosition);
            cityAdapter.notifyDataSetChanged();
            Toast.makeText(this,
                    removedCity + " deleted!", // Message popup with Toast
                    Toast.LENGTH_SHORT).show();
            selectedPosition = -1; // Reset selection
        } else {
            Toast.makeText(this,
                    "Please select a city to delete",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
