package com.saransh.SpringRest.jackson.controller;

import com.saransh.SpringRest.HelloWorld.bean.HelloWorldBean;
import com.saransh.SpringRest.jackson.model.Item;
import com.saransh.SpringRest.jackson.model.Root;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController

public class Controller {
    @GetMapping("/testJackson")
    public Root Model() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item(new HelloWorldBean(), "hsdhjshd"));
        return Root.builder()
                .item(itemList).build();

    }

}
