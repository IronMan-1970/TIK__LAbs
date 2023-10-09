package Passed.Lab3;

import java.util.HashMap;
import java.util.Map;

public class InformationCharacteristicsCalculator {

    public static void main(String[] args) {
        String inputText = "3 laba zdana"; // Replace with your text

        // Calculate character frequencies
        Map<Character, Integer> frequencies = calculateFrequencies(inputText);

        // Calculate probabilities
        Map<Character, Double> probabilities = calculateProbabilities(frequencies, inputText.length());

        // Calculate entropy
        double entropy = calculateEntropy(probabilities);

        // Calculate average codeword length
        Map<Character, Integer> codewordLengths = calculateCodewordLengths(inputText, frequencies);
        double averageCodewordLength = calculateAverageCodewordLength(codewordLengths, probabilities);

        // Calculate KCC (Statistical Compression Ratio)
        double KCC = calculateKCC(entropy, averageCodewordLength);

        // Calculate KVE (Coefficient of Relative Efficiency)
        double KVE = calculateKVE(entropy, averageCodewordLength);

        // Calculate lser (Average number of elementary symbols in a codeword)
        double lser = calculateLSer(probabilities, codewordLengths);

        // Print results
        System.out.println("KCC: " + KCC);
        System.out.println("KVE: " + KVE);
        System.out.println("lser: " + lser);
    }

    public static Map<Character, Integer> calculateFrequencies(String text) {
        Map<Character, Integer> frequencies = new HashMap<>();
        for (char c : text.toCharArray()) {
            frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
        }
        return frequencies;
    }

    public static Map<Character, Double> calculateProbabilities(Map<Character, Integer> frequencies, int totalLength) {
        Map<Character, Double> probabilities = new HashMap<>();
        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            char c = entry.getKey();
            int frequency = entry.getValue();
            double probability = (double) frequency / totalLength;
            probabilities.put(c, probability);
        }
        return probabilities;
    }

    public static double calculateEntropy(Map<Character, Double> probabilities) {
        double entropy = 0;
        for (double probability : probabilities.values()) {
            entropy += -probability * log2(probability);
        }
        return entropy;
    }

    public static Map<Character, Integer> calculateCodewordLengths(String text, Map<Character, Integer> frequencies) {
        Map<Character, Integer> codewordLengths = new HashMap<>();
        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            char c = entry.getKey();
            int frequency = entry.getValue();
            int length = (int) Math.ceil(-log2((double) frequency / text.length()));
            codewordLengths.put(c, length);
        }
        return codewordLengths;
    }

    public static double calculateAverageCodewordLength(Map<Character, Integer> codewordLengths, Map<Character, Double> probabilities) {
        double averageLength = 0;
        for (Map.Entry<Character, Integer> entry : codewordLengths.entrySet()) {
            char c = entry.getKey();
            int length = entry.getValue();
            double probability = probabilities.get(c);
            averageLength += probability * length;
        }
        return averageLength;
    }

    public static double calculateKCC(double entropy, double averageCodewordLength) {
        return entropy / averageCodewordLength;
    }

    public static double calculateKVE(double entropy, double averageCodewordLength) {
        return entropy / averageCodewordLength;
    }

    public static double calculateLSer(Map<Character, Double> probabilities, Map<Character, Integer> codewordLengths) {
        double lser = 0;
        for (Map.Entry<Character, Double> entry : probabilities.entrySet()) {
            char ai = entry.getKey();
            double p_ai = entry.getValue();
            int l_i = codewordLengths.get(ai);
            lser += p_ai * l_i;
        }
        return lser;
    }

    public static double log2(double x) {
        return Math.log(x) / Math.log(2);
    }
}
