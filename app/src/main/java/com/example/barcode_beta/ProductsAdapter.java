package com.example.barcode_beta;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {


    private Context mCtx;
    private List<Product> productList;

    public ProductsAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.activity_list, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.txt_Code.setText("Barcode: " + String.valueOf(product.getCode()));
        holder.txt_Price.setText("Price: " + String.valueOf(product.getPrice()));
        holder.txt_NumberOf.setText("Quantity: " + String.valueOf(product.getNumberOf()));
        holder.txt_ProductName.setText("Product name: " + String.valueOf(product.getProductName()));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView txt_Code, txt_Price, txt_NumberOf, txt_ProductName;

        public ProductViewHolder(View itemView) {
            super(itemView);

            txt_Code = itemView.findViewById(R.id.txt_Code);
            txt_Price = itemView.findViewById(R.id.txt_Price);
            txt_NumberOf = itemView.findViewById(R.id.txt_Quantity);
            txt_ProductName = itemView.findViewById(R.id.txt_ProductName);
        }
    }
}
