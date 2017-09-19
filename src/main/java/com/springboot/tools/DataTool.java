package com.springboot.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liuyongg on 1/8/2017.
 */
public class DataTool {
    public static Date formatData(String date) {
        String string = "2015-02-10 22:00:00";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1;
        try {
            d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(string);
            System.out.println("DateTime d1>>>>>>: " + d1);
            String d2 = format.format(d1);
            System.out.println("DateTime d2>>>>>>: " + d2);
            Date d3;
            d3 = format.parse(d2);
            System.out.println("DateTime d3>>>>>>: " + format.format(d3));
            return d3;
        } catch (ParseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return null;
    }

}
