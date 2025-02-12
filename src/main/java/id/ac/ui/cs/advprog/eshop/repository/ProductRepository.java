package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// ADDED MANUALLY
import java.util.UUID;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        if (product.getProductQuantity() > 0) {
            productData.add(product);
            return product;
        }
        return null;
    }

    public void delete(Product product) {
        productData.remove(product);
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }
}
