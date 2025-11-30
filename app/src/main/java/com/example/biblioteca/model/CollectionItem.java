package com.example.biblioteca.model;

public class CollectionItem {
    private long id;
    private long collectionId;
    private String title;
    private String description;

    public CollectionItem(long id, long collectionId, String title, String description) {
        this.id = id;
        this.collectionId = collectionId;
        this.title = title;
        this.description = description;
    }

    public long getId() { return id; }
    public long getCollectionId() { return collectionId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }

    // Setters (úteis para updates futuros)
    public void setId(long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
}