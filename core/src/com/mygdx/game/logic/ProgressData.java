package com.mygdx.game.logic;

public final class ProgressData {
    private static int currentLevel, gapsCount, toReachNextLevel, currentExperience;
    private static String backgroundName;

    public static int getCurrentLevel() {
        return currentLevel;
    }

    public static int getGapsCount() {
        return gapsCount;
    }

    public static int getToReachNextLevel() {
        return toReachNextLevel;
    }

    public static String getBackgroundName() {
        return backgroundName;
    }

    public static int getCurrentExperience() {
        return currentExperience;
    }

    public static void addToCurrentExperience(int experienceGot) {
        currentExperience += experienceGot;
    }

    public static void levelUp() {

    }

    protected static void setCurrentLevel(int currentLevel) {
        ProgressData.currentLevel = currentLevel;
    }

    protected static void setGapsCount(int gapsCount) {
        ProgressData.gapsCount = gapsCount;
    }

    protected static void setToReachNextLevel(int toReachNextLevel) {
        ProgressData.toReachNextLevel = toReachNextLevel;
    }

    protected static void setCurrentExperience(int currentExperience) {
        ProgressData.currentExperience = currentExperience;
    }

    protected static void setBackgroundName(String backgroundName) {
        ProgressData.backgroundName = backgroundName;
    }
}
