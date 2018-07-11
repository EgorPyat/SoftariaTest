package ru.nsu.pyataev.softariatest;

import ru.nsu.pyataev.softariatest.email.Email;
import ru.nsu.pyataev.softariatest.mymaputils.MapUtils;
import ru.nsu.pyataev.softariatest.table.WebpageTable;

import java.io.*;
import java.util.Properties;
import java.util.Set;

public class App {
    public static void main(String[] args) {
        if(args.length != 1){
            System.out.println("Enter TO email address\n");
            return;
        }

        WebpageTable yesterday = new WebpageTable();
        WebpageTable today = new WebpageTable();

        //tables initialization
        yesterday.putFromFile("yesterday_table");
        today.putFromFile("today_table");

        MapUtils.MapsKeysDifference<String> diff = MapUtils.keysDiff(yesterday, today);

        // Create message text
        StringBuilder text = new StringBuilder();

        Set<String> dfl = diff.getEntriesInLeft();
        text.append("Здравствуйте, дорогая И.О. секретаря!\n\nЗа последние сутки во вверенных Вам сайтах произошли следующие изменения:\n");
        if(dfl.size() > 0) text.append("\nИсчезли следующие страницы:\n");
        for(String elem : diff.getEntriesInLeft()){
            text.append(elem);
            text.append("\n");
        }
        Set<String> dfr = diff.getEntriesInRight();
        if(dfr.size() > 0) text.append("\nПоявились следующие новые:\n");
        for(String elem : dfr){
            text.append(elem);
            text.append("\n");
        }
        Set<String> dfb = diff.getEntriesInBothDiffValues();
        if(dfb.size() > 0) text.append("\nИзменились следующие страницы:\n");

        for(String elem : dfb){
            text.append(elem);
            text.append("\n");
        }
        text.append("\nС уважением,\nАвтоматизированная система\nмониторинга.");

        //create and send message
        String to = args[0];
        try {
            File f = new File("gmail");
            InputStream s = new FileInputStream(f);
            Properties props = Email.getProperties(s);

            Email.sendByHostProvider(Email.createAuthSession(props), to, "Изменения в таблицах",text.toString());
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}