package at.ac.univie.hci.u_alarm.ui.AlarmPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import at.ac.univie.hci.u_alarm.MainActivity;
import at.ac.univie.hci.u_alarm.R;
import at.ac.univie.hci.u_alarm.ui.alarmlist.Alarm;

public class AlarmActivity extends AppCompatActivity {

    public static  ArrayList<String> alarmtypeGlobal =new ArrayList<>();
    public static    ArrayList<String>  alarmortGlobal =new ArrayList<>();
    public static  ArrayList alarmdateGlobal = new ArrayList<String>();

    Button stop_button;
    TextView alarmname ;
    TextView ort ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        stop_button = (Button) findViewById(R.id.stop_button);
        alarmname = findViewById(R.id.alarm_name);
        ort = findViewById(R.id.alarm_ort_in_alarm_page);

        stop_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlarmActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        Random random = new Random();

        ArrayList<String>  alarmtype =new ArrayList<>(Arrays.asList(
                "Feuer Alarm","Probe Alarm",
                "Alarm1","Alarm2"
        ));
        ArrayList<String>  alarmort =new ArrayList<>(Arrays.asList(
                "Erdgeschoss","zweite Stock ",
                "erste Stock","Garten"
        ));

        //Alarmtype und Ort wird gewählt
        int random_Number_Alarmtype= random.nextInt(alarmtype.size());
        int random_Number_Alarmort= random.nextInt(alarmort.size());

        alarmname.setText(alarmtype.get(random_Number_Alarmtype));
        ort.setText(alarmort.get(random_Number_Alarmort));

        //Datum wird geholt und in einem List einfügen
        DateTimeFormatter jahr_monat_tag = DateTimeFormatter.ofPattern("uuuu/MM/dd");
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter zeit = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime localTime = LocalTime.now();
        String date = jahr_monat_tag.format(localDate) +" " + zeit.format(localTime) ;

        Alarm list = new Alarm(alarmname.getText().toString(),ort.getText().toString(), date);

        alarmtypeGlobal.add(alarmname.getText().toString());
        alarmortGlobal.add(ort.getText().toString());
        alarmdateGlobal.add(date);


    }
}