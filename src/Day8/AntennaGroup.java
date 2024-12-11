package Day8;

import java.util.ArrayList;

public class AntennaGroup {
    public char type;
    public ArrayList<Antenna> antennas;

    public AntennaGroup(char symbol){
        type = symbol;
        antennas = new ArrayList<>();
    }

    public void add(Antenna newAntenna){
        antennas.add(newAntenna);
    }

    public void addAntinodes(boolean[][] map){
        for(Antenna antenna1 : antennas){
            for(Antenna antenna2 : antennas){
                if(antenna1 != antenna2){
                    int[] newNodeLocation = antenna1.findAntinode(antenna2);
                    if(newNodeLocation[0] >= 0 && newNodeLocation[1] >= 0 && newNodeLocation[0] < map[1].length && newNodeLocation[1] < map[0].length ) {
                        map[newNodeLocation[1]][newNodeLocation[0]] = true;
                    }
                }
            }
        }
    }

    public void addAntinodesP2(boolean[][] map){
        for(Antenna antenna1 : antennas){
            for(Antenna antenna2 : antennas){
                if(antenna1 != antenna2){
                    ArrayList<int[]> newNodesLocations = antenna1.findAntinodes(antenna2, map[1].length, map[0].length);
                    for(int[] newNodeLocation : newNodesLocations) {
                        if (newNodeLocation[0] >= 0 && newNodeLocation[1] >= 0 && newNodeLocation[0] < map[1].length && newNodeLocation[1] < map[0].length) {
                            map[newNodeLocation[1]][newNodeLocation[0]] = true;
                        }
                    }
                }
            }
        }
    }

//    public ArrayList<Antinode> determineAntinodes(){
//        ArrayList<Antinode> nodes = new ArrayList<>();
//
//        for(Antenna antenna1 : antennas){
//            for(Antenna antenna2 : antennas){
//                if(antenna1 != antenna2){
//
//                }
//            }
//        }
//
//
//        return nodes;
//    }

    public String toString(){
        String string = type + " -";
        for(Antenna antenna : antennas){
            string += " (" + antenna.x + "," + antenna.y + ")";
        }
        return string;
    }

}
