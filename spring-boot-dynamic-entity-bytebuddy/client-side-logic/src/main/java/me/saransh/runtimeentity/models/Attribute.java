package me.saransh.runtimeentity.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class Attribute {
    private UUID identifier;
    private String name;
    private String type;
}
