package org.drogan.simplecalculatortaxi.model;

public class Shift {

    private String mEarning;
    private String mDistance;
    private String mTips;
    private String mExpenceGasoline;
    private String mCostGasoline;
    private static Shift mShift;

    public Shift(){
        new Shift(mEarning, mDistance, mTips, mExpenceGasoline, mCostGasoline);
    }

    public Shift(String earning, String distance, String tips, String expenceGasoline, String costGasoline) {
        mEarning = earning;
        mDistance = distance;
        mTips = tips;
        mExpenceGasoline = expenceGasoline;
        mCostGasoline = costGasoline;
    }

    public static Shift getShift() {
        return new Shift();
    }

    public void setShift(Shift shift) {
        mShift = shift;
    }

    public String getEarning() {
        return mEarning;
    }

    public void setEarning(){
        setEarning("0");
    }

    public void setEarning(String earning) {
        mEarning = earning;
    }

    public String getDistance() {
        return mDistance;
    }

    public void setDistance(String distance) {
        mDistance = distance;
    }

    public String getTips() {
        return mTips;
    }

    public void setTips(String tips) {
        mTips = tips;
    }

    public String getExpenceGasoline() {
        return mExpenceGasoline;
    }

    public void setExpenceGasoline(String expenceGasoline) {
        mExpenceGasoline = expenceGasoline;
    }

    public String getCostGasoline() {
        return mCostGasoline;
    }

    public void setCostGasoline(String costGasoline) {
        mCostGasoline = costGasoline;
    }
}
