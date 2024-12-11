package Day11;

public class Stone {
    public long number;

    public Stone(int value){
        number = value;
    }

    public String toString(){
        return "" + number;
    }



    public Stone blink(){
        if(passesFirstRule()){
            doFirstRule();
        } else if (passesSecondRule()) {
            return doSecondRule();
        } else{
            doThirdRule();
        }
        return null;
    }

    public boolean passesFirstRule(){
        return number == 0;
    }

    public boolean passesSecondRule(){
        String digits = "" + number;
        return digits.length() % 2 == 0;
    }

    private void doFirstRule(){
        number = 1;
    }

    private Stone doSecondRule(){
        String digits = "" + number;
        String first = digits.substring(0,digits.length()/2);
        number = Long.parseLong(first);
        String second = digits.substring(digits.length()/2);

        return new Stone(Integer.parseInt(second));
        //split
    }

    private void doThirdRule(){
        number *= 2024;
    }
}
