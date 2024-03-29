package edu.ktu.calorie_counter.Model;

public class CalorieCalculatorModel {

    int currentWeight;
    int targetWeight;
    int currentCalories;
    int targetCalories;


    public CalorieCalculatorModel(int currentWeight, int targetWeight, int currentCalories, int targetCalories) {
        this.currentWeight = currentWeight;
        this.targetWeight = targetWeight;
        this.currentCalories = currentCalories;
        this.targetCalories = targetCalories;
    }

    public CalorieCalculatorModel() {
    }

    public int getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(int currentWeight) {
        this.currentWeight = currentWeight;
    }

    public int getTargetWeight() {
        return targetWeight;
    }

    public void setTargetWeight(int targetWeight) {
        this.targetWeight = targetWeight;
    }

    public int getCurrentCalories() {
        return currentCalories;
    }

    public void setCurrentCalories(int currentCalories) {
        this.currentCalories = currentCalories;
    }

    public int getTargetCalories() {
        return targetCalories;
    }

    public void setTargetCalories(int targetCalories) {
        this.targetCalories = targetCalories;
    }
}
