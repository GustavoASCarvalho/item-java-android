package com.example.biblioteca;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.biblioteca.model.Collection;

import java.util.List;

// Activity 1: Exibe a lista de tipos de coleções (e.g., Livros, Filmes).
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Define o título da Activity
        setTitle(R.string.main_activity_title);

        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.collections_recycler_view);

        setupRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Garante que a lista seja recarregada em caso de mudanças (embora Collection seja estático por padrão)
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        // 1. Obtém a lista de coleções do SQLite
        List<Collection> collections = dbHelper.getAllCollections();

        // 2. Configura o Adapter
        CollectionAdapter adapter = new CollectionAdapter(this, collections);

        // 3. Configura o RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}