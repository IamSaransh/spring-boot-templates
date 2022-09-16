package com.saransh.SpringRest.jackson.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.saransh.SpringRest.HelloWorld.bean.HelloWorldBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
//    private String rowid;
    private HelloWorldBean helloWorldBean;
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String secondValue;
}
