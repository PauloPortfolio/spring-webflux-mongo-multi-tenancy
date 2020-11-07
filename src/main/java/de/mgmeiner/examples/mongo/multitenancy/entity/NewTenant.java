package de.mgmeiner.examples.mongo.multitenancy.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "newtenant")
public class NewTenant {
    @Id
    private String id;

    @Indexed
    private String newTenantName;

    @Indexed
    private String newTenantPassword;
}
