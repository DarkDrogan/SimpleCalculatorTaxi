package org.drogan.simplecalculatortaxi.model;

import java.util.Date;

public class Shift {

    private float mEarning;
    private float mDistance;
    private float mTips;
    private float mExpenceGasoline;
    private float mCostGasoline;
    private Date mDateShift;

    public Shift(float earning, float distance, float tips, float expenceGasoline, float costGasoline) {
        mEarning = earning;
        mDistance = distance;
        mTips = tips;
        mExpenceGasoline = expenceGasoline;
        mCostGasoline = costGasoline;
        mDateShift = new Date();

    }

    public void setDateShift(Date dateShift) {
        mDateShift = dateShift;
    }

    public Date getDateShift() {
        return mDateShift;
    }

    public float getEarning() {
        return mEarning;
    }

    public void setEarning(float earning) {
        mEarning = earning;
    }

    public float getDistance() {
        return mDistance;
    }

    public void setDistance(float distance) {
        mDistance = distance;
    }

    public float getTips() {
        return mTips;
    }

    public void setTips(float tips) {
        mTips = tips;
    }

    public float getExpenceGasoline() {
        return mExpenceGasoline;
    }

    public void setExpenceGasoline(float expenceGasoline) {
        mExpenceGasoline = expenceGasoline;
    }

    public float getCostGasoline() {
        return mCostGasoline;
    }

    public void setCostGasoline(float costGasoline) {
        mCostGasoline = costGasoline;
    }
}
