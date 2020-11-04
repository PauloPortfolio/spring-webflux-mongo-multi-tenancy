package de.mgmeiner.examples.mongo.multitenancy.tenant;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;

@Getter
@Setter
public class Tenant {
    private String id;
    private MongoProperties mongo;
}
