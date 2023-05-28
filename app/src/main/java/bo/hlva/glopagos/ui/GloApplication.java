package bo.hlva.glopagos.ui;

import android.app.Application;
import com.google.android.material.color.DynamicColors;

public class GloApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // TODO: Implement this method
        DynamicColors.applyToActivitiesIfAvailable(this);
    }
}
