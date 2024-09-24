package com.example.androidassignment;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private CustomCircularProgressBar circularProgressBar;
    private TextView progressText;
    private int progress = 53; // Set your progress value here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the custom progress bar and text view
        circularProgressBar = findViewById(R.id.circularProgressBar);
        progressText = findViewById(R.id.progressText);

        // Set initial progress
        circularProgressBar.setProgress(progress);
        progressText.setText(progress + "/100");


        // Initialize TextView variables
        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView contentTextView = findViewById(R.id.contentTextView);

        // Instantiate the RequestQueue
        // Step 1: Create a RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://www.jsonkeeper.com/b/6HBE"; // Replace with the actual URL

        // Step 2: Create a StringRequest
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Step 3: Parse the outer JSON response
                            JSONObject mainObject = new JSONObject(response);

                            // Step 4: Access the "choices" array and the "message" object
                            JSONArray choicesArray = mainObject.getJSONArray("choices");
                            JSONObject choiceObject = choicesArray.getJSONObject(0);
                            JSONObject messageObject = choiceObject.getJSONObject("message");

                            // Step 5: Get the content string, which is a JSON string
                            String contentString = messageObject.getString("content");

                            // Step 6: Parse the content string into a JSONObject
                            JSONObject contentObject = new JSONObject(contentString);

                            // Step 7: Extract the "titles" array and "description" from the content
                            JSONArray titlesArray = contentObject.getJSONArray("titles");
                            String title1 = titlesArray.getString(0);
                            String title2 = titlesArray.getString(1);
                            String description = contentObject.getString("description");

                            titleTextView.setText(title1 + " " + title2);
                            contentTextView.setText(description);

                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this, "Error Occur", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error: " + error.getMessage());
                    }
                }
        );

        // Step 9: Add the request to the RequestQueue
        queue.add(stringRequest);
    }
}