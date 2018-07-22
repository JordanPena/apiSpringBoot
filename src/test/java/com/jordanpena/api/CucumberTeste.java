package com.jordanpena.api;

import org.junit.runner.RunWith;
import cucumber.api.*;
import cucumber.api.junit.*;


@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"},
				 features = "src/test/java/resources",
				 monochrome = true,
				 glue = "resources/")
public class CucumberTeste {

}
