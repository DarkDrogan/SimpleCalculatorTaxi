package org.drogan.simplecalculatortaxi.Logic;

public class CalculationTemporaryExpenseOfGasoline {


    private float startExpenseGasoline;
    private float startDistance;
    private float averageExpenseGasoline;
    private float earnedMoney;
    private float distanse;
    private float costOfFuel;

    private float expenseOnGasoline;
    private float income;
    private float percentForApplication;
    private float wastedMoneyOfPercent;
    private float profit;


    public static void main(String[] args) {
        CalculationTemporaryExpenseOfGasoline calc = new CalculationTemporaryExpenseOfGasoline();
        System.out.println(calc.getIntervalExpenseGasoline(0f, 0f, 610f, 9.3f));
    }

    public CalculationTemporaryExpenseOfGasoline(){
    }

    public void setVariablesAndCalculateData(float averageExpenseGasoline, float earnedMoney,
                                             float distanse, float costOfFuel,
                                             float percentForApplication){
        this.averageExpenseGasoline = averageExpenseGasoline;
        this.earnedMoney = earnedMoney;
        this.distanse = distanse;
        this.costOfFuel = costOfFuel;
        this.percentForApplication = percentForApplication;

        expenseOnGasoline = (averageExpenseGasoline / 100.0f * distanse * costOfFuel);
        income = earnedMoney - expenseOnGasoline;
        percentForApplication = 15;
        wastedMoneyOfPercent = earnedMoney / 100.0f * percentForApplication;
        profit = income >= 0 ? (income - wastedMoneyOfPercent) : 0;
    }

    public float getExpenseOnGasoline() {
        return expenseOnGasoline;
    }

    public float getIncome() {
        return income;
    }

    public float getPercentForApplication() {
        return percentForApplication;
    }

    public float getWastedMoneyOfPercent() {
        return wastedMoneyOfPercent;
    }

    public float getProfit() {
        return profit;
    }

    public CalculationTemporaryExpenseOfGasoline(float startDistance, float startExpenseGasoline){
        this.startDistance = startDistance;
        this.startExpenseGasoline = startExpenseGasoline;
    }

    /** if method returned float less zero, then we need initialize another
     * constructor or use overload method with start distance and start expense gasoline
     * @param currentDistance
     * @param currentExpenseGasoline
     * @return float current distance
     */
    public float getIntervalExpenseGasoline(float currentDistance, float currentExpenseGasoline){
        //get from start application shift taxi

        //what will return
        float intervalExpenseGasoline;

        try {
            intervalExpenseGasoline = ((currentExpenseGasoline * (startDistance +
                    currentDistance - startDistance) - startDistance * startExpenseGasoline)
                    / (currentDistance-startDistance));
        } catch (NullPointerException e) {
            return -1.0f;
        }
        return intervalExpenseGasoline;
    }

    public float getIntervalExpenseGasoline(float startDistance, float startExpenseGasoline,
                                            float currentDistance, float currentExpenseGasoline){
        //get from start application shift taxi

        //what will return
        float intervalExpenseGasoline;

        intervalExpenseGasoline = ((currentExpenseGasoline * (startDistance +
                currentDistance - startDistance) - startDistance * startExpenseGasoline)
                / (currentDistance-startDistance));
        return intervalExpenseGasoline;
    }
}
