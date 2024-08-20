package com.cloth.Controller;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloth.Repository.ProductRepository;
import com.cloth.dto.ProductDTO;
import com.cloth.dto.SearchCriteria;
import com.cloth.handler.ResourceNotFoundException;
import com.cloth.model.Product;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductRepository  repo;

    @GetMapping //查詢所有
    public List<Product> getAllProducts() {
        return repo.findAll();
        
    }
        
        
        @PostMapping("/search")
        public List<ProductDTO> searchProducts(@RequestBody SearchCriteria criteria) {
            List<Product> products = repo.findBySizeAndStyleAndColor(criteria.getSize(), criteria.getStyle(), criteria.getColor());
            return products.stream()
                    .map(product -> {
                        ProductDTO dto = new ProductDTO();
                        dto.setId(product.getId());
                        dto.setName(product.getName());
                        dto.setDescription(product.getDescription());
                        dto.setImg(product.getImg());
                        return dto;
                    })
                    .collect(Collectors.toList());
        }   
        
        

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Integer id) {
        Product product = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(product, productDTO);
        if (product.getActivity() != null) {
            productDTO.setActivityId(product.getActivity().getId());
        }

        return productDTO;
    }

    @PostMapping //新增
    public Product createProduct(@RequestBody Product product) {
        return repo.save(product);
    }

  


    @PutMapping("/{id}") //修改
    public Product updateProduct(@PathVariable Integer id, @RequestBody Product productDetails) {
        Product product = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        // 使用 BeanUtils 複製所有屬性
        BeanUtils.copyProperties(productDetails, product, "id");

        return repo.save(product);
    }
    

    @DeleteMapping("/{id}") //刪除
    public void deleteProduct(@PathVariable Integer id) {
        Product product = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        repo.delete(product);
    }
}