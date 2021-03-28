package com.uber.lastmile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.uber.lastmile.adapters.RouteAdapter;
import com.uber.lastmile.models.RouteOption;

import java.util.ArrayList;
import java.util.List;

public class RouteOptionsActivity extends AppCompatActivity {
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.activity_route_options);

        List<RouteOption> routeOptionList = new ArrayList<>();
        routeOptionList.add(new RouteOption(1, "Fateh Sagar Lake", "Pichola Lake", 15, 500));
        routeOptionList.add(new RouteOption(1, "Radison BLU", "Sankalp Restaurant", 30, 1000));
        routeOptionList.add(new RouteOption(1, "NV Sports", "YMCA Pool", 63, 3700));

        ListView listView = (ListView) findViewById(R.id.list_view);
        ArrayAdapter<RouteOption> adapter = new RouteAdapter(this, routeOptionList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
    }
}