package com.teamchallenge.online_store.dto;

import com.teamchallenge.online_store.model.Collection;

public class CollectionResponse {

    private Collection collection;
    private byte[] imageBytes;

    public CollectionResponse(Collection collection, byte[] imageBytes) {
        this.collection = collection;
        this.imageBytes = imageBytes;
    }

    public Collection getCollection() {
        return collection;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }
}
