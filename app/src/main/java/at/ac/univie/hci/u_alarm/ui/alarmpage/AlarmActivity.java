package at.ac.univie.hci.u_alarm.ui.alarmpage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


import at.ac.univie.hci.u_alarm.MainActivity;
import at.ac.univie.hci.u_alarm.R;

public class AlarmActivity extends AppCompatActivity {



    private static final String ALARM_NAME = "Feuer Alarm";
    private static final String ALARM_PLACE = "Erdgeschoss";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        Button stopButton;
        TextView tvAlarmName;
        TextView tvAlarmPlace;
        ImageButton mapButton;

        stopButton = findViewById(R.id.stop_button);
        mapButton = findViewById(R.id.map_button);
        tvAlarmName = findViewById(R.id.alarm_name);
        tvAlarmPlace = findViewById(R.id.alarm_place_name);

        // Wäre wsl eleganter wenn die Klasse selbst einen Alarmer als Feld hätte, dann könnte
        // die stop_alarm() Funktion auch noch im onPause/onDestroyed aufgerufen werden. Derzeit
        // leicht buggy wenn sich 2 oder mehr Alarme überschneiden, dann vibriert das ganze auch
        // weiter nachdem der Stop-BUtton gedrückt wurde. Wird in der Praxis wahrscheinlich keinem
        // auffallen.
        AlarmSignaler alarmTester = new AlarmSignaler(AlarmActivity.this.getApplicationContext(),
                500,
                255,
                10,
                500,
                10);

        // Geht vllt auch indem ein Runnable an den Thread gepasst wird statt nur Thread,
        // sollte aber egal sein. Wenns ohne extra Thread gestartet wird läuft zuerst die Vibration
        // komplett durch bevor das User Interface gezeichnet wurde.
        // Wenn Zeit ist noch mittels Threadhandlern statt "nackt" machen.
        Thread thread = new Thread(alarmTester::startAlarm);
        thread.start();


        stopButton.setOnClickListener(v -> {
            alarmTester.stopAlarm();
            Intent intent = new Intent(AlarmActivity.this, MainActivity.class);
            startActivity(intent);
        });

        mapButton.setOnClickListener(v -> {
            alarmTester.stopAlarm();

            Intent intent = new Intent(AlarmActivity.this, MainActivity.class);
            String goToMap = "gotomap";
            intent.putExtra("GO_TO_MAP", goToMap);
            startActivity(intent);
        });



        tvAlarmName.setText(ALARM_NAME);
        tvAlarmPlace.setText(ALARM_PLACE);

        // Save the Details of the alarm in arrays in MainActivity
        MainActivity.alarmTypes.add(ALARM_NAME);
        MainActivity.alarmPlaces.add(ALARM_PLACE);
        MainActivity.alarmDates.add(calculateDate());


    }

    // Set date (actual date and time) for alarm
    private static String calculateDate() {

        // Get date and time. Convert them to strings and assign it to alarmTime.
        DateTimeFormatter date = DateTimeFormatter.ofPattern("uuuu/MM/dd");
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime localTime = LocalTime.now();
        String dateString = date.format(localDate) + " | " + time.format(localTime) ;
        return dateString;
    }
}