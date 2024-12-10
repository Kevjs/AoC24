import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class DayOne {
    public static void main(String[] args) {
        int diff = 0;

        ArrayList<Integer> left = new ArrayList<>();
        ArrayList<Integer> right = new ArrayList<>();

        try {
            File myFile = new File("./1/raw_input");

            FileWriter leftWriter = new FileWriter("left_raw_input");
            FileWriter rightWriter = new FileWriter("right_raw_input");

            leftWriter.write("");
            rightWriter.write("");

            Scanner myReader = new Scanner(myFile);


            while(myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] split = data.split("   ");
                
                leftWriter.append(split[0] + "\n");
                rightWriter.append(split[1] + "\n");

                left.add(Integer.parseInt(split[0]));
                right.add(Integer.parseInt(split[1]));
            }

            myReader.close();
            leftWriter.close();
            rightWriter.close();

        } catch (Exception e) {
            System.out.println("ERROR");
            System.out.println(e);
            System.exit(1);
        }

        left.sort((a, b) -> a - b);
        right.sort((a, b) -> a - b);

        try {
            FileWriter leftWriter = new FileWriter("2_left_sorted_input");
            FileWriter rightWriter = new FileWriter("2_right_sorted_input");

            leftWriter.write("");
            rightWriter.write("");

            for(int i = 0, size = left.size(); i < size; i++) {
                leftWriter.append(left.get(i) + "\n");
                rightWriter.append(right.get(i) + "\n");
                
                diff += Math.abs(left.get(i) - right.get(i));
            }

            leftWriter.close();
            rightWriter.close();


        } catch(Exception e) {

        }

        System.out.println("\nDiff = " + diff);

        int similarCalc = 0;

        int rightIndex = 0;

        int prevAmount = 0;

        for(int i = 0, size = left.size(); i < size; i++) {
            int leftNum = left.get(i);

            if(i > 0 && leftNum == left.get(i-1)) {
                similarCalc += prevAmount;
            } else {
                prevAmount = 0;
                while(rightIndex < right.size() && right.get(rightIndex) < leftNum) {
                    rightIndex++;
                }
                if(rightIndex < right.size()) {
                    int rightNum = right.get(rightIndex);
                    if(rightNum == leftNum) {
                        int count = 0;
                        while(rightIndex < right.size() && right.get(rightIndex) == leftNum) {
                            count++;
                            rightIndex++;
                        }
                        prevAmount += count * leftNum;
                        similarCalc += prevAmount;
                    }
                }
            }
        }
        System.out.println("\nsimilarCalc = " + similarCalc);

    }
}