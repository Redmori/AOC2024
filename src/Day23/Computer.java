package Day23;

import java.util.ArrayList;

public class Computer {

    public String name;

    ArrayList<Computer> connections;

    public Computer(String nm){
        name = nm;
        connections = new ArrayList<>();
    }

    public void connect(Computer otherComputer){
        connections.add(otherComputer);
    }

    public int isPartOfTripple(){
        int sum = 0;
        for (Computer connection : connections){
            sum += isDoubleConnectedTo(connection);

        }
        return sum;
    }

    public int isDoubleConnectedTo(Computer c){
        int sum = 0;
        for (Computer connection : connections){
            if(connection.contains(c)){
                sum ++;
            }
        }
        return sum;
    }

    public boolean contains(Computer c){
        return connections.contains(c);
    }

    public boolean nameStartsWithT(){
        return name.charAt(0) == 'T';
    }

    public String toString(){
        StringBuilder sb = new StringBuilder(name);
        for(Computer connection : connections){
            sb.append(" ").append(connection.name);
        }
        return sb.toString();
    }
}
