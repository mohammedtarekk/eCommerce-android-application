package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    RecyclerView.LayoutManager layoutManager;
    public static DPOperations database;
    public static ArrayList<Product> cartList = new ArrayList<>();
    CartListAdapter adapter;
    public static TextView costText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        getSupportActionBar().setTitle("Cart");

        RecyclerView recycler = (RecyclerView)findViewById(R.id.recyclerView_cartProducts);
        database = new DPOperations(getApplicationContext());

        Cursor cursor = database.getCartProducts(MainActivity.UserID);

        while (!cursor.isAfterLast()){
            Product products = new Product((cursor.getInt(1)), cursor.getString(2),
                    cursor.getString(3), cursor.getInt(5), cursor.getString(4), "", -1);
            cartList.add(products);
            cursor.moveToNext();
        }

        adapter = new CartListAdapter(cartList, getApplicationContext());

        layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recycler);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(layoutManager);

        // set initial total cost
        costText = (TextView) findViewById(R.id.totalCost_txt);
        Integer totalCost = 0;
        for (int i = 0; i < cartList.size(); i++)
        {
            int price = Integer.parseInt(cartList.get(i).getPrice());
            int quantity = cartList.get(i).getQuantity();
            totalCost += price * quantity;
        }
        costText.setText(totalCost.toString());

        // submit order
        Button submitOrderButton = (Button)findViewById(R.id.submitOrder_bt);
        submitOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Your order submitted successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CartActivity.this, HomePageActivity.class);
                startActivity(intent);
            }
        });
    }

    // remove item while swiping it
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Product product = cartList.remove(viewHolder.getAdapterPosition());
            database.deleteProductFromCart(product.getName(), MainActivity.UserID);
            Toast.makeText(getApplicationContext(),product.getName()+" removed",Toast.LENGTH_LONG).show();

            // update total cost
            Integer currentCost = Integer.parseInt(costText.getText().toString());
            Integer newCost = currentCost - (product.getQuantity() * Integer.parseInt(product.getPrice()));
            costText.setText(newCost.toString());

            // update adapter
            adapter.notifyDataSetChanged();
        }
    };
}