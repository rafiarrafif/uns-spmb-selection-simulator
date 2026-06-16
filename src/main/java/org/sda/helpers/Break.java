package org.sda.helpers;

public class Break {

    public static void breakLine() {
        System.out.println("--------------------------------------------------");
    }

    public static void breakSection(String sectionName) {
        System.out.println("\n=== " + sectionName + " ===");
    }

    public static void waitForUser(String message) {
        System.out.println("\nTekan Enter untuk " + message + "...");
        try {
            System.in.read();
        } catch (Exception e) {
            System.exit(0);
        }
    }
}
