package com.example.spritandroidimals.GameEvents;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spritandroidimals.Animations.GameStart;
import com.example.spritandroidimals.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import static com.example.spritandroidimals.R.layout.map_editor;
/**
 * Creates the map Maker
 */
public final class MapMaker extends AppCompatActivity
{
  private FileInputStream mapFile = null;
  private final String FILENAME = "map";
  String content = "Hello";
  String time_stamps;

  private TextView Barb;
  private TextView Log;
  private TextView Pit;
  private TextView Plessy;
  private TextView Trap;


  private Switch defaultSelect;
  private Context mContext;

  private boolean mbDefault;
  private ArrayList<String> times;

  @Override
  protected void onCreate (Bundle savedInstanceState)
  {
    super.onCreate (savedInstanceState);
    setContentView (map_editor);

    mbDefault = true;
    times = new ArrayList<> ();

    defaultSelect = (Switch) findViewById (R.id.swtDefault);
    Barb = (TextView) findViewById (R.id.barb);
    Log = (TextView) findViewById (R.id.log);
    Pit = (TextView) findViewById (R.id.pit);
    Plessy = (TextView) findViewById (R.id.plessy);
    Trap = (TextView) findViewById (R.id.trap);

  }

  /**
   * Alert for time
   * @param name - name of Item
   * @param time - The timestamp
   */
  public void toast(String name, int time)
  {
    Toast.makeText(this, "You added" + name +" at " + (time / 1000) + "secs",
        Toast.LENGTH_SHORT).show();
  }

  /**
   * ALert for bad data
   */
  public void badtoast()
  {
    Toast.makeText(this, "Invalid Time",
        Toast.LENGTH_SHORT).show();
  }

  /**
   * On click event
   * @param v - VIew for button
   */
  public void onClickBarb (View v)
  {
    time_stamps = "barb" + "," + Barb.getText () + ",";

    if (Barb.getText ().toString ().equals (""))
    {
      badtoast ();
    }
    else
    {
      toast ("Barb ", Integer.parseInt (Barb.getText ().toString ()));
    }
    times.add (time_stamps);
  }

  /**
   * On click event
   * @param v - VIew for button
   */
  public void onClickLog (View v)
  {
    time_stamps = "log" + "," + Log.getText () + ",";

    if (Log.getText ().toString ().equals (""))
    {
      badtoast ();
    }
    else
    {
      toast ("Log ", Integer.parseInt (Log.getText ().toString ()));
    }
    times.add (time_stamps);
  }

  /**
   * On click event
   * @param v - VIew for button
   */
  public void onClickPit (View v)
  {
    time_stamps = "pit" + "," + Pit.getText () + ",";

    if (Pit.getText ().toString ().equals (""))
    {
      badtoast ();
    }
    else
    {
      toast ("Pit ", Integer.parseInt (Pit.getText ().toString ()));
    }
    times.add (time_stamps);
  }

  /**
   * On click event
   * @param v - VIew for button
   */
  public void onClickPlessy (View v)
  {
    time_stamps = "plessy" + "," + Plessy.getText () + ",";

    if (Plessy.getText ().toString ().equals (""))
    {
      badtoast ();
    }
    else
    {
      toast ("Plessy ", Integer.parseInt (Plessy.getText ().toString ()));
    }
    times.add (time_stamps);
  }

  /**
   * On click event
   * @param v - VIew for button
   */
  public void onClickTrap (View v)
  {
    time_stamps = "trap" + "," + Trap.getText () + ",";
    if (Trap.getText ().toString ().equals (""))
    {
      badtoast ();
    }
    else
    {
      toast ("Trap ", Integer.parseInt (Trap.getText ().toString ()));
    }
    times.add (time_stamps);
  }

  /**
   * When the user hits the back button, submit the changes to the file
   */
  public void onBackPressed () {

    super.onBackPressed();
    FileOutputStream mapFile = null;
    try {
      mapFile = openFileOutput(FILENAME, Context.MODE_PRIVATE);
    } catch (Exception e) {
      e.printStackTrace();
    }

    for (int i = 0; i < times.size(); i++) {
      try {
        mapFile.write(times.get(i).getBytes());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    try {
      mapFile.close();
    } catch (Exception e) {
      e.printStackTrace();
    }


    Intent intent = new Intent(getApplicationContext(), GameStart.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.putExtra("DefaultMap", mbDefault);
    startActivity(intent);
  }

  /**
   * On start process
   */
  protected void onStart ()
  {
    super.onStart ();
    defaultSelect.setChecked (!mbDefault);
  }

  /**
   * On resume process
   */
  @Override
  protected void onResume ()
  {
    super.onResume ();

    defaultSelect.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener ()
    {
      @Override
      public void onCheckedChanged (CompoundButton buttonView,
          boolean isChecked)
      {

        mbDefault = !isChecked;
      }
    });
  }
}

