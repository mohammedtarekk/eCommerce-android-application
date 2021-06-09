package com.example.ecommerce;

import android.annotation.SuppressLint;
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

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.CartViewHolder>{
    Integer total;
    ArrayList<Product> cart;
    Context context;

    public CartListAdapter(ArrayList<Product> cart, Context context) {
        this.cart = cart;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item,parent,false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.onBind(cart, position, context);
    }

    @Override
    public int getItemCount() {
        return cart.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productDes, productPrice, productQuantity;
        ImageButton addButton, subButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.productName_txt4);
            productDes = itemView.findViewById(R.id.productDes_txt4);
            productPrice = itemView.findViewById(R.id.productPrice_txt4);
            productQuantity = itemView.findViewById(R.id.quantity_txt);
            addButton = itemView.findViewById(R.id.add_bt);
            subButton = itemView.findViewById(R.id.sub_bt);
        }

        @SuppressLint("SetTextI18n")
        public void onBind(final ArrayList<Product> cartList, final int position, final Context context){
            productName.setText(cartList.get(position).getName());
            productDes.setText(cartList.get(position).getDescription());
            productPrice.setText(cartList.get(position).getPrice() + " $");
            productQuantity.setText(""+cartList.get(position).getQuantity()+"");

            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int quantity = cartList.get(position).getQuantity();
                    cartList.get(position).setQuantity(++quantity);
                    productQuantity.setText(quantity);
                }
            });

            subButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int quantity = cartList.get(position).getQuantity();
                    if(--quantity >= 1){
                        cartList.get(position).setQuantity(quantity);
                        productQuantity.setText(quantity);
                    }
                }
            });
        }

    }
}
