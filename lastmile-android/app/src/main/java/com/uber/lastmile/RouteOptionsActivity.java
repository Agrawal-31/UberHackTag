package com.uber.lastmile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uber.lastmile.adapters.RouteAdapter;
import com.uber.lastmile.models.RouteOption;

import java.util.Comparator;
import java.util.List;

public class RouteOptionsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ListView listView;
    List<RouteOption> routeOptionList;

    Comparator<RouteOption> compareByReward = new Comparator<RouteOption>() {
        @Override
        public int compare(RouteOption o2, RouteOption o1) {
            return o1.getReward().compareTo(o2.getReward());
        }
    };

    Comparator<RouteOption> compareByTime = new Comparator<RouteOption>() {
        @Override
        public int compare(RouteOption o1, RouteOption o2) {
            return o1.getDuration().compareTo(o2.getDuration());
        }
    };

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.activity_route_options);

        Gson gson = new Gson();
        routeOptionList = gson.fromJson(getIntent().getStringExtra("data"), new TypeToken<List<RouteOption>>() {
        }.getType());

        String riderData = getIntent().getStringExtra("rider-data");

        listView = findViewById(R.id.list_view);
        ArrayAdapter<RouteOption> adapter = new RouteAdapter(this, routeOptionList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String myJson = gson.toJson(routeOptionList.get(position));
                Intent intent = new Intent(view.getContext(), MapsActivity.class);
                intent.putExtra("data", myJson);
                intent.putExtra("rider-data", riderData);
                startActivity(intent);
            }
        });

        Spinner spinner = findViewById(R.id.sort_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.sort_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 1) {
            routeOptionList.sort(compareByReward);
        } else if (position == 2) {
            routeOptionList.sort(compareByTime);
        } else {
            return;
        }

        ArrayAdapter<RouteOption> adapter = new RouteAdapter(this, routeOptionList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}