package com.example.biblioteca;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.biblioteca.model.CollectionItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

// Activity 2: Exibe a lista de itens dentro de uma coleção. Utiliza RecyclerView.
public class ItemsActivity extends AppCompatActivity {

    private long collectionId;
    private String collectionName;
    private RecyclerView recyclerView;
    private ItemsAdapter adapter;
    private DatabaseHelper dbHelper;
    private TextView emptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.items_recycler_view);
        FloatingActionButton fab = findViewById(R.id.fab_add_item);
        emptyTextView = findViewById(R.id.empty_items_view);

        // 1. Obtém dados da Intent
        collectionId = getIntent().getLongExtra("COLLECTION_ID", -1);
        collectionName = getIntent().getStringExtra("COLLECTION_NAME");

        if (collectionId == -1 || collectionName == null) {
            finish(); // Fecha se não tiver dados válidos
            return;
        }

        // Define o título da Activity para o nome da coleção
        setTitle(collectionName);

        // 2. Configura o RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ItemsAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // 3. Configura o FAB para abrir AddItemActivity
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(ItemsActivity.this, AddItemActivity.class);
            intent.putExtra("COLLECTION_ID", collectionId);
            intent.putExtra("COLLECTION_NAME", collectionName);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 4. Carrega os dados da coleção sempre que a Activity for retomada
        loadCollectionItems();
    }

    private void loadCollectionItems() {
        List<CollectionItem> items = dbHelper.getItemsByCollection(collectionId);

        // Atualiza o Adapter com a nova lista
        adapter.updateList(items);

        // Exibe mensagem se a lista estiver vazia
        if (items.isEmpty()) {
            emptyTextView.setVisibility(TextView.VISIBLE);
            recyclerView.setVisibility(RecyclerView.GONE);
        } else {
            emptyTextView.setVisibility(TextView.GONE);
            recyclerView.setVisibility(RecyclerView.VISIBLE);
        }
    }
}