package bo.hlva.glopagos.ui.auth;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import bo.hlva.glopagos.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // binding views
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        setupViews();
    }
    
    private void setupViews() {
    
    
    	
    }
}
