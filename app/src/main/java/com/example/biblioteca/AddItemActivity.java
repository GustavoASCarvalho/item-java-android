package com.example.biblioteca;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.biblioteca.model.CollectionItem;

public class AddItemActivity extends AppCompatActivity {

    private long collectionId;
    private String collectionName;
    private DatabaseHelper dbHelper;

    private EditText titleEditText;
    private EditText descriptionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        dbHelper = new DatabaseHelper(this);

        collectionId = getIntent().getLongExtra("COLLECTION_ID", -1);
        collectionName = getIntent().getStringExtra("COLLECTION_NAME");

        if (collectionId == -1 || collectionName == null) {
            Toast.makeText(this, "Erro: Coleção não encontrada.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setTitle(getString(R.string.add_item_title_format, collectionName));

        titleEditText = findViewById(R.id.edit_item_title);
        descriptionEditText = findViewById(R.id.edit_item_description);
        Button saveButton = findViewById(R.id.button_save_item);

        saveButton.setOnClickListener(v -> saveItem());
    }

    private void saveItem() {
        String title = titleEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();

        if (title.isEmpty()) {
            Toast.makeText(this, "O título do item é obrigatório.", Toast.LENGTH_SHORT).show();
            return;
        }

        CollectionItem newItem = new CollectionItem(0, collectionId, title, description);

        long result = dbHelper.insertItem(newItem);

        if (result != -1) {
            Toast.makeText(this, "Item '" + title + "' salvo com sucesso!", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Erro ao salvar o item.", Toast.LENGTH_LONG).show();
        }
    }
}