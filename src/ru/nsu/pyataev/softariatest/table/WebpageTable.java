package ru.nsu.pyataev.softariatest.table;

import java.io.*;
import java.net.URL;
import java.util.HashMap;

public class WebpageTable extends HashMap<String, String>{
    public String put(URL url, String html){
        return put(url.toString(), html);
    }

    public void putFromFile(String filename){
        try(
                BufferedReader br = new BufferedReader(new FileReader(filename));
        ){
            String line = "";

            while((line = br.readLine()) != null){
                String[] row = line.split(" ", 2);
                this.put(row[0], row[1]);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
