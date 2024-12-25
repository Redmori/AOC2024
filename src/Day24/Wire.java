package Day24;

import java.util.ArrayList;

public class Wire {

    String name;
    boolean normalValue;

    boolean isSwapped;
    boolean swappedValue;

    Wire normalWire1;
    Wire normalWire2;

    Operation normalOperation;

    Wire wire1;
    Wire wire2;

    Operation operation;

    ArrayList<Wire> influence;


    public void assertInfluence(){
        assertInfluence(this);
    }

    public void assertInfluence(Wire zWire){
        influence.add(zWire);
        wire1.assertInfluence(wire1);
        wire2. assertInfluence(wire2);
    }

    public ArrayList<Wire> swap(Wire otherWire){
        isSwapped = true;

        wire1 = otherWire.normalWire1;
        wire2 = otherWire.normalWire2;
        operation = otherWire.normalOperation;

        otherWire.wire1 = normalWire1;
        otherWire.wire2 = normalWire2;
        otherWire.operation = normalOperation;

        return influence;
    }





    enum Operation{
        AND,
        OR,
        XOR
    }


}
