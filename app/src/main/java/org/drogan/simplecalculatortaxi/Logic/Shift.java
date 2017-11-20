package org.drogan.simplecalculatortaxi.Logic;

public class Shift {

    private float mAverageExpenseGasoline;
    private float mEarnedMoney;
    private float mDistance;
    private float mCostOfFuel;

    public Shift(float distance, float averageExpenseGasoline, float costOfFuel, float earnedMoney){
        this.mDistance = distance;
        this.mAverageExpenseGasoline = averageExpenseGasoline;
        this.mCostOfFuel = costOfFuel;
        this.mEarnedMoney = earnedMoney;
    }

    public float getAverageExpenseGasoline() {
        return mAverageExpenseGasoline;
    }

    public void setAverageExpenseGasoline(float averageExpenseGasoline) {
        this.mAverageExpenseGasoline = averageExpenseGasoline;
    }

    public float getEarnedMoney() {
        return mEarnedMoney;
    }

    public void setEarnedMoney(float earnedMoney) {
        this.mEarnedMoney = earnedMoney;
    }

    public float getDistance() {
        return mDistance;
    }

    public void setDistance(float distance) {
        this.mDistance = distance;
    }

    public float getCostOfFuel() {
        return mCostOfFuel;
    }

    public void setCostOfFuel(float costOfFuel) {
        this.mCostOfFuel = costOfFuel;
    }
}
