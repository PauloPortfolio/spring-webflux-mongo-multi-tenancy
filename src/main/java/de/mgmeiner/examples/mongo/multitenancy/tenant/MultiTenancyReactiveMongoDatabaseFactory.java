package de.mgmeiner.examples.mongo.multitenancy.tenant;

import com.mongodb.reactivestreams.client.MongoClient;
import lombok.Getter;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;

@Getter
public final class MultiTenancyReactiveMongoDatabaseFactory extends SimpleReactiveMongoDatabaseFactory {

    private String tenantId;

    public MultiTenancyReactiveMongoDatabaseFactory(
            String tenantId,MongoClient client,String dnName) {
        super(client,dnName);
        this.tenantId = tenantId;
    }

//    public String getTenantId() {
//        return tenantId;
//    }
}
