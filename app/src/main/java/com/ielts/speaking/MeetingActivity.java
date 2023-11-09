package com.ielts.speaking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.webrtc.VideoTrack;

import live.videosdk.rtc.android.Meeting;
import live.videosdk.rtc.android.Stream;
import live.videosdk.rtc.android.VideoSDK;
import live.videosdk.rtc.android.VideoView;
import live.videosdk.rtc.android.listeners.ParticipantEventListener;

public class MeetingActivity extends AppCompatActivity {
    Meeting meeting;
    String token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcGlrZXkiOiIwN2M2YjZlNC1jM2I5LTQ2MDMtYTZiZS1hZmI5MjMwZTllZTYiLCJwZXJtaXNzaW9ucyI6WyJhbGxvd19qb2luIl0sImlhdCI6MTY5OTQxNjc1NSwiZXhwIjoxODU3MjA0NzU1fQ.jcQiIMvJUIKZO7My7utfaZReEDeTk3EOgCS51lHzADU";
    String meetingId="MEETING_ID_GOES_HERE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);

        // ask runTime permission
        checkSelfPermission(REQUESTED_PERMISSIONS[0], PERMISSION_REQ_ID);
        checkSelfPermission(REQUESTED_PERMISSIONS[1], PERMISSION_REQ_ID);


        Button btnJoin = findViewById(R.id.btnJoin);

        btnJoin.setOnClickListener(v -> {
            VideoSDK.initialize(getApplicationContext());
            VideoSDK.config(token);
//            meeting = VideoSDK.initMeeting(this, meetingId, "John Due", true, true, null, null, null);

            //4. Join VideoSDK Meeting
            meeting.join();

            ((TextView)findViewById(R.id.tvMeetingId)).setText(meeting.getMeetingId());
            btnJoin.setVisibility(View.GONE);
            findViewById(R.id.meetingLayout).setVisibility(View.VISIBLE);

            // adding the video stream of the participant into the 'VideoView'
            meeting.getLocalParticipant().addEventListener(new ParticipantEventListener() {
                @Override
                public void onStreamEnabled(Stream stream) {
                    if (stream.getKind().equalsIgnoreCase("video")) {
                        VideoTrack track = (VideoTrack) stream.getTrack();
                        ((VideoView)findViewById(R.id.localView)).addTrack(track);
                    }
                }
            });
        });

    }
    private static final int PERMISSION_REQ_ID = 22;
    private static final String[] REQUESTED_PERMISSIONS = {
            android.Manifest.permission.RECORD_AUDIO,
            android.Manifest.permission.CAMERA
    };
    private void checkSelfPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, requestCode);
        }
    }



}