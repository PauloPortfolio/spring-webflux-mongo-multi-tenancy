package de.mgmeiner.examples.mongo.multitenancy.product;

import de.mgmeiner.examples.mongo.multitenancy.entity.TenantDTO;
import de.mgmeiner.examples.mongo.multitenancy.tenant.MultiTenancyConfiguration;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
public class ProductRestController {

    private final ProductRepository repo;

    @Autowired
    MultiTenancyConfiguration mTenancyConfig;

    @GetMapping("/products")
    public Flux<Product> products() {

        return repo.findAll()
                   .map(it -> it);
    }

    @PutMapping("/products")
    public Mono<Product> put(@RequestBody Product product) {
        return repo.save(product);
    }


    @PostMapping("/products")
    public Mono<TenantDTO> products(@RequestBody TenantDTO tenantDTO) {

        mTenancyConfig.reloadReactiveMongoTemplateWithNewTenant(tenantDTO.getNewTenant());

        return Mono.just(tenantDTO);
    }
}
