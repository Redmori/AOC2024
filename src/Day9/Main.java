package Day9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        char[] input = readInput();
        int[] digits = convertCharToInt(input);
        printArray(digits);

        ArrayList<Integer> filesystem = generateFileSystem(digits);
        System.out.println(filesystem);
        ArrayList<Integer> filesystemP2 = (ArrayList<Integer>) filesystem.clone();

        compressFiles(filesystem);
        System.out.println(filesystem);

        long checkSum = calculateCheckSum(filesystem);
        System.out.println("Day 9 Part 1: " + checkSum);

        System.out.println(filesystemP2);
        //compressWholeFiles(filesystemP2);
        System.out.println(filesystemP2);
        ArrayList<FileBlock> files = generateFileSystemP2(digits);
        printFileBlocks(files);
        compressWholeFiles(files);
        System.out.println("==========");
        printFileBlocks(files);
        ArrayList<Integer> compressedSystemP2 = filesToArray(files);
        System.out.println(compressedSystemP2);

        long checkSumP2 = calculateCheckSum(compressedSystemP2);
        System.out.println("Day 9 Part 2: " + checkSumP2);

        //ArrayList<FileBlock> compressedP2 = compressBlocks(files[1], files[0]);
        //printFileBlocks(compressedP2);

    }

    private static ArrayList<Integer> filesToArray(ArrayList<FileBlock> files){
        ArrayList<Integer> array = new ArrayList<>();
        int i = 0;
        for(FileBlock file : files){
            for(int n = 0; n < file.size; n++){
                if(file.isNull)
                    array.add(0);
                else
                    array.add(file.value);
            }
        }
        return array;
    }

    private static void compressWholeFiles(ArrayList<FileBlock> files) {
        int nChanged = 0;
        boolean changed = true;
        while(changed){
            changed = compressStep(files);
            System.out.println("changed " + nChanged);
            nChanged++;
        }
    }

    private static boolean compressStep(ArrayList<FileBlock> files) {
        for(int i = 0; i < files.size(); i ++){
            FileBlock gap = files.get(i);
            if(gap.isNull){
                for(int j = files.size() - 1; j > i; j--){
                    FileBlock block = files.get(j);
                    if(!block.isNull && gap.size >= block.size){
                        gap.size = gap.size - block.size;
                        files.remove(block);
                        FileBlock newEmpty = new FileBlock(block.size,0);
                        newEmpty.isNull = true;
                        files.add(j, newEmpty);
                        //mergeNulls(newEmpty, files);
                        files.add(i,block);
                        System.out.println(i + " -> " + j);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static void mergeNulls(FileBlock nullFile, ArrayList<FileBlock> files) {
        int index = files.indexOf(nullFile);
        if(index + 1 < files.size()) {
            FileBlock rightFile = files.get(index + 1);
            if (rightFile.isNull) {
                nullFile.size += rightFile.size;
                files.remove(rightFile);
            }
        }
        if(index - 1 >= 0){
            FileBlock leftFile = files.get(index - 1);
            if (leftFile.isNull) {
                nullFile.size += leftFile.size;
                files.remove(leftFile);
            }
        }

    }

    private static ArrayList<FileBlock> generateFileSystemP2(int[] digits) {
        ArrayList<FileBlock> system = new ArrayList<>();
        int n = 0;
        for(int i = 0; i < digits.length; i++){
            if(i%2 == 0) {
                system.add(new FileBlock(digits[i],i/2));
            }
            else{
                FileBlock nullFile = new FileBlock(digits[i],0);
                nullFile.isNull = true;
                system.add(nullFile);
            }
        }
        return system;
    }


//
//    private static ArrayList<FileBlock> compressBlocks(ArrayList<FileBlock> files, ArrayList<FileBlock> nulls) {
//        ArrayList<FileBlock> compressed = new ArrayList<>();
//
//        int n = 0;
//        for(FileBlock nullBlock : nulls){
//            if(n >= files.size())
//                break;
//            compressed.add(files.get(n));
//            ArrayList<FileBlock> movedBlocks = new ArrayList<>();
//            for (int i = files.size() - 1; i >= 0; i--) {
//                FileBlock file = files.get(i);
//                if(file.size <= nullBlock.size){
//                    movedBlocks.add(file);
//                    nullBlock.size = nullBlock.size - file.size;
//                }
//            }
//            for(FileBlock moved : movedBlocks){
//                compressed.add(moved);
//                files.remove(moved);
//            }
//            compressed.add(nullBlock);
//            n++;
//        }
//
//        return compressed;
//    }

//    private static ArrayList<FileBlock> groupInFiles(ArrayList<Integer> filesystem){
//        ArrayList<FileBlock> files = new ArrayList<>();
//        int i = 0;
//        int value;
//
//        value = filesystem.get(i);
//        int blockSize = 1;
//        while(filesystem.get(i + blockSize) == value){
//            blockSize++;
//        }
//        FileBlock newFile = new FileBlock(blockSize, value);
//        files.add(newFile);
//        i += blockSize;
//
//
//        return files;
//    }




//    private static ArrayList<FileBlock>[] generateFileSystemP2(int[] digits) {
//        ArrayList<FileBlock>[] system = new ArrayList[2];
//        system[0] = new ArrayList<>();
//        system[1] = new ArrayList<>();
//        int n = 0;
//        for(int i = 0; i < digits.length; i++){
//                if(i%2 == 0) {
//                    system[1].add(new FileBlock(digits[i],i/2));
//                }
//                else{
//                    FileBlock nullFile = new FileBlock(digits[i],0);
//                    nullFile.isNull = true;
//                    system[0].add(nullFile);
//                }
//        }
//        return system;
//    }

    private static void printFileBlocks(ArrayList<FileBlock> files){
        for(FileBlock file : files){
            System.out.println(file);
        }
    }


//    private static void compressWholeFiles(ArrayList<Integer> filesystem) {
//        int i = 0;
//        while(i < filesystem.size()){
//            if(filesystem.get(i) == null){
//                removeTrailingNull(filesystem);
//                if(!(i <filesystem.size()))
//                    return;
//                int gapSize = 1;
//                while(filesystem.get(i + gapSize) == null){
//                    gapSize++;
//                }
//                int start;
//                int digit;
//                int j = 0;
//                int blockSize;
//                do {
//                    start = filesystem.size() - 1 - j;
//                    blockSize = 0;
//                    digit = filesystem.get(start - j);
//                    while (filesystem.get(start - j) != null && filesystem.get(start - j - blockSize) == digit) {
//                        blockSize++;
//                        j++;
//                    }
//                }
//                while(blockSize > gapSize);
//
//                for(int k = 0; k < blockSize; k++){
//                    filesystem.set(i+k,digit);
//                }
//
//
//
//            }
//            i++;
//        }
//    }


    private static long calculateCheckSum(ArrayList<Integer> filesystem) {
        long sum = 0;
        for(int i = 0; i < filesystem.size(); i++){
            sum += i*filesystem.get(i);
        }
        return sum;
    }

    private static void compressFiles(ArrayList<Integer> filesystem) {
        int i = 0;
        while(i < filesystem.size()){
            if(filesystem.get(i) == null){
                removeTrailingNull(filesystem);
                if(!(i <filesystem.size()))
                    return;
                filesystem.set(i, filesystem.getLast());
                filesystem.removeLast();
            }
            i++;
        }
    }

    private static void removeTrailingNull(ArrayList<Integer> filesystem) {
        while(filesystem.getLast() == null){
            filesystem.removeLast();
        }
    }

    private static ArrayList<Integer> generateFileSystem(int[] digits) {
        ArrayList<Integer> system = new ArrayList<>();
        int n = 0;
        for(int i = 0; i < digits.length; i++){
            for(int digit = 0; digit < digits[i]; digit++){
                if(i%2 == 0) {
                    system.add(i/2);
                }
                else{
                    system.add(null);
                }
            }
        }
        return system;
    }


    private static void printArray(int[] array){
        for(int i : array){
            System.out.print(i);
        }
        System.out.println();
    }

    private static int[] convertCharToInt(char[] charArray){
        int[] intArray = new int[charArray.length];
        for(int i = 0; i < charArray.length; i++){
            intArray[i] = Integer.parseInt(String.valueOf(charArray[i]));
        }
        return intArray;
    }

    private static char[] readInput() {
        File inputFile = new File("src/Day9/input.txt");
        char[] input = new char[0];
        try {
            Scanner scanner = new Scanner(inputFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                input = line.toCharArray();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return input;
    }
}