package com.example.ecommerce;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.ProductViewHolder> {

    DPOperations database;
    ArrayList<Product> products;
    Context context;

    public ProductsListAdapter(ArrayList<Product> list1, Context context){
        this.products = list1;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.onBind(products, position, context);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productDes, productPrice;
        ImageButton addToCartButton;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.productName_txt);
            productDes = itemView.findViewById(R.id.productDes_txt);
            productPrice = itemView.findViewById(R.id.productPrice_txt);
            addToCartButton = itemView.findViewById(R.id.addToCart_bt);
        }

        public void onBind(final ArrayList<Product> prodName, final int position, final Context context){
            productName.setText(prodName.get(position).getName());
            productDes.setText(prodName.get(position).getDescription());
            productPrice.setText(prodName.get(position).getPrice()+" $");

            database = new DPOperations(context);

            // add product to cart
            addToCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Product product = new Product(prodName.get(position).getId(), prodName.get(position).getName(),
                            prodName.get(position).getPrice(), prodName.get(position).getQuantity(),
                            prodName.get(position).getDescription(), prodName.get(position).getQrCode(),
                            prodName.get(position).getCategoryID());

                    database.addProductToCart(product, MainActivity.UserID);
                    prodName.get(position).setAddedToCart(true);
                    Toast.makeText(context,prodName.get(position).getName()+"Added to cart", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
