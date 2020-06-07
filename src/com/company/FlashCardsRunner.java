package com.company;

import java.util.*;

public class FlashCardsRunner {

    private static final Scanner scanner = new Scanner(System.in);

    private Map<String, String> cards;
    private List<String> logs;
    private Map<String, Integer> statistics;
    private CardWriter writer;
    private CardReader reader;
    private Random random;
    private String commandLineInputFile;
    private String commandLineOutputFile;

    public FlashCardsRunner(String commandLineInputFile, String commandLineOutputFile) {
        cards = new LinkedHashMap<>();
        logs = new ArrayList<>();
        statistics = new HashMap<>();
        writer = new CardWriter();
        reader = new CardReader();
        this.commandLineInputFile = commandLineInputFile;
        this.commandLineOutputFile = commandLineOutputFile;
    }

    public void run() {
        String action;

        if (commandLineInputFile != null) {
            importCards(commandLineInputFile);
        }

        do {
            System.out.println("Input the action (add, remove, import, export, ask, exit, log, hardest card," +
                    " reset stats, count):");
            action = scanner.nextLine().trim().toLowerCase();

            switch (action) {
                case "add":
                    addCard();
                    break;
                case "remove":
                    removeCard();
                    break;
                case "import":
                    importCards(null);
                    break;
                case "export":
                    exportCards(null);
                    break;
                case "ask":
                    askForCard();
                    break;
                case "exit":
                    System.out.println("Bye bye!");
                    if (commandLineOutputFile != null) {
                        exportCards(commandLineOutputFile);
                    }
                    break;
                case "log":
                    saveLogs();
                    break;
                case "hardest card":
                    displayHardest(statistics);
                    break;
                case "reset stats":
                    statistics = new HashMap<>();
                    System.out.println("Card statistics has been reset.\n");
                    break;
                case "count":
                    count();
                    break;
            }
        } while (!Objects.equals(action, "exit"));
    }

    private void addCard() {
        String term = readTerm();
        logs.add(term);

        if (cards.containsKey(term)) {
            System.out.println("The card \"" + term + "\" already exists.\n");
            return;
        }

        String def = readDef();
        logs.add(def);

        if (cards.containsValue(def)) {
            System.out.println("The definition \"" + def + "\" already exists.\n");
            return;
        }

        cards.put(term, def);
        System.out.println("The pair (\"" + term + "\"" + ":" + "\"" + def + "\") has been added.\n");
    }

    private String readDef() {
        String def;
        System.out.println("The definition of the card:");
        def = scanner.nextLine().trim().toLowerCase();
        return def;
    }

    private String readTerm() {
        String term;

        System.out.println("The card:");
        term = scanner.nextLine().trim().toLowerCase();
        return term;
    }


    private void removeCard() {
        String term = readTerm();
        logs.add(term);

        if (cards.containsKey(term)) {
            cards.remove(term);
            statistics.remove(term);
            System.out.println("The card has been removed.\n");
        } else {
            System.out.println("Can't remove \"" + term + "\": there is no such card.\n");
        }
    }

    private void importCards(String fileName) {
        if (fileName == null) {
            fileName = readFileName();
            logs.add(fileName);
        }

        reader.setFileName(fileName);
        reader.readFile();

        cards.putAll(reader.getCards());
        statistics.putAll(reader.getStatistics());
    }

    private void exportCards(String fileName) {
        if (fileName == null) {
            fileName = readFileName();
            logs.add(fileName);
        }

        writer.setCards(cards);
        writer.setStatistics(statistics);
        writer.setFileName(fileName);
        writer.writeCards();
    }

    private String readFileName() {
        System.out.println("File name:");
        return scanner.nextLine().trim();
    }

    private void askForCard() {
        int quantity = readNumberOfAsks();

        if (quantity == -1) {
            System.out.println("Provide a number.\n");
            return;
        } else if (quantity == 0) {
            return;
        }

        List<Map.Entry<String, String>> entries = new ArrayList<>(cards.entrySet());
        if (entries.size() == 0) {
            System.out.println("There are no flashcards to ask.\n");
            return;
        }

        random = new Random();
        int counter = 0;
        do {
            int index = random.nextInt(entries.size());
            String key = entries.get(index).getKey();
            String value = entries.get(index).getValue();

            System.out.println("Print the definition of \"" + key + "\":");
            String answer = scanner.nextLine().trim();
            logs.add(answer);
            GenericMap<String, String> generic = new GenericMap<>();

            counter++;

            if (answer.equals(value)) {
                System.out.println("Correct answer.");
            } else if (cards.containsValue(answer)) {
                addToStats(key);
                System.out.print("Wrong answer. The correct one is \"" + value + "\"");
                System.out.println(", you've just written the definition of \"" + generic.findKeyForValue(cards, answer) + "\".");
            } else {
                addToStats(key);
                System.out.println("Wrong answer. The correct one is \"" + value + "\".");
            }
        } while (counter != quantity);

        entries.clear();
    }

    private int readNumberOfAsks() {
        System.out.println("How many times to ask?");
        String s = scanner.nextLine().trim();
        logs.add(s);
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void addToStats(String s) {
        if (statistics.containsKey(s)) {
            statistics.put(s, statistics.get(s) + 1);
        } else {
            statistics.put(s, 1);
        }
    }

    private void displayHardest(Map<String, Integer> map) {
        int maxError = findHardest(map)[0];
        int times = findHardest(map)[1];

        if (times == -1) {
            System.out.println("There are no cards with errors.\n");
        } else if (times == 1) {
            GenericMap<String, Integer> generic = new GenericMap<>();

            System.out.print("The hardest card is \"" + generic.findKeyForValue(map, maxError) + "\". ");
            System.out.println("You have " + maxError + " errors answering it.\n");
        } else {
            StringBuilder builder = new StringBuilder();
            builder.append("The hardest cards are ");

            map.entrySet().stream()
                    .filter(entry -> entry.getValue() == maxError)
                    .forEach(entry -> {
                        builder.append("\"");
                        builder.append(entry.getKey());
                        builder.append("\", ");
                    });
            builder.delete(builder.length() - 2, builder.length());
            builder.append(". You have ");
            builder.append(maxError);
            builder.append(" errors answering them.");

            System.out.println(builder.toString());
        }
    }

    private int[] findHardest(Map<String, Integer> map) {
        if (map.size() == 0) {
            return new int[]{-1, -1};
        }

        int maxError = map.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get().getValue();

        long times = map.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == maxError)
                .count();
        return new int[]{maxError, (int) times};
    }

    private void saveLogs() {
        writer.setLogs(logs);

        String fileName = readFileName();
        logs.add(fileName);

        writer.setLogName(fileName);
        writer.saveLogs();
    }

    private void count() {
        if (cards.size() == 1) {
            System.out.println("There is 1 card in memory.\n");
        } else {
            System.out.println("There are " + cards.size() + " cards in memory.\n");
        }
    }
}
