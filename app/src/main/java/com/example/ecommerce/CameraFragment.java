package com.example.ecommerce;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CameraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CameraFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CameraFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CameraFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CameraFragment newInstance(String param1, String param2) {
        CameraFragment fragment = new CameraFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_camera, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView_camera);
        RecyclerView.LayoutManager layoutManager;
        ProductsListAdapter adapter;
        ArrayList<Product> productsList = new ArrayList<>();
        DPOperations database = new DPOperations(getActivity().getApplicationContext());
        Cursor cursor = database.getProductsByCategory("2");

        while (!cursor.isAfterLast())
        {
            Product products = new Product((cursor.getInt(0)), cursor.getString(1),
                    cursor.getString(2), cursor.getInt(3), cursor.getString(4),
                    cursor.getString(5), cursor.getInt(6));
            products.setAddedToCart(false);
            productsList.add(products);
            cursor.moveToNext();
        }

        adapter = new ProductsListAdapter(productsList, getActivity().getApplicationContext());

        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL,false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        return rootView;
    }
}