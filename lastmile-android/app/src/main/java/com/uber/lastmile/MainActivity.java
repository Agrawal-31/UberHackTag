package com.uber.lastmile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uber.lastmile.models.RiderRoute;
import com.uber.lastmile.models.RouteOption;
import com.uber.lastmile.utils.SharedPrefManager;
import com.uber.lastmile.utils.VolleySingleton;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.uber.lastmile.constants.constants.ROUTE_URL;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    SharedPrefManager sharedPrefManager;
    Button letsGoButton;
    RiderRoute riderRoute;

    List<RouteOption> routeOptionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        riderRoute = new RiderRoute();

        // Initialize the SDK
        Places.initialize(getApplicationContext(), getResources().getString(R.string.google_maps_key));

        // Create a new PlacesClient instance
        PlacesClient placesClient = Places.createClient(this);

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragmentSource = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment_source);

        // Specify the types of place data to return.
        autocompleteFragmentSource.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragmentSource.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                LatLng latLng = place.getLatLng();
                riderRoute.setRiderSourceX(latLng.latitude);
                riderRoute.setRiderSourceY(latLng.longitude);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
            }


            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragmentDestination = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment_destination);

        // Specify the types of place data to return.
        autocompleteFragmentDestination.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragmentDestination.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                LatLng latLng = place.getLatLng();
                riderRoute.setRiderDestinationX(latLng.latitude);
                riderRoute.setRiderDestinationY(latLng.longitude);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
            }


            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        letsGoButton = (Button) this.findViewById(R.id.button);
        letsGoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                validator();
            }
        });

        sharedPrefManager = SharedPrefManager.getInstance(this);

        if (sharedPrefManager.isLoggedIn()) {
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    void validator() {
        if (riderRoute.getRiderDestinationX() == null || riderRoute.getRiderDestinationY() == null
                || riderRoute.getRiderSourceX() == null || riderRoute.getRiderSourceY() == null) {
            Toast.makeText(getApplicationContext(), "Please select both locations", Toast.LENGTH_SHORT).show();
            return;
        }
        fetchRoutes();
    }


    public void fetchRoutes() {
        Log.d(TAG, "Login");

        letsGoButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Looking for suitable pick-up");
        progressDialog.show();

        try {
            Gson gson = new Gson();
            final String requestBody = gson.toJson(riderRoute);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ROUTE_URL,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            Gson gson = new Gson();
                            routeOptionList = gson.fromJson(response, new TypeToken<List<RouteOption>>() {
                            }.getType());
                            nextActivity();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            try {
                                String body = new String(error.networkResponse.data, "UTF-8");
                                JSONObject obj = new JSONObject(body);
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", "Bearer " + sharedPrefManager.getJWT());
                    return params;
                }
            };

            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        } catch (Exception e) {
            Log.e("VOLLEY", e.toString());
        }
    }

    public void nextActivity() {
        letsGoButton.setEnabled(true);
        Gson gson = new Gson();
        String myJson = gson.toJson(routeOptionList);
        Intent intent = new Intent(this, RouteOptionsActivity.class);
        intent.putExtra("data", myJson);
        myJson = gson.toJson(riderRoute);
        intent.putExtra("rider-data", myJson);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sign_out) {
            SharedPrefManager.getInstance(this).logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}