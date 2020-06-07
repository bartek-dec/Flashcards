package com.company;

import java.util.Arrays;
import java.util.List;

public class Main {

    private static final String IMPORT = "-import";
    private static final String EXPORT = "-export";

    public static void main(String[] args) {
        String importFile = null;
        String exportFile = null;

        List<String> commands = Arrays.asList(args);

        if (commands.contains(IMPORT)) {
            importFile = commands.get(commands.indexOf(IMPORT) + 1);
        }

        if (commands.contains(EXPORT)) {
            exportFile = commands.get(commands.indexOf(EXPORT) + 1);
        }

        FlashCardsRunner runner = new FlashCardsRunner(importFile, exportFile);
        runner.run();

    }
}
