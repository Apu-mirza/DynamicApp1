package com.example.myserver1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private GridView gridView;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);

        // Make the network request
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://example.com/products.json"; // Replace with your URL

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            productList = new ArrayList<>();

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject productObject = response.getJSONObject(i);

                                int id = productObject.getInt("id");
                                String title = productObject.getString("title");
                                String description = productObject.getString("description");
                                float price = (float) productObject.getDouble("price");
                                float discountPercentage = (float) productObject.getDouble("discountPercentage");
                                float rating = (float) productObject.getDouble("rating");
                                int stock = productObject.getInt("stock");
                                String brand = productObject.getString("brand");
                                String category = productObject.getString("category");
                                String thumbnail = productObject.getString("thumbnail");
                                JSONArray imagesArray = productObject.getJSONArray("images");
                                List<String> images = new ArrayList<>();
                                for (int j = 0; j < imagesArray.length(); j++) {
                                    String imageUrl = imagesArray.getString(j);
                                    images.add(imageUrl);
                                }

                                Product product = new Product(id, title, description, price, discountPercentage,
                                        rating, stock, brand, category, thumbnail, images);
                                productList.add(product);
                            }

                            // Set up the GridView adapter
                            ProductAdapter adapter = new ProductAdapter(MainActivity.this, productList);
                            gridView.setAdapter(adapter);

                            // Set item click listener
                            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Product selectedProduct = productList.get(position);

                                    // Pass the selectedProduct to the second activity
                                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                                    intent.putExtra("selectedProduct", selectedProduct);
                                    startActivity(intent);
                                }
                            });
                        } catch (JSONException e) {
                            Log.e(TAG, "JSONException: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Volley error: " + error.getMessage());
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }
}

