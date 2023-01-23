import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.*;
import java.sql.*;

public class CovidDatabase {
    private ArrayList<CovidEntry> data;
    private static final int SAFE = 5;
    //Constructor
    public CovidDatabase() {
        data = new ArrayList<CovidEntry>();
    }

    //Reads data from file and populates the arraylist
    public void readCovidData(String filename) {
        try {
            Scanner scanner = new Scanner(new File(filename));
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String[] CovidDetails = scanner.nextLine().split(",");
                String state = CovidDetails[0];
                int month = Integer.parseInt(CovidDetails[1]);
                int day = Integer.parseInt(CovidDetails[2]);
                int dailyInfections = Integer.parseInt(CovidDetails[3]);
                int dailyDeaths = Integer.parseInt(CovidDetails[4]);
                int totalInfections = Integer.parseInt(CovidDetails[5]);
                int totalDeaths = Integer.parseInt(CovidDetails[6]);

                CovidEntry Covid = new CovidEntry(state, month, day, 
                dailyInfections, dailyDeaths, totalInfections, totalDeaths);
                data.add(Covid);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }            

    }

    //This is a function to transfer to a database.
/* 
    private void transferCovidData(String filename) {
        String jdbcURL = "jC:\\Users\\cscha\\FinalProjectJavaTwo\\Covid.db;";
        String csvFilePath = "C:\\Users\\cscha\\FinalProjectJavaTwo\\covid_data.csv";
        Connection connection = null;


        try {
            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;
            lineReader.readLine(); // skip header line
            String sql = "INSERT INTO ENTRY (id, state, month, day, daily_infections, daily_deaths, total_infections, total_deaths) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
           
            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(",");
                String state = data[0];
                int month = Integer.parseInt(data[1]);
                int day = Integer.parseInt(data[2]);
                int dailyInfections = Integer.parseInt(data[3]);
                int dailyDeaths = Integer.parseInt(data[4]);
                int totalInfections = Integer.parseInt(data[5]);
                int totalDeaths = Integer.parseInt(data[6]);

                statement.setString(1, state);
                statement.setInt(2, month);
                statement.setInt(3, day);
                statement.setInt(4, dailyInfections);
                statement.setInt(5, dailyDeaths);
                statement.setInt(6, totalInfections);
                statement.setInt(7, totalDeaths);
                statement.addBatch();
            }
 
            lineReader.close();
 
            connection.commit();
            connection.close();
 
        } catch (IOException ex) {
            System.err.println(ex);
        } catch (SQLException ex) {
            ex.printStackTrace();
 
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    } 
    */

        //Accessor Methods

    //Return number of entries
    public int countRecords() {
        return data.size();
    }

    //Return sum of all daily deaths
    public int getTotalDeaths() {
        int deaths = 0;

        for(CovidEntry deathCount: data) {
            deaths += deathCount.getDailyDeaths();
        }

        return deaths;
    }

    //Return sum of all daily infections
    public int getTotalInfections() {
        int infections = 0;

        for(CovidEntry infectionCount: data) {
            infections += infectionCount.getDailyInfections();
        }

        return infections;
    }

    //Return sum of all daily deaths w/ specific date
    public int countTotalDeaths(int m, int d) {
        int deaths = 0;

        for(CovidEntry deathCount: data) {
            if(deathCount.getMonth() == m && deathCount.getDay() == d) {
                deaths += deathCount.getDailyDeaths();
            }
        }

        return deaths;
    }
    
    //Return sum of all daily infections w/ specific date
    public int countTotalInfections(int m, int d) {
        int infections = 0;

        for(CovidEntry infectionCount: data) {
            if(infectionCount.getMonth() == m && infectionCount.getDay() == d) {
                infections += infectionCount.getDailyInfections();
            }
        }

        return infections;
    }

