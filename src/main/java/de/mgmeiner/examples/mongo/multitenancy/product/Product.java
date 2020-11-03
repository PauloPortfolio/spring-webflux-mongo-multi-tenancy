package de.mgmeiner.examples.mongo.multitenancy.product;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("products")
public class Product {
    @Id
    private String id;

    @Indexed
    private String name;
}
