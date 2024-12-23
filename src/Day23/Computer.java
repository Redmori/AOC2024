package Day23;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Computer {

    public String name;

    ArrayList<Computer> connections;

    Set<Computer> connected;

    public Computer(String nm){
        name = nm;
        connections = new ArrayList<>();
        connected = new HashSet<>();
    }

    public void connect(Computer otherComputer){
        connections.add(otherComputer);
    }


    public boolean contains(Computer c){
        return connections.contains(c);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder(name + " [");
        for(Computer connection : connections){
            sb.append(" ").append(connection.name);
        }
        sb.append("]");
        return sb.toString();
    }

}
