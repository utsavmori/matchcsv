package org.example;

import java.io.*;
import java.util.*;

public class MatchingUtil
{
    private HashSet<String> list = new HashSet<>();

    public MatchingUtil read(String pathName)
    {
        try (Scanner s = new Scanner(new File(pathName))) {
            while (s.hasNextLine()) {
                list.add(filterLine(s.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return this;
    }

    public String match(String pathName, boolean returnOnlyNonMatched)
    {
        StringBuilder builder = new StringBuilder();
        try (Scanner s = new Scanner(new File(pathName))) {
            while (s.hasNextLine()) {
                String line = filterLine(s.nextLine());
                if (list.contains(line) && !returnOnlyNonMatched) {
                    list.remove(line);
                    builder.append(line).append(System.lineSeparator());
                } else {
                    if (returnOnlyNonMatched) {
                        list.remove(line);
                        builder.append(line).append(System.lineSeparator());
                    }

                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (returnOnlyNonMatched) {
            list.parallelStream().forEach(builder::append);
        }
        return builder.toString();

    }

    private String filterLine(String line)
    {
        return line.trim();
    }
}
