//package de.mgmeiner.examples.mongo.multitenancy.entity;
//
//import com.mongodb.DB;
//import com.mongodb.reactivestreams.client.MongoClient;
//import de.mgmeiner.examples.mongo.multitenancy.tenant.TenantProperties;
//import org.springframework.boot.autoconfigure.mongo.MongoProperties;
//import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//import static de.mgmeiner.examples.mongo.multitenancy.tenant.TenantProperties.*;
//
//@Repository
//public class NewTenantRepository {
//
//    void createDb(){
//        MongoClient mongoClient = new MongoClient();
//        DB database = mongoClient.getDB("myMongoDb");
//        boolean auth = database.authenticate("username", "pwd".toCharArray());
//        database.createCollection("customers", null);
//    }
//
//    private TenantProperties newTenantProperties(String tenant) {
//
//        var props = new MongoProperties();
//        props.setDatabase(tenant);
//        props.setUsername("admin");
//        props.setPassword("admin".toCharArray());
//        props.setAuthenticationDatabase("admin");
//
//        Tenant newTenant = new Tenant();
//        newTenant.setId(tenant);
//        newTenant.setMongo(props);
//
//        List<Tenant> newTenants = tenantProperties.getTenants();
//        newTenants.add(newTenant);
//
//        this.tenantProperties.setTenants(null);
//        this.tenantProperties.setTenants(newTenants);
//
//        return tenantProperties;
//    }
//
//}
