package com.saransh.model.client;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ClientSideModel {
    public String identifier;
    public String name;
    public String namespace;
    public ArrayList<Attribute> attributes;
    public ArrayList<EntityList> entityList;
}
