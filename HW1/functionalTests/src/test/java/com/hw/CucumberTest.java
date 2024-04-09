package com.hw;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import io.cucumber.core.options.Constants;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("com/hw")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "com.hw")
public class CucumberTest {

}
