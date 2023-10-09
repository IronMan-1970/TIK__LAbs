package Passed.Lab5;
import java.util.*;

public class lzw {

    static ArrayList<Integer> encoding(String s1) {
        System.out.println("Encoding");
        HashMap<String, Integer> table = new HashMap<>();
        for (int i = 0; i <= 255; i++) {
            String ch = "" + (char) i;
            table.put(ch, i);
        }

        String p = "", c = "";
        p += s1.charAt(0);
        int code = 256;
        ArrayList<Integer> outputCode = new ArrayList<>();
        System.out.println("String\tOutput_Code\tAddition");
        for (int i = 0; i < s1.length(); i++) {
            if (i != s1.length() - 1)
                c += s1.charAt(i + 1);
            if (table.containsKey(p + c)) {
                p = p + c;
            } else {
                System.out.println(p + "\t" + table.get(p) + "\t\t" + (p + c) + "\t" + code);
                outputCode.add(table.get(p));
                table.put(p + c, code);
                code++;
                p = c;
            }
            c = "";
        }
        System.out.println(p + "\t" + table.get(p));
        outputCode.add(table.get(p));
        return outputCode;
    }

    static void decoding(ArrayList<Integer> op) {
        System.out.println("\nDecoding");
        HashMap<Integer, String> table = new HashMap<>();
        for (int i = 0; i <= 255; i++) {
            String ch = "" + (char) i;
            table.put(i, ch);
        }
        int old = op.get(0), n;
        String s = table.get(old);
        String c = "" + s.charAt(0);
        System.out.print(s);
        int count = 256;
        for (int i = 0; i < op.size() - 1; i++) {
            n = op.get(i + 1);
            if (!table.containsKey(n)) {
                s = table.get(old);
                s = s + c;
            } else {
                s = table.get(n);
            }
            System.out.print(s);
            c = "" + s.charAt(0);
            table.put(count, table.get(old) + c);
            count++;
            old = n;
        }
    }

    public static void main(String[] args) {
        String mesage1 = "Wake up samurai";
        ArrayList<Integer> outputCode1 = encoding(mesage1);
        System.out.print("Сoefficient: ");
        float a = mesage1.length();
        float b = outputCode1.size();
        float coef1 = a / b;
        System.out.println(coef1);
        System.out.print("Output Codes are: ");
        for (int i = 0; i < outputCode1.size(); i++) {
            System.out.print(outputCode1.get(i).toString() + " ");
        }
        System.out.println();
        decoding(outputCode1);


        String mesage2 = "WTF is a kilometer?!?!?!!? RAHHHHHHHHH!!! *Sounds of the US Anthem*";
        ArrayList<Integer> outputCode2 = encoding(mesage2);
        System.out.print("Сoefficient: ");
         a = mesage2.length();
         b = outputCode2.size();
        float coef2 = a / b;
        System.out.println(coef2);
        System.out.print("Output Codes are: ");
        for (int i = 0; i < outputCode2.size(); i++) {
            System.out.print(outputCode2.get(i).toString() + " ");
        }
        System.out.println();
        decoding(outputCode2);

        String mesage3 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus pretium justo eget elit eleifend, et dignissim quam eleifend. Nam vehicula nisl posuere velit volutpat, vitae scelerisque nisl imperdiet. Phasellus dignissim, dolor amet.";
        ArrayList<Integer> outputCode3 = encoding(mesage3);
        System.out.print("Coefficient: ");
        a = mesage3.length();
        b = outputCode3.size();
        float coef3 = a / b;
        System.out.println(coef3);
        System.out.print("Output Codes are: ");
        for (int i = 0; i < outputCode3.size(); i++) {
            System.out.print(outputCode3.get(i).toString() + " ");
        }
        System.out.println();
        decoding(outputCode3);
        float averageCoef = (coef1 + coef2 + coef3)/3;
        System.out.print("\nAverage Coefficient: ");
        System.out.println(averageCoef);

    }
}
