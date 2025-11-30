package com.example.biblioteca;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.biblioteca.model.Collection;
import com.example.biblioteca.model.CollectionItem;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CollectionsDB";
    private static final int DATABASE_VERSION = 1;

    // Tabela COLLECTIONS
    public static final String TABLE_COLLECTIONS = "collections";
    public static final String COL_C_ID = "_id";
    public static final String COL_C_NAME = "name";

    // Tabela ITEMS
    public static final String TABLE_ITEMS = "items";
    public static final String COL_I_ID = "_id";
    public static final String COL_I_COLLECTION_ID = "collection_id";
    public static final String COL_I_TITLE = "title";
    public static final String COL_I_DESCRIPTION = "description";

    // Criação da Tabela COLLECTIONS
    private static final String CREATE_TABLE_COLLECTIONS = "CREATE TABLE " + TABLE_COLLECTIONS + "("
            + COL_C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_C_NAME + " TEXT UNIQUE NOT NULL"
            + ")";

    // Criação da Tabela ITEMS
    private static final String CREATE_TABLE_ITEMS = "CREATE TABLE " + TABLE_ITEMS + "("
            + COL_I_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_I_COLLECTION_ID + " INTEGER NOT NULL,"
            + COL_I_TITLE + " TEXT NOT NULL,"
            + COL_I_DESCRIPTION + " TEXT,"
            + "FOREIGN KEY(" + COL_I_COLLECTION_ID + ") REFERENCES " + TABLE_COLLECTIONS + "(" + COL_C_ID + ")"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Cria as tabelas
        db.execSQL(CREATE_TABLE_COLLECTIONS);
        db.execSQL(CREATE_TABLE_ITEMS);

        // Insere coleções padrão para iniciar o app
        insertDefaultCollections(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COLLECTIONS);
        onCreate(db);
    }

    // Método auxiliar para popular a tabela COLLECTIONS
    private void insertDefaultCollections(SQLiteDatabase db) {
        String[] defaultNames = {"Livros", "Filmes", "Jogos"};
        for (String name : defaultNames) {
            ContentValues values = new ContentValues();
            values.put(COL_C_NAME, name);
            db.insert(TABLE_COLLECTIONS, null, values);
        }
    }

    // --- MÉTODOS DE DADOS PARA COLLECTIONS ---

    public List<Collection> getAllCollections() {
        List<Collection> collectionList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_COLLECTIONS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                long id = c.getLong(c.getColumnIndexOrThrow(COL_C_ID));
                String name = c.getString(c.getColumnIndexOrThrow(COL_C_NAME));
                collectionList.add(new Collection(id, name));
            } while (c.moveToNext());
        }
        c.close();
        return collectionList;
    }

    // --- MÉTODOS DE DADOS PARA ITEMS ---

    public long insertItem(CollectionItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_I_COLLECTION_ID, item.getCollectionId());
        values.put(COL_I_TITLE, item.getTitle());
        values.put(COL_I_DESCRIPTION, item.getDescription());

        // Insere a linha, retornando o ID da nova linha
        return db.insert(TABLE_ITEMS, null, values);
    }

    public List<CollectionItem> getItemsByCollection(long collectionId) {
        List<CollectionItem> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_ITEMS + " WHERE " + COL_I_COLLECTION_ID + " = ?";
        Cursor c = db.rawQuery(selectQuery, new String[]{String.valueOf(collectionId)});

        if (c.moveToFirst()) {
            do {
                long id = c.getLong(c.getColumnIndexOrThrow(COL_I_ID));
                String title = c.getString(c.getColumnIndexOrThrow(COL_I_TITLE));
                String description = c.getString(c.getColumnIndexOrThrow(COL_I_DESCRIPTION));

                itemList.add(new CollectionItem(id, collectionId, title, description));
            } while (c.moveToNext());
        }
        c.close();
        return itemList;
    }
}