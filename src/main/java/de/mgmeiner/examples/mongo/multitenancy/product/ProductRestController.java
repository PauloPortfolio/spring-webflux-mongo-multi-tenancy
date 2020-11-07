package de.mgmeiner.examples.mongo.multitenancy.product;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
public class ProductRestController {

    @Autowired
    private final ProductRepository repo;

    @GetMapping("/products")
    public Flux<Product> products() {
        return repo.findAll()
                   .map(it -> it);
    }

    @PutMapping("/products")
    public Mono<Product> put(@RequestBody Product product) {
        return repo.save(product);
    }
}
