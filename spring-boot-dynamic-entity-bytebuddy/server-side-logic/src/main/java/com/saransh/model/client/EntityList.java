package com.saransh.model.client;

import com.saransh.model.client.Attribute;
import lombok.Data;

import java.util.ArrayList;

@Data
public class EntityList {
    public String identifier;
    public String name;
    public String namespace;
    public ArrayList<Attribute> attributes;
}
