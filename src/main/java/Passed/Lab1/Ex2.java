package Passed.Lab1;

import java.util.HashMap;

public class Ex2 {
    public static double entropy(HashMap<Character, Double> probabilities) {
        double entropy = 0;
        for (double probability : probabilities.values()) {
            entropy -= probability * (Math.log(probability) / Math.log(2));
        }
        return entropy;
    }

    public static double maxEntropy(int totalMessages) {
        return Math.log(totalMessages) / Math.log(2);
    }

    public static double redundancy(double maxEntropy, double actualEntropy) {
        return 1 - (actualEntropy / maxEntropy);
    }

    public static double informationContent(char symbol, double probability) {
        return -Math.log(probability) / Math.log(2);
    }

    public static double messageInformation(int messageLength, double entropy) {
        return messageLength * entropy;
    }
    public static void main(String[] args) {
        // Step 1: Define the probability table (Replace with your actual data)
        HashMap<Character, Double> probabilities = new HashMap<>();
        probabilities.put('a', 0.1);
        probabilities.put('b', 0.2);
        probabilities.put('c', 0.3);
        probabilities.put('d', 0.2);
        probabilities.put('e', 0.2);

        int totalMessages = probabilities.size();

        // Step 2: Calculate Entropy
        double entropy = entropy(probabilities);
        System.out.println("Step 2 - Entropy of the source: " + entropy);

        // Step 3: Calculate Max Entropy and Redundancy
        double maxEntropy = maxEntropy(totalMessages);
        double redundancy = redundancy(maxEntropy, entropy);
        System.out.println("Step 3 - Maximum entropy: " + maxEntropy);
        System.out.println("Step 3 - Redundancy of the source: " + redundancy);



        // Step 4 (cont'd): Calculate information content for selected symbols
        double infoMostFrequent = informationContent('O', 0.09336824);
        double infoLeastFrequent = informationContent('Ш', 0.001745201);
        double infoAverageFrequency = informationContent('М', 0.028795812);

        System.out.println("Step 4 - Information content of most frequent symbol: " + infoMostFrequent);
        System.out.println("Step 4 - Information content of least frequent symbol: " + infoLeastFrequent);
        System.out.println("Step 4 - Information content of symbol with average frequency: " + infoAverageFrequency);

        // Step 5: Calculate information in a message
        String fullName = "Havryliuk Pavlo-Maksymyliano";
        int messageLength = fullName.length();
        double messageInfo = messageInformation(messageLength, entropy);
        System.out.println("Step 5 - Information in the message: " + messageInfo);

        // Step 6: Calculate characters per second for 100MB channel
        int channelSizeMB = 100;
        int channelSizeBytes = channelSizeMB * 1024 * 1024; // 1 MB = 1024^2 bytes
        int bytesPerCharacter = 2; // Assuming 2 bytes per character (UTF-16 encoding)
        double charactersPerSecond = channelSizeBytes / bytesPerCharacter;
        System.out.println("Step 6 - Characters per second to fully utilize the channel: " + charactersPerSecond);
    }



}
