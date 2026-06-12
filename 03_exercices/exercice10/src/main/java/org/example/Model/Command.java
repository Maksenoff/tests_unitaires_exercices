package org.example.Model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Command {
    private int id_command;
    private Map<Product,Integer> productsQuantity;

    public Command(int id_command) {
        this.productsQuantity = new HashMap<>();
        this.id_command = id_command;
    }

    public Command() {
        this.productsQuantity = new HashMap<>();
    }

    public int getId_command() {
        return id_command;
    }

    public void setId_command(int id_command) {
        this.id_command = id_command;
    }

    public void setProductsQuantity(Map<Product, Integer> productsQuantity) {
        this.productsQuantity = productsQuantity;
    }

    public Map<Product, Integer> getProductsQuantity() {
        return productsQuantity;
    }

    public void addProductQuantity(Product product, int quantity) {
        this.productsQuantity.put(product,quantity);
    }

    public void replaceProductQuantity (Product product , int quantity){
        this.productsQuantity.replace(product,quantity);
    }

    public void removeProduct (Product product){
        this.productsQuantity.remove(product);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return id_command == command.id_command && Objects.equals(productsQuantity, command.productsQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_command, productsQuantity);
    }
}
