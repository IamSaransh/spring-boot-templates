package me.saransh.runtimeentity.models;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;


import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
public class Core {
    protected UUID identifier;
    protected String name;
    protected String namespace;
}
