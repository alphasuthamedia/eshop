package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/create")
    public String createProductPage(Model model) {
        Product product = new Product();

        model.addAttribute("product", product);
        return "CreateProduct";
    }

    @PostMapping("/create")
    public String createProductPost(@ModelAttribute Product product, Model model) {
        service.create(product);
        return "redirect:list";
    }

    @GetMapping("/edit")
    public String editProductPage(@RequestParam(value = "id", required = false) String ProductID,Model model) {
        Product product = service.getProductByID(ProductID);

        // Prevent IDOR :)
        if (product == null) {
            return "redirect:https://www.youtube.com/watch?v=dQw4w9WgXcQ";
        }

        model.addAttribute("product", product);

        return "EditProduct";
    }

    @PostMapping("/edit")
    public String editProductPatch(@ModelAttribute Product product) {
        service.editProduct(product);

        return "redirect:list";
    }

    // REST API DELETE METHOD
    @DeleteMapping("/delete")
    @ResponseBody
    public void deleteProduct(@ModelAttribute Product product, @RequestBody HashMap deleteRequest) {
        service.removeByID(deleteRequest.get("ID").toString());
    }

    @GetMapping("/list")
    public String productListPage(Model model) {
        List<Product> allProduct = service.findAll();
        model.addAttribute("products", allProduct);
        return "ProductList";
    }
}
