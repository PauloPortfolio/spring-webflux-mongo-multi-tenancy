package de.mgmeiner.examples.mongo.multitenancy.tenant;


import com.mongodb.MongoClientSettings;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.autoconfigure.mongo.ReactiveMongoClientFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.convert.MongoConverter;

import java.util.List;
import java.util.stream.Collectors;

import static de.mgmeiner.examples.mongo.multitenancy.tenant.TenantProperties.Tenant;

@Configuration
@EnableConfigurationProperties(TenantProperties.class)
public class MultiTenancyConfiguration {

    @Autowired
    private TenantProperties tenantProperties;

    private Environment env;
    private MongoConverter mongoConv;
    private ObjectProvider<MongoClientSettingsBuilderCustomizer> builderCustom;
    private ObjectProvider<MongoClientSettings> setngs;

    private TenantProperties newTenantProperties(String tenant) {

        var props = new MongoProperties();
        props.setDatabase(tenant);
        props.setUsername("admin");
        props.setPassword("admin".toCharArray());
        props.setAuthenticationDatabase("admin");

        Tenant newTenant = new Tenant();
        newTenant.setId(tenant);
        newTenant.setMongo(props);

        List<Tenant> newTenants = tenantProperties.getTenants();
        newTenants.add(newTenant);

        this.tenantProperties.setTenants(null);
        this.tenantProperties.setTenants(newTenants);

        return tenantProperties;
    }


    public void reloadReactiveMongoTemplateWithNewTenant(String newTenant) {

        reactiveMongoTemplate(
                newTenantProperties(newTenant),
                this.env,
                this.mongoConv,
                this.builderCustom,
                this.setngs
                             );
    }


    @Bean
    public MultiTenancyReactiveMongoTemplate reactiveMongoTemplate(
            TenantProperties tenantProperties,
            Environment environment,
            MongoConverter mongoConverter,
            ObjectProvider<MongoClientSettingsBuilderCustomizer> builderCustomizers,
            ObjectProvider<MongoClientSettings> settings) {


        this.env = environment;
        this.mongoConv = mongoConverter;
        this.builderCustom = builderCustomizers;
        this.setngs = settings;

        var mTenancyReactiveMongoDBFactories =
                tenantProperties
                        .getTenants()
                        .stream()
                        .map(tenantItem -> {

                            var mongoProps = tenantItem.getMongo();

                            var factory =
                                    new ReactiveMongoClientFactory(
                                            mongoProps,
                                            environment,
                                            builderCustomizers
                                                    .orderedStream()
                                                    .collect(
                                                            Collectors.toList())
                                    );

                            var newMongoClient =
                                    factory.createMongoClient(
                                            settings.getIfAvailable());

                            System.out.println(
                                    "DBS => " + newMongoClient.listDatabaseNames());

                            return
                                    new MultiTenancyReactiveMongoDatabaseFactory(
                                            tenantItem.getId(),
                                            newMongoClient,
                                            mongoProps.getDatabase()
                                    );
                        })
                        .collect(Collectors.toList());

        return new MultiTenancyReactiveMongoTemplate(
                mTenancyReactiveMongoDBFactories,
                mongoConverter
        );
    }

    @Bean
    public TenantExtractingWebFilter tenantExtractingWebFilter(TenantProperties tenantProperties) {
        return new TenantExtractingWebFilter(
                tenantProperties
                        .getTenants()
                        .stream()
                        //                        .map(TenantProperties.Tenant::getId)
                        .map(item -> {
                            return item.getId();
                        })
                        .collect(Collectors.toList()));
    }
}
