package com.youtube.sorcjc.incidencias.io.response;

import com.youtube.sorcjc.incidencias.model.Category;

import java.util.ArrayList;

public class CategoriesResponse {

    private ArrayList<Category> categories;

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }
}