    //Return the CovidEntry with the highest daily deaths
    public CovidEntry peakDailyDeaths(String st) {
        int deaths = 0;
        CovidEntry peakDaily = null;
        
        for(CovidEntry dataEntry: data) {
            if(dataEntry.getDailyDeaths() > deaths && st.equalsIgnoreCase(dataEntry.getState())) {
                deaths = dataEntry.getDailyDeaths();
                peakDaily = dataEntry;
            }
        }

        return peakDaily;
    }

    //Return an ArrayList of all records for a specific date
    public ArrayList <CovidEntry> getDailyDeaths(int m, int d) {
        ArrayList <CovidEntry> allRecords = new ArrayList <CovidEntry>();

        for(CovidEntry records: data) {
            if(records.getMonth() == m && records.getDay() == d) {
                allRecords.add(records);
            }
        }

        return allRecords;
    }

    //Return CovidEntry records with highest daily deaths for a specific date
    public CovidEntry peakDailyDeaths(int m, int d) {
        ArrayList <CovidEntry> allRecords = new ArrayList <CovidEntry>();
        int deaths = 0;
        CovidEntry peakDeaths = null;

        for(CovidEntry records: data) {
            if(records.getMonth() == m && records.getDay() == d) {
                allRecords.add(records);
            }
        }
        for(CovidEntry highDeaths: allRecords) {
            if(highDeaths.getDailyDeaths() > deaths) {
                deaths = highDeaths.getDailyDeaths();
                peakDeaths = highDeaths;
            }
        }

        return peakDeaths;
    }

    //Return CovidEntry records with highest total deaths
    public CovidEntry mostTotalDeaths() {
        int deaths = 0;
        CovidEntry mostDeaths = null;

        for(CovidEntry records: data) {
            if(records.getTotalDeaths() > deaths) {
                deaths = records.getTotalDeaths();
                mostDeaths = records;
            }
        }

        return mostDeaths;
    }

    //Return a new ArrayList with all records that match the
    //requested date and have minimum requested daily infection
    public ArrayList <CovidEntry> listMinimumDailyInfections(int m, int d, int min) {
        ArrayList <CovidEntry> mins = new ArrayList <CovidEntry>();

        for(CovidEntry records: data) {
            if(records.getMonth() == m && records.getDay() == d && records.getDailyInfections() >= min) {
                mins.add(records);
            }
        }

        return mins;
    }

    //Look for the first five consecutive days of decreasing daily infections
    public ArrayList <CovidEntry> safeToOpen(String st) {
        int daysDecreasing = 1;
        ArrayList <CovidEntry> days = new ArrayList <CovidEntry>();
        ArrayList <CovidEntry> stateNumbers = new ArrayList <CovidEntry>();

        for(CovidEntry count: data) {
            if(count.getState().equalsIgnoreCase(st)) {
                stateNumbers.add(count);
            }
        }
        for(int count = 0; count < stateNumbers.size() - 1; count++) {
            if(stateNumbers.get(count).getDailyInfections() >
            stateNumbers.get(count + 1).getDailyInfections()) {
                if(daysDecreasing == 1) {
                    days.add(stateNumbers.get(count));
                    days.add(stateNumbers.get(count + 1));
                    daysDecreasing++;
                } else if (daysDecreasing > 1) {
                    days.add(stateNumbers.get(count + 1));
                    daysDecreasing++;
                }
                if (daysDecreasing == SAFE) {
                    return days;
                } 
            } else {
                days.clear();
                daysDecreasing = 1;
            }
        }

    if (daysDecreasing == 1) {
        return null;
    }

        return days;
    }

    //Compare objects to find top ten
    public ArrayList <CovidEntry> topTenDeaths(int m, int d) {
        ArrayList <CovidEntry> topDeaths = getDailyDeaths (m, d);
        Collections.sort(topDeaths);

        for(int count = topDeaths.size() - 1; count > 9; count--) {
            topDeaths.remove(count);
        }

        return topDeaths;
    }
}
