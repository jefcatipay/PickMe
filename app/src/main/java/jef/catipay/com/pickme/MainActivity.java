package jef.catipay.com.pickme;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private ImageButton imageBtn;
    private Button shareBtn;
    private RelativeLayout mainLayout;
    private Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        imageBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 0);
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, selectedImage);
                shareIntent.setType("image/jpeg");
                startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send)));
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {

                    selectedImage = imageReturnedIntent.getData();
                    imageBtn.setVisibility(View.GONE);
                    shareBtn.setVisibility(View.VISIBLE);

                    try {
                InputStream inputStream = getContentResolver().openInputStream(selectedImage);
                Drawable yourDrawable = Drawable.createFromStream(inputStream, selectedImage.toString() );
                mainLayout.setBackground(yourDrawable);

                    } catch (Exception e) {
                        Toast.makeText(this, "WROOONNGG", Toast.LENGTH_LONG).show();
                    }

                }
                break;
        }

    }
    private void findViews(){
        imageBtn = (ImageButton) findViewById(R.id.imageButton);
        shareBtn = (Button) findViewById(R.id.shareBtn);
        mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
    }

}
