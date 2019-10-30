package eg.com.misrins.mic.micproject;


import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

@SuppressWarnings("ResourceType")
public class Intro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        //getActionBar().hide();
        getSupportActionBar().hide();
        //ImageView img = (ImageView)findViewById(R.id.logointro);
        LinearLayout l = (LinearLayout) findViewById(R.id.logointro);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.animator.animate);
       // img.startAnimation(animation);
        l.startAnimation(animation);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
            /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(Intro.this,MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, 2700);
    }
    public void movetomain(View view){
     //  startActivity(new Intent(this,MainActivity.class));
    }
}
