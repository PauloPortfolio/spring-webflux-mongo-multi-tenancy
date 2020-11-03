package de.mgmeiner.examples.mongo.multitenancy.tenant;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ConfigurationProperties("multi-tenancy")
public class TenantProperties {
    private List<Tenant> tenants = new ArrayList<>();

    @Getter
    @Setter
    static class Tenant {
        private String id;
        private MongoProperties mongo;
    }
}
