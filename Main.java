package encryptdecrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static String alphabet = "abcdefghijklmnopqrstuvwxyz";

    public static void main(String[] args) {
        String operation = "enc";
        char[] msg = "".toCharArray();
        int key = 0;
        File out = null;
        File in = null;
        boolean printToFile = false;
        String result = "";
        String algorithm = "shift";
        for (int i = 0; i < args.length; i += 2) {
            switch (args[i]) {
                case "-alg":
                    algorithm = args[i + 1];
                    break;
                case "-mode":
                    operation = args[i + 1];
                    break;
                case "-key":
                    key = Integer.parseInt(args[i + 1]);
                    break;
                case "-data":
                    msg = args[i + 1].toCharArray();
                    break;
                case "-out":
                    out = new File(args[i + 1]);
                    break;
                case "-in":
                    in = new File(args[i + 1]);
                    break;
            }
        }

        if (!in.equals(null) && Arrays.equals(msg, "".toCharArray())) {
            try (Scanner scan = new Scanner(in)) {
                msg = scan.nextLine().toCharArray();
                System.out.println(msg);
            } catch (FileNotFoundException e) {
                System.out.println("Error: the file input wasn't found");
            }
        }
        switch (operation) {
            case "enc":
                result = encrypt(msg, key, algorithm);
                break;
            case "dec":
                result = decrypt(msg, key, algorithm);
                break;
        }

        if (!out.equals(null)) {
            try (PrintWriter output = new PrintWriter(out)) {
                printToFile = true;
                output.println(result);
            } catch (FileNotFoundException e) {
                System.out.println("Error: the file output wasn't found");
            }
        }

        if (!printToFile) {
            System.out.println(result);
        }
    }

    private static String encrypt(char[] msg, int key, String algo) {
        if (algo.equals("unicode")) {
            String encrypted = "";
            for (char item : msg) {
                encrypted += (char) (item + key);
            }
            return encrypted;
        } else {
            for (int i = 0; i < msg.length; i++) {
                for (int j = 0; j < alphabet.length(); j++) {
                    if (msg[i] == alphabet.charAt(j)) {
                        msg[i] = alphabet.charAt((j + key) % 26);
                        break;
                    }
                    if (msg[i] == Character.toUpperCase(alphabet.charAt(j))) {
                        msg[i] = Character.toUpperCase(alphabet.charAt((j + key) % 26));
                        break;
                    }
                }
            }
            return String.copyValueOf(msg);
        }
    }

    private static String decrypt(char[] msg, int key, String algo) {
        if (algo.equals("unicode")) {
            String decrypted = "";
            for (char item : msg) {
                decrypted += (char) (item - key);
            }
            return decrypted;
        } else {
            for (int i = 0; i < msg.length; i++) {
                for (int j = 0; j < alphabet.length(); j++) {
                    if (msg[i] == alphabet.charAt(j)) {
                        msg[i] = alphabet.charAt((j + 26 - key) % 26);
                        break;
                    }
                    if (msg[i] == Character.toUpperCase(alphabet.charAt(j))) {
                        msg[i] = Character.toUpperCase(alphabet.charAt((j + 26 - key) % 26));
                        break;
                    }
                }
            }
            return String.copyValueOf(msg);
        }

    }
}
