package se.claremont.taf.javasupport.applicationundertest.applicationstarters;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import se.claremont.taf.core.testcase.TestCase;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ApplicationStartMechanism implements Serializable {
    @JsonProperty
    public String startUrlOrPathToJarFile;
    @JsonProperty
    public String mainClass;
    @JsonProperty
    public List<String> arguments = new ArrayList<>();
    @JsonIgnore
    transient TestCase testCase;
    @JsonIgnore
    ClassLoader classLoader;

    private ApplicationStartMechanism() {
    }

    public ApplicationStartMechanism(TestCase testCase) {
        this.testCase = testCase;
    }

    public static ApplicationStartMechanism readFromJsonFile(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(filePath), ApplicationStartMechanism.class);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return new ApplicationStartMechanism(new TestCase());
    }

    public void run() {
        ApplicationStarter as = new ApplicationStarter(testCase);
        try {
            as.startJar(new URL(startUrlOrPathToJarFile), mainClass, arguments.toArray(new String[0]));
        } catch (MalformedURLException e) {
            System.out.println("Could not start program '" + startUrlOrPathToJarFile + "', with start class '" + mainClass + "' and arguments '" + String.join("', '", arguments) + "'. Error:" + e.toString());
        } catch (SecurityException e) {
            System.out.println("SUT application exited.");
        }
    }

    public String saveToJsonFile(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(filePath), this);
        } catch (IOException e) {
            return e.toString();
        }
        return "ok";
    }

    String toClassFile() {
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }
}
