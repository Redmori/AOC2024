package Day9;

public class FileBlock {
    public int size;
    public int value;
    public boolean isNull;

    public FileBlock(int _size, int _value){
        size = _size;
        value = _value;
    }

    public String toString(){
        return size + " : " + (isNull ? "." : value);
    }
}
