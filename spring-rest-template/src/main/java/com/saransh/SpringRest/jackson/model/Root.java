package com.saransh.SpringRest.jackson.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class Root {
    List<Item> item;
}

