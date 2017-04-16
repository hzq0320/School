package functions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.hdu.school.R;


public class Second_hand extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        Intent intent = new Intent ();
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);          //隐藏系统自带标题栏
        setContentView(R.layout.function_second_hand);
        getSupportActionBar().hide();                           //隐藏系统自带标题栏
        ImageView back=(ImageView) findViewById(R.id.secondhand_back_imageview);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onBackPressed();
            }
        });

    }

}
