package drs_corp.com.gsphoneapp;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.sip.SipAudioCall;
import android.net.sip.SipManager;
import android.net.sip.SipProfile;
import android.os.Bundle;


public class MainActivity extends Activity {
    public SipProfile myProfile = null;
    public SipManager mSipManager = null;
    public IncomingCallReceiver callReceiver;
    public SipAudioCall call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(mSipManager == null) {
            mSipManager = SipManager.newInstance(this);
        }
        try {
            //need to input domain here to register with sip server
            SipProfile.Builder b = new SipProfile.Builder("test", "testDomain");
            b.setPassword("pw");
            myProfile = b.build();
            Intent intent = new Intent();
            intent.setAction("android.SipDemo.INCOMING_CALL");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, Intent.FILL_IN_DATA);
            mSipManager.open(myProfile, pendingIntent, null);
        }
        catch (Exception e)
        {

        }
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.SipDemo.INCOMING_CALL");
        callReceiver = new IncomingCallReceiver();
        this.registerReceiver(callReceiver, filter);
    }
    //do something when there's an incoming call (get info and such
    public void updateStatus (SipAudioCall incomingCall)
    {
        SipProfile peerProfile = incomingCall.getPeerProfile();
        String URIString = peerProfile.getUriString();
    }
}
