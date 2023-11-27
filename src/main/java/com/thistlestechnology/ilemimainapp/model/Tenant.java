package com.thistlestechnology.ilemimainapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document(collection = "tenants")
@Data

public class Tenant {
}
