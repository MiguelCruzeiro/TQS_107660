package com.lab52;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import io.cucumber.core.options.Constants;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("com/lab52")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "com.lab52")
public class CucumberTest {

}
