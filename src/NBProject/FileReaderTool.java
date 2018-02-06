package NBProject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class FileReaderTool {
    BufferedReader br = null;
    String line = "";
    String csvSplitBy = ",";


    public int[] singleOutPut(String csvFile, int numOfLine) {
        int[] output = new int[numOfLine];
        Vector<Integer> temp = new Vector<>();
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] words = line.split(csvSplitBy);
                temp.add(Integer.valueOf(words[0]));
            }
            for (int i = 0; i<numOfLine; i++){
                output[i] = temp.get(i);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return output;
    }

    public String[] singleOutPutS(String csvFile, int numOfLine) {

        String[] output = new String[numOfLine];
        Vector<String> temp = new Vector<>();
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] words = line.split(csvSplitBy);
                temp.add(words[0]);
            }
            for (int i = 0; i<numOfLine; i++){
                output[i] = temp.get(i);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return output;
    }

    public String[][] doubleOutPut(String csvFile, int numOfLine) {
        String[][] output = new String[numOfLine][2];
        Vector<String> temp1 = new Vector<>();
        Vector<String> temp2 = new Vector<>();
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] words = line.split(csvSplitBy);
                temp1.add(words[0]);
                temp2.add(words[1]);
            }
            for (int i = 0; i<numOfLine; i++){
                output[i][0] = temp1.get(i);
                output[i][1] = temp2.get(i);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return output;
    }

    public int[][] tripleOutPut(String csvFile, int numOfLine) {
        int[][] output = new int[numOfLine][3];
        Vector<Integer> temp1 = new Vector<>();
        Vector<Integer> temp2 = new Vector<>();
        Vector<Integer> temp3 = new Vector<>();
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] words = line.split(csvSplitBy);
                temp1.add(Integer.valueOf(words[0]));
                temp2.add(Integer.valueOf(words[1]));
                temp3.add(Integer.valueOf(words[2]));
            }
            for (int i = 0; i<numOfLine; i++){
                output[i][0] = temp1.get(i);
                output[i][1] = temp2.get(i);
                output[i][2] = temp3.get(i);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return output;
    }

}
