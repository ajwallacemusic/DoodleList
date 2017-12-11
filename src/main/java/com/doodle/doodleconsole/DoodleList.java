package com.doodle.doodleconsole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DoodleList {

    private static Scanner in = new Scanner(System.in);

    public static void main (String[] args) {

        // Initialize our field map with key/name pairs
        HashMap<String, String> columnChoices = new HashMap<>();
        columnChoices.put("name", "Name");
        columnChoices.put("breeds", "Breeds");
        columnChoices.put("location", "Location");
        columnChoices.put("all", "All");


        // Top-level menu options
        HashMap<String, String> actionChoices = new HashMap<>();
        actionChoices.put("search", "Search");
        actionChoices.put("list", "List");

        System.out.println("Welcome to the DoodleList App!");

        // Allow the user to search until they manually quit
        while (true) {

            String actionChoice = getUserSelection("View breeders by:", actionChoices);

            if (actionChoice.equals("list")) {

                String columnChoice = getUserSelection("List", columnChoices);

                if (columnChoice.equals("all")) {
                    printBreeders(DoodleData.findAll());
                } else {

                    ArrayList<String> results = DoodleData.findAll(columnChoice);

                    System.out.println("\n*** All " + columnChoices.get(columnChoice) + " Values ***");

                    // Print list of breeds, locations, etc
                    for (String item : results) {
                        System.out.println(item);
                    }
                }
            } else { // choice is "search"

                // How does the user want to search (e.g. by breed or location)
                String searchField = getUserSelection("Search by:", columnChoices);

                // What is their search term?
                System.out.println("\nSearch term: ");
                String searchTerm = in.nextLine();

                if (searchField.equals("all")) {
                    printBreeders(DoodleData.findByValue(searchTerm));
                } else {
                    printBreeders(DoodleData.findByColumnAndValue(searchField, searchTerm));
                }
            }
        }
    }

    /*edit following methods */

    // ﻿Returns the key of the selected item from the choices Dictionary
    private static String getUserSelection(String menuHeader, HashMap<String, String> choices) {

        Integer choiceIdx;
        Boolean validChoice = false;
        String[] choiceKeys = new String[choices.size()];

        // Put the choices in an ordered structure so we can
        // associate an integer with each one
        Integer i = 0;
        for (String choiceKey : choices.keySet()) {
            choiceKeys[i] = choiceKey;
            i++;
        }

        do {

            System.out.println("\n" + menuHeader);

            // Print available choices
            for (Integer j = 0; j < choiceKeys.length; j++) {
                System.out.println("" + j + " - " + choices.get(choiceKeys[j]));
            }

            choiceIdx = in.nextInt();
            in.nextLine();

            // Validate user's input
            if (choiceIdx < 0 || choiceIdx >= choiceKeys.length) {
                System.out.println("Invalid choice. Try again.");
            } else {
                validChoice = true;
            }

        } while(!validChoice);

        return choiceKeys[choiceIdx];
    }


    // Print a list of jobs
    private static void printBreeders(ArrayList<HashMap<String, String>> someBreeders) {
        String attribute = null;
        String value = null;
        ArrayList<String> breederstring = new ArrayList<>();

        if (someBreeders.isEmpty()) {
            System.out.println("Sorry, there are no results matching your search.");
        }
        else {
            for (Integer i = 0; i < someBreeders.size(); i++) {
                HashMap<String, String> breeders = someBreeders.get(i);
                System.out.println("*****");
                for (Map.Entry<String, String> breeder : breeders.entrySet()) {
                    attribute = breeder.getKey();
                    value = breeder.getValue();
                    String thebreeder = attribute + ": " + value;
                    System.out.println(thebreeder);

                }
            }
            System.out.println("*****");
        }
    }

}
