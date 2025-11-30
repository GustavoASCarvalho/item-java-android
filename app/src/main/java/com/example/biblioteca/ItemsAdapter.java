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

// Adapter para o RecyclerView da ItemsActivity (lista de itens específicos)
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
        // Usa o layout item_list.xml
        View view = LayoutInflater.from(context).inflate(R.layout.item_collection, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        CollectionItem item = itemList.get(position);
        holder.titleTextView.setText(item.getTitle());
        holder.descriptionTextView.setText(item.getDescription());

        // TODO: Adicionar lógica de clique para edição futura do item
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // Método para atualizar os dados no RecyclerView
    public void updateList(List<CollectionItem> newList) {
        itemList.clear();
        itemList.addAll(newList);
        notifyDataSetChanged();
    }

    // ViewHolder interno
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