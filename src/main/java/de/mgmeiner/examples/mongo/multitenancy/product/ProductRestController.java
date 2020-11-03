package de.mgmeiner.examples.mongo.multitenancy.product;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
public class ProductRestController {

    private ProductRepository repo;


    @GetMapping("/products")
    public Flux<Product> products() {
        return repo.findAll().map(it -> it);
    }

    @PutMapping("/products")
    public Mono<Product> put(@RequestBody Product product) {
        return repo.save(product);
    }
}
