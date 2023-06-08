package bo.hlva.glopagos.data.database;

import android.content.Context;
import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import bo.hlva.glopagos.data.model.Customer;

@Database(
        entities = {Customer.class},
        version = 2,
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
                                    .addMigrations(MIGRATION_1_2)
                                   // .fallbackToDestructiveMigration()
                                    .build();
                }
            }
        }

        return INSTANCE;
    }

    static final Migration MIGRATION_1_2 =
            new Migration(1, 2) {
                @Override
                public void migrate(SupportSQLiteDatabase database) {

                    database.execSQL("ALTER TABLE customers ADD COLUMN percentage INTEGER NOT NULL DEFAULT 20");
                }
            };
}
