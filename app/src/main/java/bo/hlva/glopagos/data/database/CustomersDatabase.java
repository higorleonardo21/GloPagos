package bo.hlva.glopagos.data.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import bo.hlva.glopagos.data.model.Customer;

@Database(
        entities = {Customer.class},
        version = 1,
        exportSchema = false)
@TypeConverters(DataTypeConverter.class)
public abstract class CustomersDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "customers";
    private static CustomersDatabase INSTANCE;

    public abstract CustomersDao getCustomersDao();

    public static CustomersDatabase getInstance(Context context) {

        if (INSTANCE == null) {
            synchronized (CustomersDatabase.class) {
                if (INSTANCE == null) {

                    INSTANCE =
                            Room.databaseBuilder(context, CustomersDatabase.class, DATABASE_NAME)
                                    .allowMainThreadQueries()
                                    .build();
                }
            }
        }

        return INSTANCE;
    }
}
