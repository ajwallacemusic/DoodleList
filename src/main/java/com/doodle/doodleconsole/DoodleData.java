package com.doodle.doodleconsole;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DoodleData {

    private static final String DATA_FILE = "src/main/resources/static/DoodleData.csv";
    private static Boolean isDataLoaded = false;

    private static ArrayList<HashMap<String, String>> allBreeders;


    /**
     * Fetch list of all values from loaded data,
     * without duplicates, for a given column.
     *
     * @param field The column to retrieve values from
     * @return List of all of the values of the given field
     */
    public static ArrayList<String> findAll(String field) {

        // load data, if not already loaded
        loadData();

        ArrayList<String> values = new ArrayList<>();

        for (HashMap<String, String> row : allBreeders) {
            String aValue = row.get(field);
            if (!values.contains(aValue)) {
                values.add(aValue);
            }
        }

        return values;
    }


    public static ArrayList<HashMap<String, String>> findAll() {

        // load data, if not already loaded
        loadData();

        return allBreeders;
    }

    /**
     * Returns results of searching the breeders data by key/value, using
     * inclusion of the search term.
     *
     * @param column   Column that should be searched.
     * @param value Value of the field to search for
     * @return List of all breeders matching the criteria
     */
    public static ArrayList<HashMap<String, String>> findByColumnAndValue(String column, String value) {

        // load data, if not already loaded
        loadData();

        ArrayList<HashMap<String, String>> breeders = new ArrayList<>();

        for (HashMap<String, String> row : allBreeders) {

            String aValue = row.get(column).toLowerCase();

            if (aValue.contains(value.toLowerCase())) {
                breeders.add(row);
            }
        }

        return breeders;
    }

    /**
     * search for breeders by any value
     * @param value
     * @return breeders
     */
    public static ArrayList<HashMap<String, String>> findByValue(String value) {

        // load data, if not already loaded
        loadData();

        ArrayList<HashMap<String, String>> breeders = new ArrayList<>();

        for (HashMap<String, String> row : allBreeders) {

            String rowstring = row.toString().toLowerCase();

            if (rowstring.contains(value.toLowerCase())) {
                breeders.add(row);
            }
        }
        return breeders;
    }

    /**
     * Read in data from the CSV file and store it in a list
     */
    private static void loadData() {

        // Only load data once
        if (isDataLoaded) {
            return;
        }

        try {

            // Open the CSV file and set up pull out column header info and records
            Reader in = new FileReader(DATA_FILE);
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
            List<CSVRecord> records = parser.getRecords();
            Integer numberOfColumns = records.get(0).size();
            String[] headers = parser.getHeaderMap().keySet().toArray(new String[numberOfColumns]);

            allBreeders = new ArrayList<>();

            // Put the records into a more friendly format
            for (CSVRecord record : records) {
                HashMap<String, String> newBreeder = new HashMap<>();

                for (String headerLabel : headers) {
                    newBreeder.put(headerLabel, record.get(headerLabel));
                }

                allBreeders.add(newBreeder);
            }

            // flag the data as loaded, so we don't do it twice
            isDataLoaded = true;

        } catch (IOException e) {
            System.out.println("Failed to load breeder data");
            e.printStackTrace();
        }
    }

}
