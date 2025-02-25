package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.CarServiceImpl;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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
        if (service.create(product) != null) {
            return "redirect:list";
        }

        model.addAttribute("product", product);
        model.addAttribute("positive_value_error", "Please input a positive value for quantity");
        return "CreateProduct";
    }

    @GetMapping("/edit")
    public String editProductPage(@RequestParam(value = "id", required = false) String ProductID,Model model) {
        Product product = service.getProductByID(ProductID);

        // Prevent IDOR :) if product ID didnt exist
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

@Controller
@RequestMapping("/car")
class CarController extends ProductController {
    @Autowired
    private CarServiceImpl carService;

    @GetMapping("/createCar")
    public String createCarPage(Model model) {
        Car car = new Car();
        model.addAttribute("car", car);
        return "createCar";
    }

    @PostMapping("/createCar")
    public String createCarPost(@ModelAttribute Car car, Model model) {
        carService.create(car);
        return "redirect:listCar";
    }

    @GetMapping("/listCar")
    public String carListPage(Model model) {
        List<Car> allCars = carService.findAll();
        model.addAttribute("cars", allCars);
        return "carList";
    }

    @GetMapping("/editCare/{carID}")
    public String editCarPage(@PathVariable("carID") String carID, Model model) {
        Car car = carService.findByID(carID);
        model.addAttribute("car", car);
        return "editCar";
    }

    @PostMapping("/editCar")
    public String editCarPost(@ModelAttribute Car car, Model model) {
        System.out.println(car.getCarID());
        carService.update(car.getCarID(), car);

        return "redirect:listCar";
    }

    @PostMapping("/deleteCar")
    public String deleteCar(@RequestParam("carID") String carID) {
        carService.deleteCarByID(carID);
        return "redirect:listCar";
    }
}