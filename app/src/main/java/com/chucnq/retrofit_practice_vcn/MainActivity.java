package com.chucnq.retrofit_practice_vcn;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chucnq.retrofit_practice_vcn.adapter.ProductAdapter;
import com.chucnq.retrofit_practice_vcn.data.model.model.database.DatabaseHandler;
import com.chucnq.retrofit_practice_vcn.data.model.model.model.ListProductResponse;
import com.chucnq.retrofit_practice_vcn.data.model.model.model.Product;
import com.chucnq.retrofit_practice_vcn.data.model.model.remote.APIUtils;
import com.chucnq.retrofit_practice_vcn.data.model.model.remote.ProductService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // UI
    RecyclerView rcProducts;
    ProgressBar pbLoading;
    Button btnAddProduct;
    EditText txtName,txtPrice;

    //Service
    ProductService mService;

    //adapter
    ArrayList<Product> mProducts;
    ProductAdapter adapter;

    //database
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        pbLoading.setVisibility(View.VISIBLE);

        loadProducts();

        DisplayListProduct();

        btnAddProduct.setOnClickListener(this);

    }

    private void DisplayListProduct() {
        mProducts = db.getAllProduct();
        /* Quan trọng nhất thằng này */
        adapter.updateProducts(mProducts);
        rcProducts.setAdapter(adapter);
    }

    private void init(){
        pbLoading = findViewById(R.id.pbLoading);
        rcProducts = findViewById(R.id.rc_list_product);
        mService = APIUtils.getProService();
        mProducts = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcProducts.setLayoutManager(linearLayoutManager);
        rcProducts.setHasFixedSize(true);
        db = new DatabaseHandler(this);
        adapter = new ProductAdapter(this,mProducts);
        btnAddProduct = findViewById(R.id.btn_add_product);

    }

    private void loadProducts() {
        mService.getProducts().enqueue(new Callback<ListProductResponse>() {
            @Override
            public void onResponse(Call<ListProductResponse> call, Response<ListProductResponse> response) {

                if (response.isSuccessful()){
//                    adapter.updateProducts(response.body().getData());
//                    Toast.makeText(MainActivity.this,response.body().getData().size()+"",Toast.LENGTH_SHORT)
//                            .show();


                    ArrayList<Product> tempList = new ArrayList<>();

                    tempList = response.body().getData();

                    for (int i = 0; i < tempList.size(); i++){

                        db.insertProduct(tempList.get(i));

                    }

                    DisplayListProduct();

                    pbLoading.setVisibility(View.GONE);
                }else {
                    int statusCode = response.code();
                }

            }

            @Override
            public void onFailure(Call<ListProductResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Failed!",Toast.LENGTH_SHORT)
                        .show();
                pbLoading.setVisibility(View.GONE);

            }
        });
    }


    @Override
    public void onClick(View view) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        View dialogView = this.getLayoutInflater().inflate(R.layout.dialog,null);
        dialog.setView(dialogView);

        txtName = dialogView.findViewById(R.id.txt_name);
        txtPrice = dialogView.findViewById(R.id.txt_price);

        dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (!TextUtils.isEmpty(txtName.getText().toString())
                        && !TextUtils.isEmpty(txtPrice.getText().toString())){

                    db.insertProduct(new Product(i,txtName.getText().toString(),
                            Integer.parseInt(txtPrice.getText().toString())));
                    dialogInterface.dismiss();

                    DisplayListProduct();

                    Toast.makeText(MainActivity.this,"Add successfully!",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(MainActivity.this,"Must fill all requirements!",Toast.LENGTH_LONG).show();
                }

            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.show();

    }
}
