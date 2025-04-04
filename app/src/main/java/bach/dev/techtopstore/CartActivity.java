package bach.dev.techtopstore;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import bach.dev.techtopstore.ui.fragment.CartFragment;

public class CartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        int orderId = getIntent().getIntExtra("orderId", 0);
        Log.i("CartActivity", "Received orderId: " + orderId);

        CartFragment cartFragment = new CartFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("orderId", orderId);
        cartFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.cart_fragment_container, cartFragment)
                .commit();
    }
}