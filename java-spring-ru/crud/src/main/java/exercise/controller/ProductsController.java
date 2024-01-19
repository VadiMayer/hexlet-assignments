package exercise.controller;

import java.util.List;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.exception.BadRequestException;
import exercise.mapper.ProductMapper;
import exercise.model.Category;
import exercise.model.Product;
import exercise.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.ProductRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    // BEGIN
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public List<ProductDTO> getProducts() {
        return productRepository.findAll().stream().map(product -> productMapper.map(product)).toList();
    }

    @GetMapping("/{id}")
    public ProductDTO getProduct(@PathVariable Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found " + id));
        return productMapper.map(product);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO create(@RequestBody @Valid ProductCreateDTO createDTO) {
        Product product = productMapper.map(createDTO);
        Category category = categoryRepository.findById(createDTO.getCategoryId())
                .orElseThrow(() -> new BadRequestException("Category with id " + createDTO.getCategoryId() + " not exists"));
        product.setCategory(category);
        productRepository.save(product);
        return productMapper.map(product);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody @Valid ProductUpdateDTO updateDTO, @PathVariable Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not Found " + id));
        Category category = categoryRepository.findById(updateDTO.getCategoryId().get())
                .orElseThrow(() -> new BadRequestException("Category with id " + updateDTO.getCategoryId().get() + " not exists"));
        product.setCategory(category);
        productMapper.update(updateDTO, product);
        productRepository.save(product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productRepository.deleteById(id);
    }
    // END
}
