package com.example.sms_bomber.views;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.sms_bomber.R;

import java.text.NumberFormat;
import java.text.ParseException;

public class SendMessage extends Fragment {
    EditText phoneNum;
    EditText message;
    EditText repeat;
    Button btnSend;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sendmessage_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        this.phoneNum = getView().findViewById(R.id.phone_number);
        this.message = getView().findViewById(R.id.message);
        this.repeat = getView().findViewById(R.id.repeat);
        this.btnSend = getView().findViewById(R.id.btn_send);

        btnSend.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(SendMessage.this.requireActivity(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                sendMessage();
            } else {
                ActivityCompat.requestPermissions(SendMessage.this.requireActivity(), new String[]{Manifest.permission.SEND_SMS}, 100);
            }
        });
    }

    private void sendMessage() {
        String sPhoneNum = phoneNum.getText().toString().trim();
        String sMessage = message.getText().toString().trim();
        int iNumberOfSms = Integer.parseInt(repeat.getText().toString());

        if (!sPhoneNum.equals("") && !sMessage.equals("")) {
            SmsManager smsManager = SmsManager.getDefault();
            for (int i = 0; i <= iNumberOfSms; i++) {
                smsManager.sendTextMessage(sPhoneNum, null, sMessage, null, null);
            }
            Toast.makeText(requireContext().getApplicationContext(), "SMS sent", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(requireContext().getApplicationContext(), "Enter correct values", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            sendMessage();
        } else {
            Toast.makeText(requireContext().getApplicationContext(), "Permission denied!", Toast.LENGTH_SHORT).show();
        }
    }
}
