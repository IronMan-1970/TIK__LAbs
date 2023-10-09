package Passed.Lab6;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HammingCode {

    private static void hammingCommon(List<List<Integer>> src, int sNum, boolean encode) {
        int[] sRange = new int[sNum];
        for (int i = 0; i < sNum; i++) {
            sRange[i] = i;
        }

        for (List<Integer> i : src) {
            int syndrome = 0;
            for (int s : sRange) {
                int sind = 0;
                for (int p = (int) Math.pow(2, s); p <= i.size(); p += Math.pow(2, s + 1)) {
                    for (int j = 0; j < Math.pow(2, s); j++) {
                        if ((p + j - 1) >= i.size()) {
                            break;
                        }
                        sind ^= i.get(p + j - 1);
                    }
                }
                if (encode) {
                    i.set((int) Math.pow(2, s) - 1, sind);
                } else {
                    syndrome += (Math.pow(2, s) * sind);
                }
            }
            if (!encode && syndrome != 0) {
                i.set(syndrome - 1, i.get(syndrome - 1) == 1 ? 0 : 1);
            }
        }
    }

    private static String bytesToBinary(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
        }
        return result.toString();
    }

    public static String hammingEncode(String msg, int mode) {
        StringBuilder result = new StringBuilder();
        byte[] msgBytes = msg.getBytes();
        int sNum = (int) Math.ceil(Math.log(Math.log(mode + 1) + mode + 1) / Math.log(2));

        List<List<Integer>> toHamming = new ArrayList<>();
        for (int i = 0; i < msgBytes.length * 8 / mode; i++) {
            List<Integer> code = new ArrayList<>();
            for (int j = i * mode; j < i * mode + mode; j++) {
                code.add((msgBytes[j / 8] >> (7 - j % 8)) & 1);
            }
            for (int j = 0; j < sNum; j++) {
                code.add((int) Math.pow(2, j) - 1, 0);
            }
            toHamming.add(code);
        }

        hammingCommon(toHamming, sNum, true);

        for (List<Integer> i : toHamming) {
            for (Integer bit : i) {
                result.append(bit);
            }
        }

        return result.toString();
    }

    public static String hammingDecode(String msg, int mode) {
        StringBuilder result = new StringBuilder();
        int sNum = (int) Math.ceil(Math.log(Math.log(mode + 1) + mode + 1) / Math.log(2));
        int resLen = msg.length() / (mode + sNum);
        int codeLen = mode + sNum;

        List<List<Integer>> toHamming = new ArrayList<>();
        for (int i = 0; i < resLen; i++) {
            List<Integer> code = new ArrayList<>();
            for (int j = i * codeLen; j < i * codeLen + codeLen; j++) {
                code.add(Integer.parseInt(msg.substring(j, j + 1)));
            }
            toHamming.add(code);
        }

        hammingCommon(toHamming, sNum, false);

        for (List<Integer> i : toHamming) {
            for (int j = 0; j < sNum; j++) {
                i.remove((int) Math.pow(2, j) - 1 - j);
            }
            for (Integer bit : i) {
                result.append(bit);
            }
        }

        String resultStr = result.toString();
        byte[] msgBytes = new byte[resultStr.length() / 8];
        for (int i = 0; i < resultStr.length(); i += 8) {
            String byteStr = resultStr.substring(i, i + 8);
            msgBytes[i / 8] = (byte) Integer.parseInt(byteStr, 2);
        }

        return new String(msgBytes);
    }

    public static String noizer(String msg, int mode) {
        int sNum = (int) Math.ceil(Math.log(Math.log(mode + 1) + mode + 1) / Math.log(2));
        int codeLen = mode + sNum;
        int cnt = msg.length() / codeLen;
        StringBuilder result = new StringBuilder();

        Random rand = new Random();

        for (int i = 0; i < cnt; i++) {
            String toNoize = msg.substring(i * codeLen, i * codeLen + codeLen);
            int noize = rand.nextInt(codeLen);
            char[] toNoizeArr = toNoize.toCharArray();
            toNoizeArr[noize] = (toNoizeArr[noize] == '0') ? '1' : '0';
            result.append(toNoizeArr);
        }

        return result.toString();
    }

    public static void main(String[] args) {
        int MODE = 8; // count of significant bits
        String msg = "Лаба 6 з TIK готова!";

        String encMsg = hammingEncode(msg, MODE); // Encode your message to Hamming code
        System.out.println("enc:" + encMsg);

        String noizeMsg = noizer(encMsg, MODE); // Noizer for encoded message
        System.out.println("nz: " + noizeMsg);

        String decMsg = hammingDecode(noizeMsg, MODE); // Hamming decode
        System.out.println("dec:" + decMsg);
    }
}
