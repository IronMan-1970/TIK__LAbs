package Passed.Lab4;// Java program to find move to front transform of
// a given string
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MoveToFront {
    // Returns index at which character of the input text
    // exists in the list
    static int search(char input_char, char[] list) {
        for (int i = 0; i < list.length; i++) {
            if (list[i] == input_char) {
                return i;
            }
        }
        return -1;
    }

    // Takes curr_index of input_char as argument
    // to bring that character to the front of the list
    static void moveToFront(int curr_index, char[] list) {
        char record[] = Arrays.copyOf(list, list.length);

        // Characters pushed one position right
        // in the list up until curr_index
        for (int i = curr_index; i > 0; i--) {
            list[i] = record[i - 1];
        }

        // Character at curr_index stored at 0th position
        list[0] = record[curr_index];
    }
    static void moveToFrontd(int index, StringBuilder list) {
        char[] record = new char[list.length()];
        list.getChars(0, list.length(), record, 0);

        // Characters pushed one position right
        // in the list up until index
        for (int i = index; i > 0; i--) {
            list.setCharAt(i, record[i - 1]);
        }

        // Character at index stored at 0th position
        list.setCharAt(0, record[index]);
    }
    // Move to Front Encoding
    static int[] mtfEncode(String input_text, int len_text, char[] list) {
        int[] output_arr = new int[len_text];

        for (int i = 0; i < len_text; i++) {

            // Linear Searches the characters of input_text
            // in list
            output_arr[i] = search(input_text.charAt(i), list);

            // Printing the Move to Front Transform
            System.out.print(output_arr[i] + " ");

            // Moves the searched character to the front
            // of the list
            moveToFront(output_arr[i], list);
        }
        return output_arr;
    }

    public static List<String> levenshteinCoding(int[] n) {
        List<String> encodedMessage = new ArrayList<>();
        for (int n1:n) {
            StringBuilder result = new StringBuilder();
            if (n1 == 0) {
                return Collections.singletonList("0");
            }

            int c = 1;
            StringBuilder code = new StringBuilder();

            while (true) {
                String binaryN = Integer.toBinaryString(n1);
                String truncatedBinaryN = binaryN.substring(1);
                code.insert(0, truncatedBinaryN);
                n1 = (int) Math.ceil(Math.log(n1 + 1) / Math.log(2)) - 1;

                if (n1 == 0) {
                    break;
                }

                c++;
            }


            for (int i = 0; i < c; i++) {
                result.append('1');
            }
            result.append('0').append(code);
            encodedMessage.add(result.toString());
        }
        return encodedMessage;
    }

    public static List<Integer> decodeLevenshteinCoding(List<String> code) {
        String cleanText = code.get(0).replaceAll("[,]","");
        List<String> strings = List.of(cleanText.split(" "));
        List<Integer>decodedMessage = new ArrayList<>();
        for (String code0:strings) {
                String code1 = code0.replace("[","");

            int ones = 0;
            for (char c : code1.toCharArray()) {
                if (c == '0') {
                    break;
                }
                ones++;
            }

            if (ones == 0) {
                return Collections.singletonList(0);
            }

            int n = 1;
            int offset = ones + 1;

            for (int i = 0; i < ones - 1; i++) {
                int newN = Integer.parseInt("1" + code1.substring(offset, offset + n), 2);
                offset += n;
                n = newN;
            }
            decodedMessage.add(n);
        }
        return decodedMessage;
    }
    static void mtfDecode(List<Integer> arr, int n) {
        // Maintains an ordered list of legal symbols
        StringBuilder list = new StringBuilder("abcdefghijklmnopqrstuvwxyz ");


        for (int i = 0; i < n; i++) {

            // Printing characters of Inverse MTF as output
            System.out.print(list.charAt(arr.get(i)));

            // Moves the printed character to the front
            // of the list
            moveToFrontd(arr.get(i), list);
        }
    }

    // Driver program to test functions above
    public static void main(String[] args) {

        String input_text = "chetverta laba z tik zdana";
        int len_text = input_text.length();

        // Maintains an ordered list of legal symbols
        char[] list = "abcdefghijklmnopqrstuvwxyz ".toCharArray();

        System.out.println("Input text: " + input_text);
        System.out.print("Move to Front encoded: ");
        List<String> encoded = Collections.singletonList(levenshteinCoding((mtfEncode(input_text, len_text, list))).toString());
        System.out.println("\nEncoded: " + encoded);
        List<Integer> decoded = decodeLevenshteinCoding(encoded);
        System.out.println("Decoded: " + decoded);
        System.out.print("Move to Front decoded: ");
        mtfDecode(decoded, decoded.size());
        double KP = (Math.log(26) / Math.log(2)) * input_text.length() / encoded.size();
        System.out.println("\nКоефіцієнт стиску:" + KP);
        System.out.println();
    }

}

// this code is contributed by bhardwajji
