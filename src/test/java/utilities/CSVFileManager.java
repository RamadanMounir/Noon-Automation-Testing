package utilities;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVFileManager {

    private final String csvFilePath;
    private final List<CSVRecord> records;
    private static final Logger log = LoggerFactory.getLogger(CSVFileManager.class);


    public CSVFileManager(String csvFilePath){

        this.csvFilePath = csvFilePath;

        this.records = loadCSV();
    }

    List<CSVRecord>loadCSV(){

       try(FileReader  reader = new FileReader(csvFilePath);
        CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)){

           List<CSVRecord> rows = new ArrayList<>();
           for(CSVRecord record:parser){
               rows.add(record);
           }
           log.info("Reading test data from file: [{}]", csvFilePath);
           return rows;

       } catch (IOException e) {
           log.error("Couldn't read file: [{}]", csvFilePath, e);
           throw new RuntimeException("CSV file could not be loaded: " + csvFilePath, e);
       }
        }

    /** Get total row count */
    public int getRowCount() {
        return records.size();
    }
    /** Get a whole row by index */
    public CSVRecord getRow(int index) {
        return records.get(index);
    }
    /** Get a cell by row index and column name */
    public String getValue(int rowIndex, String columnName) {
        return records.get(rowIndex).get(columnName);
    }
    public List<String> getColumn(String columnName) {
        List<String> columnValues = new ArrayList<>();
        for (CSVRecord record : records) {
            columnValues.add(record.get(columnName));
        }
        return columnValues;
    }
    /** Get all rows */
    public List<CSVRecord> getAllRows() {
        return new ArrayList<>(records);
    }
    }


