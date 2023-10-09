package Passed.Lab3;

import java.util.*;

class ShannonFanoNode {
    char symbol;
    String code;
    ShannonFanoNode left, right;
}

public class ShannonFanoEncoding {

    public static void main(String[] args) {
        String inputText = "3 laba zdana"; // Replace with your text

        // Shannon-Fano Encoding
        Map<Character, Integer> frequencies = calculateFrequencies(inputText);
        List<ShannonFanoNode> nodes = createNodes(frequencies);
        ShannonFanoNode root = buildTree(nodes);

        Map<Character, String> codes = generateCodes(root);

        String shannonFanoEncoded = encodeText(inputText, codes);
        String shannonFanoDecoded = decodeText(shannonFanoEncoded, root);

        System.out.println("Shannon-Fano Encoded: " + shannonFanoEncoded);
        System.out.println("Shannon-Fano Decoded: " + shannonFanoDecoded);
    }

    // Calculate frequencies of characters
    public static Map<Character, Integer> calculateFrequencies(String text) {
        Map<Character, Integer> frequencies = new HashMap<>();
        for (char c : text.toCharArray()) {
            frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
        }
        return frequencies;
    }

    // Create nodes for characters and their frequencies
    public static List<ShannonFanoNode> createNodes(Map<Character, Integer> frequencies) {
        List<ShannonFanoNode> nodes = new ArrayList<>();
        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            ShannonFanoNode node = new ShannonFanoNode();
            node.symbol = entry.getKey();
            node.code = "";
            nodes.add(node);
        }
        return nodes;
    }

    // Build Shannon-Fano tree
    public static ShannonFanoNode buildTree(List<ShannonFanoNode> nodes) {
        if (nodes.size() == 1) {
            return nodes.get(0);
        }
        List<ShannonFanoNode> leftNodes = nodes.subList(0, nodes.size() / 2);
        List<ShannonFanoNode> rightNodes = nodes.subList(nodes.size() / 2, nodes.size());
        ShannonFanoNode node = new ShannonFanoNode();
        node.left = buildTree(leftNodes);
        node.right = buildTree(rightNodes);
        return node;
    }

    // Generate Shannon-Fano codes
    public static Map<Character, String> generateCodes(ShannonFanoNode node) {
        Map<Character, String> codes = new HashMap<>();
        generateCodesHelper(node, codes, "");
        return codes;
    }

    private static void generateCodesHelper(ShannonFanoNode node, Map<Character, String> codes, String code) {
        if (node != null) {
            node.code = code;
            if (node.left == null && node.right == null) {
                codes.put(node.symbol, code);
            }
            generateCodesHelper(node.left, codes, code + "0");
            generateCodesHelper(node.right, codes, code + "1");
        }
    }

    // Encode text using Shannon-Fano codes
    public static String encodeText(String text, Map<Character, String> codes) {
        StringBuilder encodedText = new StringBuilder();
        for (char c : text.toCharArray()) {
            encodedText.append(codes.get(c));
        }
        return encodedText.toString();
    }

    // Decode text using Shannon-Fano tree
    public static String decodeText(String encodedText, ShannonFanoNode root) {
        StringBuilder decodedText = new StringBuilder();
        ShannonFanoNode currentNode = root;

        for (char bit : encodedText.toCharArray()) {
            if (bit == '0') {
                currentNode = currentNode.left;
            } else if (bit == '1') {
                currentNode = currentNode.right;
            }

            if (currentNode.left == null && currentNode.right == null) {
                decodedText.append(currentNode.symbol);
                currentNode = root;
            }
        }

        return decodedText.toString();
    }
}
