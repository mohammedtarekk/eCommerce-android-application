package com.example.ecommerce;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    DPOperations database;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ProductsListAdapter adapter;
    ArrayList<Product> searchList;
    Cursor cursor;
    Product searchProducts;
    EditText searchText;
    ImageButton searchTextButton, searchVoiceButton, searchQrButton;
    boolean isQRSearch = false;
    int voiceCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setTitle("Search");

        searchText = findViewById(R.id.searchText);
        searchTextButton = findViewById(R.id.searchByText_bt);
        searchVoiceButton = findViewById(R.id.searchByVoice_bt);
        searchQrButton = findViewById(R.id.searchByQr_bt);
        recyclerView = findViewById(R.id.recyclerview_search);
        database = new DPOperations(this);

        searchTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = searchText.getText().toString();
                if(!productName.equals("")){
                    boolean searchSuccess = search(productName, "");
                    if(!searchSuccess)
                        Toast.makeText(getApplicationContext(),"No matches", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getApplicationContext(),"Enter product name", Toast.LENGTH_LONG).show();
            }
        });

        searchVoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                startActivityForResult(intent, voiceCode);
            }
        });

        searchQrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isQRSearch = true;
                ActivityCompat.requestPermissions(SearchActivity.this, new String[]{Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);
                IntentIntegrator intentIntegrator = new IntentIntegrator(SearchActivity.this);
                intentIntegrator.initiateScan();
            }
        });
    }

    // if product name is null , it will search by QR code and vice versa
    private boolean search(String productName, String QRCode){
        searchList = new ArrayList<>();
        cursor = database.productSearch(productName, QRCode);
        boolean isSuccess = false;
        if(cursor != null)
        {
            while (!cursor.isAfterLast())
            {
                searchProducts = new Product((cursor.getInt(0)), cursor.getString(1),
                    cursor.getString(2), cursor.getInt(3), cursor.getString(4),
                    cursor.getString(5), cursor.getInt(6));

                searchList.add(searchProducts);
                cursor.moveToNext();
            }
            isSuccess = true;
        }

        adapter = new ProductsListAdapter(searchList, getApplicationContext());
        layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        return isSuccess;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Voice response
        if(requestCode == voiceCode && resultCode == RESULT_OK)
        {
            ArrayList<String> voiceText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            searchText.setText(voiceText.get(0));

            // Perform search
            boolean searchSuccess = search(searchText.getText().toString(), "");
            if(!searchSuccess)
                Toast.makeText(getApplicationContext(),"No matches", Toast.LENGTH_LONG).show();
        }

        // Qr response
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(intentResult != null && intentResult.getContents() != null){
            // Perform search
            boolean searchSuccess = search("", intentResult.getContents());
            if(!searchSuccess)
                Toast.makeText(getApplicationContext(),"No matches", Toast.LENGTH_LONG).show();
        }
        else
            super.onActivityResult(requestCode, resultCode, data);
    }
}