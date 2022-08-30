package com.saransh.SpringRest.HelloWorld.controller;

import com.saransh.SpringRest.HelloWorld.bean.HelloWorldBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController //or @Controller
public class HelloWorldController {
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET, path = "/hello-world")
    public HelloWorldBean helloWorld(){
        return new HelloWorldBean("FooBar");
    }

    @GetMapping (path = "/hello-world/{paramPassed}")
    public HelloWorldBean helloWorldPathVariable(@PathVariable String paramPassed){
        return new HelloWorldBean("You passed: " + paramPassed);
    }

    @GetMapping(path = "/")
    public String defaultMapping(){
        return "Greetings from Spring Boot!";
    }

    @GetMapping(path = "/v1/hello-world-internationalized")
    public String helloWorldInternationalizedWithLocale(
            @RequestHeader(name = "Accept-Language", required = false) Locale locale){
        return messageSource.getMessage("good.morning.message", null, "Default message", locale);
    }

    @GetMapping(path = "/v2/hello-world-internationalized")
    public String helloWorldInternationalizedWithLocaleContextHolder(
//            @RequestHeader(name = "Accept-Language", required = false) Locale locale
            ){
        return messageSource.getMessage("good.morning.message", null, "Default message",
                LocaleContextHolder.getLocale());
    }
}
