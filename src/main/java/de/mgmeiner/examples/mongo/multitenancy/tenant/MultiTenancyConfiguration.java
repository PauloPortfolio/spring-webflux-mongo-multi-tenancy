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

@Configuration
@EnableConfigurationProperties(TenantProperties.class)
public class MultiTenancyConfiguration {

    @Autowired
    private TenantProperties tenantProperties;

    private Environment env;
    private MongoConverter mongoConv;
    private ObjectProvider<MongoClientSettingsBuilderCustomizer> builderCustom;
    private ObjectProvider<MongoClientSettings> setngs;

    private TenantProperties newTenantProperties(String newTenant) {

        MongoProperties props = new MongoProperties();
        props.setDatabase(newTenant);
        props.setUsername("root");
        props.setPassword("example".toCharArray());
        props.setAuthenticationDatabase("admin");

        TenantProperties.Tenant newTenantCreated = new TenantProperties.Tenant();
        newTenantCreated.setId(newTenant + "XXX");
        newTenantCreated.setMongo(props);

        List<TenantProperties.Tenant> newListTenants = tenantProperties.getTenants();
        newListTenants.add(newTenantCreated);

        tenantProperties.setTenants(newListTenants);

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

        var multiTenancyReactiveMongoDatabaseFactories =
                tenantProperties
                        .getTenants()
                        .stream()
                        .map(t -> {

                            var mongoProperties = t.getMongo();

                            var factory =
                                    new ReactiveMongoClientFactory(
                                            mongoProperties,
                                            environment,
                                            builderCustomizers
                                                    .orderedStream()
                                                    .collect(
                                                            Collectors.toList())
                                    );

                            var mongoClient =
                                    factory
                                            .createMongoClient(settings.getIfAvailable());

                            System.out.println("MTCONFIG => " + t.getId());

                            return
                                    new MultiTenancyReactiveMongoDatabaseFactory(
                                            t.getId(),
                                            mongoClient,
                                            mongoProperties.getDatabase()
                                    );
                        })
                        .collect(Collectors.toList());

        return new MultiTenancyReactiveMongoTemplate(
                multiTenancyReactiveMongoDatabaseFactories,
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
