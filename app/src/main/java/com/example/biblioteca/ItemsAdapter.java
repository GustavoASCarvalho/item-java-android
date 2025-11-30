package com.example.biblioteca;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.biblioteca.model.CollectionItem;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private final List<CollectionItem> itemList;
    private final Context context;

    public ItemsAdapter(Context context, List<CollectionItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        CollectionItem item = itemList.get(position);

        String title = item.getTitle();
        if (holder.titleTextView != null) {
            holder.titleTextView.setText(title != null ? title : "");
        }

        String description = item.getDescription();
        if (holder.descriptionTextView != null) {
            holder.descriptionTextView.setText(description != null ? description : "");
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void updateList(List<CollectionItem> newList) {
        itemList.clear();
        itemList.addAll(newList);
        notifyDataSetChanged();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.text_item_title);
            descriptionTextView = itemView.findViewById(R.id.text_item_description);
        }
    }
}