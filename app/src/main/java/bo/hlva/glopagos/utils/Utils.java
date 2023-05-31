package bo.hlva.glopagos.utils;

import bo.hlva.glopagos.data.model.Day;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class Utils {

    // get date
    public static String getCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        Date date = new Date();
        String text = simpleDateFormat.format(date);

        return text;
    }

    // organize list day
    public static ArrayList<Day> getOrganizedList(ArrayList<Day> list) {

        Collections.sort(
                list,
                new Comparator<Day>() {
                    @Override
                    public int compare(Day day1, Day day2) {
                        return new Integer(day2.getNumber())
                                .compareTo(new Integer(day1.getNumber()));
                    }
                });

        return list;
    }
}
