package com.example.biblioteca;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.biblioteca.model.Collection;

import java.util.List;

// Adapter para o RecyclerView da MainActivity (lista de tipos de coleções)
public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder> {

    private final Context context;
    private final List<Collection> collectionList;

    public CollectionAdapter(Context context, List<Collection> collectionList) {
        this.context = context;
        this.collectionList = collectionList;
    }

    @NonNull
    @Override
    public CollectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Usa o layout item_collection.xml
        View view = LayoutInflater.from(context).inflate(R.layout.item_collection, parent, false);
        return new CollectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionViewHolder holder, int position) {
        final Collection collection = collectionList.get(position);
        holder.nameTextView.setText(collection.getName());

        // Define o clique do item para abrir ItemsActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ItemsActivity.class);
            intent.putExtra("COLLECTION_ID", collection.getId());
            intent.putExtra("COLLECTION_NAME", collection.getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return collectionList.size();
    }

    // ViewHolder interno
    public static class CollectionViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;

        public CollectionViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.text_collection_name);
        }
    }
}