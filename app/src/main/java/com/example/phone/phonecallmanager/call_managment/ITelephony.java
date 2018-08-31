package com.android.internal.telephony;

public interface ITelephony {
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
                    double aDouble, String aString);
    boolean endCall();
    void silenceRinger();
    void answerRingingCall();
}
