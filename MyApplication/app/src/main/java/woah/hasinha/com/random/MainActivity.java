package woah.hasinha.com.random;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context ctx, Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
                ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButtonBluetooth);
                toggleButton.setChecked(btAdapter.isEnabled());
                Snackbar.make(findViewById(R.id.toggleButtonBluetooth), "Bluetooth State Changed", Snackbar.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Lubba Dubba Dub Dub!!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        final ToggleButton bluetoothToggleButton = (ToggleButton) findViewById(R.id.toggleButtonBluetooth);
        if (null == bluetoothAdapter) {
            Toast.makeText(getApplicationContext(), "Bluetooth Not Supported On Your Device!!", Toast.LENGTH_LONG).show();
        } else {
            bluetoothToggleButton.setChecked(bluetoothAdapter.isEnabled());
        }
        bluetoothToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), bluetoothToggleButton.isChecked() + "", Toast.LENGTH_SHORT).show();
                if (bluetoothToggleButton.isChecked()) {
                    bluetoothAdapter.enable();
                    List<BluetoothDevice> bondedDevices = new ArrayList<>(bluetoothAdapter.getBondedDevices());
                    List<String> bondedDevicesName = new ArrayList<>();
                    for (BluetoothDevice bD : bondedDevices) {
                        bD.getBondState();
                        bondedDevicesName.add(bD.getName());
                    }
                    Toast.makeText(getApplicationContext(), "Bonded Devices: " + bondedDevicesName, Toast.LENGTH_LONG).show();
                } else {
                    bluetoothAdapter.disable();
                }
            }
        });
        registerReceiver(myReceiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
