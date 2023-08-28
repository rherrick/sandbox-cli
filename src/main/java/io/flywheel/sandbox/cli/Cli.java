package io.flywheel.sandbox.cli;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Cli {
    public static void main(final String[] arguments) throws IOException {
        final Cli cli = new Cli();
        cli.run();
    }

    private void run() throws IOException {
        final Map<String, List<String>> tags = new HashMap<>();
        try (final BufferedReader reader = new BufferedReader(new FileReader(new File(Paths.get("/Users/rickherrick/tags.txt").toUri())))) {
            reader.lines().map(StringUtils::split).forEach(array -> {
                final List<String> list = tags.computeIfAbsent(array[0], (key) -> new ArrayList<>());
                list.add(array[1]);
            });
        }
        final Map<Boolean, List<Map.Entry<String, List<String>>>> split = tags.entrySet().stream().collect(Collectors.partitioningBy(entry -> entry.getValue().size() > 1));
        System.out.println("Found " + split.get(false).size() + " tags with only a single name");
        final List<Map.Entry<String, List<String>>> multipleNames = split.get(true);
        System.out.println("Found " + multipleNames.size() + " tags with one or more names:");
        multipleNames.forEach(entry -> {
            System.out.println(" * " + entry.getKey() + ":");
            entry.getValue().stream().sorted().forEach(value -> System.out.println("    - " + value));
        });
    }
}
