package com.nirmancraft.corepowermobileapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nirmancraft.corepower.R;
import com.nirmancraft.corepowermobileapp.license.LicenseManager;

public class LicenseActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        final Intent intent = getIntent();
        // check for license here
        TelephonyManager tMgr = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        String tempId = tMgr.getDeviceId();
        tempId = tempId.substring(0,5);
        final String deviceId = tempId;

        boolean licensed = LicenseManager.fetchLicenseDetails(deviceId, getApplicationContext());

        if(!licensed) {

                // Need to move to license entering module
                LayoutInflater layoutInflater = LayoutInflater.from(this);

                View promptView = layoutInflater.inflate(R.layout.prompt, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setView(promptView);
                final EditText input = (EditText) promptView.findViewById(R.id.license_text);

                alertDialogBuilder.setCancelable(false).setPositiveButton("Activate",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Push to DB here
                        String license = input.getText().toString();
                        if(LicenseManager.validateLicense(deviceId,license)) {
                            LicenseManager.saveLicense(license,LicenseActivity.this);
                            Intent browseIntent = new Intent(LicenseActivity.this,MainActivity.class);
                            startActivity(browseIntent);
                            finish();
                        } else {
                            Toast.makeText(LicenseActivity.this, "Invalid license. Please try again", Toast.LENGTH_LONG).show();
                            Intent browseIntent = new Intent(LicenseActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }).setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        finish();
                    }
                });

                AlertDialog alertD = alertDialogBuilder.create();
                alertD.show();

        } else {
            Intent browseIntent = new Intent(LicenseActivity.this,MainActivity.class);
            startActivity(browseIntent);
            finish();
        }
    }

}
