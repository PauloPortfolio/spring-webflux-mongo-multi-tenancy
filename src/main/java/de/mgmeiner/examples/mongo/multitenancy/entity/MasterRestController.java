//package de.mgmeiner.examples.mongo.multitenancy.entity;
//
//import lombok.AllArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//@AllArgsConstructor
//@RestController
//public class MasterRestController {
//
//    private final NewTenantRepository repo;
//
//    @GetMapping("/newtenant")
//    public Flux<NewTenant> tenants() {
//        return repo.findAll()
//                   .map(it -> it);
//    }
//
//    @PostMapping("/newtenant")
//    public Mono<NewTenantDTO> put(@RequestBody NewTenant newTenant) {
//
//        return repo
//                .save(newTenant)
//                .map(i -> {
//                    var ret = new NewTenantDTO();
//                    ret.setNewTenantName(i.getNewTenantName());
//                    return ret;
//                });
//
//    }
//
//}
