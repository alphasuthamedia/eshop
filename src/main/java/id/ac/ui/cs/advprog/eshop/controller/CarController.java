package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/car")
public class CarController {
    @Autowired
    private Service<Car> carService;

    @GetMapping("/create")
    public String createCarPage(Model model) {
        model.addAttribute("car", new Car());
        return "createCar";
    }

    @PostMapping("/create")
    public String createCarPost(@ModelAttribute Car car) {
        carService.create(car);
        return "redirect:/car/list";
    }

    @GetMapping("/list")
    public String carListPage(Model model) {
        model.addAttribute("cars", carService.findAll());
        return "carList";
    }

    @GetMapping("/edit/{carId}")
    public String editCarPage(@PathVariable String carId, Model model) {
        model.addAttribute("car", carService.getItemByID(carId));
        return "editCar";
    }

    @PostMapping("/edit")
    public String editCarPost(@ModelAttribute Car car) {
        carService.editItem(car);
        return "redirect:/car/list";
    }

    @PostMapping("/delete")
    public String deleteCarPost(@RequestParam("carId") String carId) {
        carService.removeByID(carId);
        return "redirect:/car/list";
    }
}