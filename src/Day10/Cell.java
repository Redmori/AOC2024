package Day10;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Cell {
    int x;
    int y;
    int height;
    boolean hasSaved = false;
    boolean hasSavedAll = false;
    Set<Cell> savedPeaks = new HashSet<>();
    ArrayList<Cell> allSavedPeaks = new ArrayList<>();

    Cell neighbourN;
    Cell neighbourE;
    Cell neighbourW;
    Cell neighbourS;


    public Cell(int _height, int _x, int _y){
        x = _x;
        y = _y;
        height = _height;
    }


    public Set<Cell> getPeaks(){
        if(height == 9){
            Set<Cell> peak = new HashSet<>();
            peak.add(this);
            return peak;
        }

        if(!hasSaved){

            if(neighbourN != null && neighbourN.height == height + 1)
                savedPeaks.addAll(neighbourN.getPeaks());
            if(neighbourE != null && neighbourE.height == height + 1)
                savedPeaks.addAll(neighbourE.getPeaks());
            if(neighbourS != null && neighbourS.height == height + 1)
                savedPeaks.addAll(neighbourS.getPeaks());
            if(neighbourW != null && neighbourW.height == height + 1)
                savedPeaks.addAll(neighbourW.getPeaks());

            hasSaved = true;
        }
        return savedPeaks;
    }

    public ArrayList<Cell> getAllPeaks(){
        if(height == 9){
            ArrayList<Cell> peak = new ArrayList<>();
            peak.add(this);
            return peak;
        }

        if(!hasSavedAll){

            if(neighbourN != null && neighbourN.height == height + 1)
                allSavedPeaks.addAll(neighbourN.getAllPeaks());
            if(neighbourE != null && neighbourE.height == height + 1)
                allSavedPeaks.addAll(neighbourE.getAllPeaks());
            if(neighbourS != null && neighbourS.height == height + 1)
                allSavedPeaks.addAll(neighbourS.getAllPeaks());
            if(neighbourW != null && neighbourW.height == height + 1)
                allSavedPeaks.addAll(neighbourW.getAllPeaks());

            hasSavedAll = true;
        }
        return allSavedPeaks;
    }
}
