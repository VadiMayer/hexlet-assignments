package exercise.controller;

import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import exercise.model.Product;
import exercise.repository.ProductRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    // BEGIN
    @GetMapping
    public List<Product> getProducts(@RequestParam @Nullable Integer min, @RequestParam @Nullable Integer max) {
        if (min != null && max != null) {
            return productRepository.findByPriceBetweenOrderByPriceAsc(min, max);
        } else if (max != null) {
            return productRepository.findAllByPriceBeforeOrderByPriceAsc(max);
        } else if (min != null) {
            return productRepository.findAllByPriceAfterOrderByPriceAsc(min);
        } else
            return productRepository.findAll();
    }
    // END

    @GetMapping(path = "/{id}")
    public Product show(@PathVariable long id) {

        var product =  productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        return product;
    }
}
