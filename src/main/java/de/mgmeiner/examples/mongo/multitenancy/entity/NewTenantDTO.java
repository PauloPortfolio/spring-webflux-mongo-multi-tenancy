package de.mgmeiner.examples.mongo.multitenancy.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
public class NewTenantDTO {

    private String newTenantName;
}
