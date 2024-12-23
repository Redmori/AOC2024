package Day23;

import AOCutil.AOC;

import java.util.ArrayList;
import java.util.Comparator;

public class Main{
    public static void main(String[] args) {
        String[] input = AOC.input("src/Day23/input.txt");

        ArrayList<Computer> computers = generateComputers(input);

        generateConnections(computers, input);

        printComputers(computers);

        ArrayList<String> tripples = getTripples(computers);


        ArrayList<String> tripplesWithT = getTripplesWithT(tripples);


        System.out.println("Day 23 Part 1: " + tripplesWithT.size());


        ArrayList<Computer> largets = findLargestLAN(computers);

        String p2 = sortLAN(largets);

        System.out.println("Day 23 Part 2: " + p2);


    }

    public static String sortLAN(ArrayList<Computer> computers){
        computers.sort(Comparator.comparing(computer -> computer.name));

        StringBuilder sb = new StringBuilder();

        for(Computer computer : computers){
            sb.append(computer.name);
            sb.append(",");
        }

        return sb.toString();
    }


    public static ArrayList<Computer> findLargestLAN(ArrayList<Computer> computers) {
        ArrayList<Computer> largestLAN = new ArrayList<>();
        generateLAN(new ArrayList<>(), computers, largestLAN);
        return largestLAN;
    }


    private static void generateLAN(ArrayList<Computer> currentLAN, ArrayList<Computer> additions, ArrayList<Computer> largestLAN) {
        if (additions.isEmpty()) {
            if (currentLAN.size() > largestLAN.size()) {
                largestLAN.clear();
                largestLAN.addAll(currentLAN);
            }
            return;
        }

        for (int i = 0; i < additions.size(); i++) {
            Computer addition = additions.get(i);

            boolean canAdd = true;
            for (Computer computer : currentLAN) {
                if (!computer.contains(addition)) {
                    canAdd = false;
                    break;
                }
            }

            if (canAdd) {
                currentLAN.add(addition);

                ArrayList<Computer> newAdditions = new ArrayList<>();
                for (int j = i + 1; j < additions.size(); j++) {
                    if (addition.contains(additions.get(j))) {
                        newAdditions.add(additions.get(j));
                    }
                }

                generateLAN(currentLAN, newAdditions, largestLAN);
                currentLAN.remove(currentLAN.size() - 1);
            }
        }
    }

    private static ArrayList<String> getTripplesWithT(ArrayList<String> tripples) {
        ArrayList<String> tripplesWithT = new ArrayList<>();

        for (String tripple : tripples){
            String[] split = tripple.split(",");
            if(startsWithT(split[0]) || startsWithT(split[1]) || startsWithT(split[2])){
                tripplesWithT.add(tripple);
            }
        }

        return tripplesWithT;
    }

    private static boolean startsWithT(String string){
        return string.charAt(0) == 't';
    }

    private static ArrayList<String> getTripples(ArrayList<Computer> computers){
        ArrayList<String> tripples = new ArrayList<>();

        for(Computer computer : computers){
            for(Computer c1 : computer.connections){
                for(Computer c2 : c1.connections){
                    if(c2.contains(computer)){
                        boolean exists = false;
                        for(String tripple : tripples) {
                            if(tripple.contains(computer.name) && tripple.contains(c1.name) && tripple.contains(c2.name)) {
                                exists = true;
                                break;
                            }
                        }
                        if(!exists){
                            tripples.add(computer.name + "," + c1.name + "," + c2.name);
                        }
                    }
                }
            }
        }
        return tripples;
    }

    private static void generateConnections(ArrayList<Computer> computers, String[] input) {
        for(String connection : input){
            String[] names = connection.split("-");
            Computer c1 = findComputerByName(computers, names[0]);
            Computer c2 = findComputerByName(computers, names[1]);
            c1.connect(c2);
            c2.connect(c1);
        }
    }

    private static ArrayList<Computer> generateComputers(String[] input) {

        ArrayList<Computer> computers = new ArrayList<>();

        for(String connection : input){
            String[] names = connection.split("-");
            createComputer(names[0], computers);
            createComputer(names[1], computers);
        }
        return computers;
    }

    private static void createComputer(String name, ArrayList<Computer> computers) {
        if(computers.stream().anyMatch(c -> c.name.equals(name))){
            return;
        }
        Computer newComputer = new Computer(name);
        computers.add(newComputer);
    }

    private static void printComputers(ArrayList<Computer> computers){
        for(Computer computer : computers){
            System.out.println(computer);
        }
    }

    public static Computer findComputerByName(ArrayList<Computer> computers, String computerName) {
        return computers.stream()
                .filter(computer -> computer.name.equals(computerName))
                .findFirst()
                .orElse(null);
    }
}
