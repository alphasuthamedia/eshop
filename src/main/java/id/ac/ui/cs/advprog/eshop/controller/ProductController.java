package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.Service;
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
    private Service<Product> service;

    @GetMapping("/create")
    public String createProductPage(Model model) {
        Product product = new Product();

        model.addAttribute("product", product);
        return "CreateProduct";
    }

    @PostMapping("/create")
    public String createProductPost(@ModelAttribute Product product, Model model) {
        if (service.create(product) != null) {
            return "redirect:list";
        }

        model.addAttribute("product", product);
        model.addAttribute("positive_value_error", "Please input a positive value for quantity");
        return "CreateProduct";
    }

    @GetMapping("/edit")
    public String editProductPage(@RequestParam(value = "id", required = false) String ProductID,Model model) {
        Product product = service.getItemByID(ProductID);

        // Prevent IDOR :) if product ID didnt exist
        if (product == null) {
            return "redirect:https://www.youtube.com/watch?v=dQw4w9WgXcQ";
        }

        model.addAttribute("product", product);

        return "EditProduct";
    }

    @PostMapping("/edit")
    public String editProductPatch(@ModelAttribute Product product) {
        service.editItem(product);

        return "redirect:list";
    }

    // REST API DELETE METHOD
    @DeleteMapping("/delete")
    @ResponseBody
    public String deleteProduct(@ModelAttribute Product product, @RequestBody HashMap deleteRequest) {
        return service.removeByID(deleteRequest.get("ID").toString());
    }

    @GetMapping("/list")
    public String productListPage(Model model) {
        List<Product> allProduct = service.findAll();
        model.addAttribute("products", allProduct);
        return "ProductList";
    }
}
