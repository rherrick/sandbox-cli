package io.flywheel.sandbox.cli;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.StopWatch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Cli {
    private static final String translatedXMLPath = RandomStringUtils.randomAlphabetic(20);
    private static final String comparisonType    = RandomStringUtils.randomAlphabetic(20);


    public static void main(final String[] arguments) {
        final Cli cli = new Cli();
        cli.run();
    }

    private void run() {
        StopWatch    stopWatch = StopWatch.createStarted();
        List<String> format1   = IntStream.range(0, 1000000).mapToObj(Integer::toString).map(this::formatString1).collect(Collectors.toList());
        stopWatch.stop();
        long format1Time = stopWatch.getTime();
        stopWatch.reset();
        stopWatch.start();
        List<String> format2   = IntStream.range(0, 1000000).mapToObj(Integer::toString).map(this::formatString2).collect(Collectors.toList());
        stopWatch.stop();
        long format2Time = stopWatch.getTime();
        System.out.println("       Format 1 with stream: " + format1Time + " ms");
        System.out.println("Format 2 with String.join(): " + format2Time + " ms");
    }

    private String formatString1(String value) {
        return " (" + Stream.of(" LOWER(", translatedXMLPath, ") ", comparisonType, " LOWER(", value, ") ").collect(Collectors.joining(" ")) + ")";
    }

    private String formatString2(String value) {
        return " (" + String.join(" "," LOWER(", translatedXMLPath.trim(), ") ", comparisonType.trim(), " LOWER(", value, ") ") + ")";
    }
}
