package org.example.Model;

import java.util.Objects;

public class Product {
    private String name;
    private float price;

    private String categorie;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Float.compare(price, product.price) == 0 && Objects.equals(name, product.name) && Objects.equals(categorie, product.categorie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, categorie);
    }

    public Product() {
    }

    public Product(String name, float price,String categorie) {
        this.name = name;
        this.price = price;
        this.categorie = categorie;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
}
