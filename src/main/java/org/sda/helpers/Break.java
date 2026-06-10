package org.sda.helpers;

public class Break {

    public static void breakLine() {
        System.out.println("--------------------------------------------------");
    }

    public static void breakSection(String sectionName) {
        System.out.println("\n=== " + sectionName + " ===");
    }

    public static void waitForUser(String message) {
        System.out.println("\n" + message + " (Tekan Enter untuk melanjutkan...)");
        try {
            System.in.read();
        } catch (Exception e) {
            System.exit(0);
        }
    }
}
