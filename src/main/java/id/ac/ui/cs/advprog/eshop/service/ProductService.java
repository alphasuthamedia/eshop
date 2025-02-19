package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.List;

public interface ProductService {
    public Product create(Product product);
    public Product getProductByID(String ID);
    public void editProduct(Product product);
    public String removeByID(String ID);
    public List<Product> findAll();
}
