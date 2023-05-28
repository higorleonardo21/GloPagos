package bo.hlva.glopagos.data.database;

import android.util.Log;

import androidx.room.TypeConverter;
import bo.hlva.glopagos.data.model.Day;

import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class DataTypeConverter {

    @TypeConverter
    public static String fromStringArrayList(ArrayList<Day> value) {
        return new Gson().toJson(value);
    }

    @TypeConverter
    public static ArrayList<Day> toStringArrayList(String value) {
        try {

            Type type = new TypeToken<ArrayList<Day>>() {}.getType();
            return new Gson().fromJson(value, type);

        } catch (Exception err) {

            Log.e("Error TypeConverter", err.getMessage());
        }

        return new ArrayList<>();
    }
}
