package Day6;

public class Map
{
    Main.tile[][] tiles;
    int sizeX;
    int sizeY;

    public Map(Main.tile[][] input){
        tiles = input;
        sizeX = input[0].length;
        sizeY = input.length;
    }

    public Main.tile getTile(Position position){
        return tiles[position.y][position.x];
    }

    public void setTile(Position position, Main.tile tile){
        tiles[position.y][position.x] = tile;
    }

    public static Map cloneWithObject(Map map, Position objectPosition){
        Map clonedMap = clone(map);
        clonedMap.setTile(objectPosition, Main.tile.OBJECT);
        return clonedMap;
    }

    public static Map clone(Map map){
        Main.tile[][] newTiles = new Main.tile[map.sizeY][map.sizeX];
        for(int i = 0; i < map.sizeY; i++){
            for(int j = 0; j < map.sizeX; j++){
                newTiles[i][j] = map.tiles[i][j];
            }
        }
        return new Map(newTiles);
    }

    public void print(){
        for(Main.tile[] row : tiles){
            for(Main.tile cell : row){
                System.out.print(Main.convertEnum(cell));
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printWithoutPath(){
        for(Main.tile[] row : tiles){
            for(Main.tile cell : row){
                System.out.print(Main.convertEnumWithoutPath(cell));
            }
            System.out.println();
        }
        System.out.println();
    }
}
