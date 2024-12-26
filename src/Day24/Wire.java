package Day24;

import java.util.ArrayList;

public class Wire {

    String name;
    boolean value;
    boolean normalValue;

    boolean isSwapped;
    boolean swappedValue;

    Wire normalWire1;
    Wire normalWire2;

    Operation normalOperation;

    Wire wire1;
    Wire wire2;

    Operation operation;

    ArrayList<Wire> influence = new ArrayList<>();

    public Wire(String nm, boolean v){
        name = nm;
        value = v;
    }

    public Wire(String nm) {
        name = nm;

    }

    public int countOperations(){
        if(isEnd()){
            return 0;
        }
        return wire1.countOperations() + wire2.countOperations() + 1;
    }

    public boolean isA(Wire zWire){
        String xName = zWire.name.replace('z', 'x');
        String yName = zWire.name.replace('z', 'y');

        if(isEnd()){
            return false;
        }

        if((wire1.name.equals(xName) && wire2.name.equals(yName)) || (wire1.name.equals(yName) && wire2.name.equals(xName))){
            return true;
        }else{
            return false;
        }
    }

    public boolean isAnyA(){
        if(isEnd()){
            return false;
        }
        return wire1.connectedToXY() && wire2.connectedToXY();
    }

    public boolean connectedToXY(){
        return name.charAt(0) == 'x' || name.charAt(0) == 'y';
    }

    public boolean isNearEnd(){
        if(wire1 == null){
            return false;
        }
        return wire1.isEnd() || wire2.isEnd();
    }

    public boolean isEnd(){
        return wire1 == null || wire2 == null;
    }


    public void setConnection(Wire w1, String op, Wire w2) {
        wire1 = w1;
        wire2 = w2;
        operation = stringToOperation(op);
    }


    public void assertInfluence(){
        assertInfluence(this);
    }

    public void assertInfluence(Wire zWire){
        if(influence.contains(zWire)) //TODO use set instead?
            return;
        influence.add(zWire);
        if(wire1 != null) {
            wire1.assertInfluence(zWire);
            wire2.assertInfluence(zWire);
        }
    }

    public ArrayList<Wire> swap(Wire otherWire){
        isSwapped = true;

        wire1 = otherWire.normalWire1;
        wire2 = otherWire.normalWire2;
        operation = otherWire.normalOperation;

        otherWire.wire1 = normalWire1;
        otherWire.wire2 = normalWire2;
        otherWire.operation = normalOperation;

        ArrayList<Wire> adjusted = new ArrayList<>();
        adjusted.addAll(influence);
        adjusted.addAll(otherWire.influence);
        return adjusted;
    }

    public void reset(){
        wire1 = normalWire1;
        wire2 = normalWire2;
        operation = normalOperation;
        value = normalValue;
    }

    public void setNormal(){
        normalWire1 = wire1;
        normalWire2 = wire2;
        normalOperation = operation;
        normalValue = value;
    }

    public boolean calculate(){
        if(wire1 == null){
            return value;
        }

        wire1.calculate();
        wire2.calculate();

        switch(operation){
            case Operation.AND:
                value = wire1.value && wire2.value;
                break;
            case Operation.OR:
                value = wire1.value || wire2.value;
                break;
            case Operation.XOR:
                value = wire1.value ^ wire2.value;
                break;
        }

        return value;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(" - ");
        if(wire1 != null) {
            sb.append(wire1.name);
            sb.append(" ").append(operation).append(" ");
            sb.append(wire2.name);
        }else{
            sb.append(value ? 1 : 0);
        }

        return sb.toString();
    }

    public String toStringWithValue(){
        return name + " - " + (value ? "1" : "0");
    }

    public String toStringFull(){
        return name + " " + (value ? "1" : "0") + " - " + wire1.name + " " + (wire1.value ? "1" : "0")  + " " + operation + " " + wire2.name + " " + (wire2.value ? "1" : "0");
    }

    public String toStringChained(){
        return name + " " + (value ? "1" : "0") + " (" + (wire1 == null ? "-" : wire1.toStringChained()) + ") " + "("+ (wire2 == null ? "-" : wire2.toStringChained()) + ")";
    }


    public static Operation stringToOperation(String op) {
        return switch (op) {
            case "OR" -> Operation.OR;
            case "AND" -> Operation.AND;
            case "XOR" -> Operation.XOR;
            default -> null;
        };
    }

    enum Operation{
        AND,
        OR,
        XOR
    }


}
