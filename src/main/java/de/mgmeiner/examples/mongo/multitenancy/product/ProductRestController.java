package de.mgmeiner.examples.mongo.multitenancy.product;

import de.mgmeiner.examples.mongo.multitenancy.tenant.MultiTenancyConfiguration;
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

    private final ProductRepository repo;

    @Autowired
    MultiTenancyConfiguration test;

    @GetMapping("/products")
    public Flux<Product> products() {

        return repo.findAll()
                   .map(it -> it);
    }

    @PutMapping("/products")
    public Mono<Product> put(@RequestBody Product product) {
        return repo.save(product);
    }

//    @GetMapping("/productsteste")
//    public Flux<Product> products() {
//        MongoProperties props = new MongoProperties();
//        Tenant tenant = new Tenant();
//
//        props.setDatabase("testando333");
//        props.setUsername("root");
//        props.setPassword("example");
//        props.setAuthenticationDatabase("admin");
//
//        tenant.setId("teste-3");
//        tenant.setMongo(props);
//
//        TenantProperties test = new TenantProperties();
//        MultiTenancyConfiguration xx = new MultiTenancyConfiguration();
//        xx.reactiveMongoTemplate()
//
//        return repo.findAll()
//                   .map(it -> it);
//    }
}
