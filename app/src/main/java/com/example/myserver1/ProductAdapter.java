package com.example.myserver1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.Locale;

public class ProductAdapter extends ArrayAdapter<Product> {
    private Context context;
    private List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        super(context, 0, productList);
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.thumbnailImageView = convertView.findViewById(R.id.thumbnailImageView);
            viewHolder.titleTextView = convertView.findViewById(R.id.titleTextView);
            viewHolder.priceTextView = convertView.findViewById(R.id.priceTextView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Product product = productList.get(position);
        viewHolder.titleTextView.setText(product.getTitle());
        viewHolder.priceTextView.setText(String.format(Locale.getDefault(), "$%.2f", product.getPrice()));

        // Load thumbnail image using your preferred image loading library (e.g., Glide, Picasso)
        Glide.with(context)
                .load(product.getThumbnail())
                .into(viewHolder.thumbnailImageView);

        return convertView;
    }

    static class ViewHolder {
        ImageView thumbnailImageView;
        TextView titleTextView;
        TextView priceTextView;
    }
}
