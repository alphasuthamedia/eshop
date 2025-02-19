package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// ADDED MANUAL
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        product.setProductID(UUID.randomUUID().toString());
        return productRepository.create(product);
    }

    @Override
    public List<Product> findAll() {
        Iterator<Product> productIterator = productRepository.findAll();
        List<Product> allProduct = new ArrayList<>();
        productIterator.forEachRemaining(allProduct::add);
        return allProduct;
    }

    @Override
    public Product getProductByID(String ID) {
        Product product = null;
        for (Product i : findAll()) {
            if (i.getProductID().equals(ID)) {
                product = i;
                break;
            }
        }
        return product;
    }

    @Override
    public void editProduct(Product product) {
        for (Product i : findAll()) {
            if (i.getProductID().equals(product.getProductID())) {
                i.setProductName(product.getProductName());
                i.setProductQuantity(product.getProductQuantity());
                break;
            }
        }
    }

    @Override
    public String removeByID(String ID) {
        for (Product product : findAll()) {
            if (product.getProductID().equals(ID)) {
                productRepository.delete(product);
                return product.getProductID();
            }
        }
        return null;
    }
}
