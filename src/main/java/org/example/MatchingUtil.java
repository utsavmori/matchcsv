package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MatchingUtil {
    private HashSet<String> list = new HashSet<>();



    public MatchingUtil read(String pathName) {
        try (Scanner s = new Scanner(new File(pathName))) {
            while (s.hasNextLine()) {
                list.add(filterLine(s.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return this;
    }

    public String match(String pathName) {
        StringBuilder builder= new StringBuilder();
        try (Scanner s = new Scanner(new File(pathName))) {
            while (s.hasNextLine()) {
                String line=filterLine(s.nextLine());
                if(list.contains(line)){
                    builder.append(line).append(System.lineSeparator());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return builder.toString();

    }

    private String filterLine(String line) {
        return Arrays.stream(line.trim().split(",")).map(String::trim).collect(Collectors.joining(","));
    }
}