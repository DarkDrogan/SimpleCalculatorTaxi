package org.drogan.simplecalculatortaxi.Logic;

import android.content.Context;

import java.util.List;

public class ShiftLab {

    private static ShiftLab mShiftLab;
    private List<Shift> mShifts;

    private ShiftLab(Context context){
        //here load all shift
    }

    public static ShiftLab getmShiftLab(Context context) {
        if(mShiftLab == null){
            mShiftLab = new ShiftLab(context);
        }
        return mShiftLab;
    }
}
