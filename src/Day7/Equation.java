package Day7;

import java.util.ArrayList;

public class Equation {
    long result;
    ArrayList<Integer> values;

    public Equation(long _result, ArrayList<Integer> _values){
        result = _result;
        values = _values;
    }

    public boolean validate(int part){
        if (part == 1)
            return validateD1(0,0);
        else
            return validateD2(0,0);
    }

    public boolean validateD1(long res, int i){
        if(res > result)
            return false;
        long newSum = res + values.get(i);
        long newMult = res * values.get(i);
        if(values.size() == i + 1){
            return newSum == result ||newMult == result;
        }
        else {
            return validateD1(newSum, i + 1) || validateD1(newMult, i + 1);
        }
    }

    public boolean validateD2(long res, int i){
        if(res > result)
            return false;
        long newSum = res + values.get(i);
        long newMult = res * values.get(i);
        long newConcat = Long.parseLong(res + "" + values.get(i));
        if(values.size() == i + 1){
            return newSum == result ||newMult == result || newConcat == result;
        }
        else {
            return validateD2(newSum, i + 1) || validateD2( newMult, i + 1) || validateD2(newConcat, i + 1);
        }
    }

}
