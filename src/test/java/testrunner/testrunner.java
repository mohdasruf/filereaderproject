package testrunner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features="classpath:features/"
        ,glue={"stepdefinition"},
        plugin = {"pretty","html:target/cucumber-html-report"})
public class testrunner {
}