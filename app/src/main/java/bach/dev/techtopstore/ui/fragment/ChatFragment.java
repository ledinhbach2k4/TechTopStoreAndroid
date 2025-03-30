package bach.dev.techtopstore.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import bach.dev.techtopstore.R;

public class ChatFragment extends Fragment {

    // Thay thế bằng Page ID của shop bạn trên Facebook
    private static final String FACEBOOK_PAGE_ID = "100015014236292";
    // Hoặc sử dụng username của page (nếu có)
    private static final String FACEBOOK_PAGE_USERNAME = "catvn";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        Button btnChatWithUs = view.findViewById(R.id.btn_chat_with_us);
        btnChatWithUs.setOnClickListener(v -> openMessenger());

        return view;
    }

    private void openMessenger() {
        try {
            // Thử mở ứng dụng Messenger nếu đã cài đặt
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("fb-messenger://user/" + FACEBOOK_PAGE_ID));
            startActivity(intent);
        } catch (Exception e) {
            // Nếu không cài đặt Messenger, mở trình duyệt
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://m.me/" + FACEBOOK_PAGE_USERNAME));
            startActivity(intent);
        }
    }
}