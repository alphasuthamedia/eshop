package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;

import java.util.List;

public interface Service<T> {
    public T create(T item);
    public List<T> findAll();
    public T getItemByID(String ID);
    public void editItem(T product);
    public String removeByID(String ID);

}
