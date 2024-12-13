package Day13;

public class Equation {
    long c1;
    long c2;
    long c3;
    long c4;
    long d1;
    long d2;

    public Equation(long _c1, long _c2, long _c3, long _c4, long _d1, long _d2){
        c1 = _c1;
        c2 = _c2;
        c3 = _c3;
        c4 = _c4;
        d1 = _d1;
        System.out.println(d1);
        d2 = _d2;
    }


    public long[] evaluate(){
        long[] results = new long[2];
        System.out.println(d1);
        long denominator = c1 * c4 - c2 * c3;
        long numerator1 = c4 * d1 - c2 * d2;
        long numerator2 = c1 * d2 - c3 * d1;
        if (numerator1 % denominator != 0L || numerator2 % denominator != 0L) {
            return new long[]{-1L,-1L};
        }
        results[0] = numerator1 / denominator;
        results[1] = numerator2 / denominator;
        return results;
    }


    public String toString(){
        String string = "";
        string += c1 + " + " + c2 + " = " + d1 + "\n" + c3 + " + " + c4 + " = " + d2;

        return string;
    }
}
