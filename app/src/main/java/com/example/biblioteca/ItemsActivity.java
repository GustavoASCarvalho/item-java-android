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
        setContentView(R.layout.item_list);

        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.items_recycler_view);
        FloatingActionButton fab = findViewById(R.id.fab_add_item);
        emptyTextView = findViewById(R.id.empty_items_view);

        collectionId = getIntent().getLongExtra("COLLECTION_ID", -1);
        collectionName = getIntent().getStringExtra("COLLECTION_NAME");

        if (collectionId == -1 || collectionName == null) {
            finish();
            return;
        }

        setTitle(collectionName);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ItemsAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(adapter);

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
        loadCollectionItems();
    }

    private void loadCollectionItems() {
        List<CollectionItem> items = dbHelper.getItemsByCollection(collectionId);

        adapter.updateList(items);

        if (items.isEmpty()) {
            emptyTextView.setVisibility(TextView.VISIBLE);
            recyclerView.setVisibility(RecyclerView.GONE);
        } else {
            emptyTextView.setVisibility(TextView.GONE);
            recyclerView.setVisibility(RecyclerView.VISIBLE);
        }
    }
}