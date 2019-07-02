package com.safari.practice.democh1.controllers;


import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import static org.junit.Assert.assertEquals;

public class HelloControllerUnitTest {
    @Test
    public void sayHello() {
        HelloController helloController = new HelloController();
        Model model = new BindingAwareModelMap();

        String result = helloController.sayHello("World", model);

        assertEquals("hello", result);
        assertEquals("World", model.asMap().get("user"));
    }
}
