package com.mygdx.game.logic;

import java.util.HashMap;

public final class ProgressData {
    private static int currentLevel, gapsCount, toReachNextLevel, currentExperience, elementsCount, startNumber;
    private static String backgroundName;
    public final HashMap<Integer,Integer> LEVEL_LIMITS = new HashMap<>();

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

    public static int getElementsCount() { return elementsCount; }

    public static int getStartNumber() { return startNumber; }

    public static void addToCurrentExperience(int experienceGot) {
        currentExperience += experienceGot;
    }

    public static void levelUp() {
        SaveUtils.loadNextLevel();
    }

    protected static void setElementsCount(int elementsCount1) { elementsCount = elementsCount1; }

    protected static void setCurrentLevel(int currentLevel) { ProgressData.currentLevel = currentLevel; }

    protected static void setGapsCount(int gapsCount) {
        ProgressData.gapsCount = gapsCount;
    }

    protected static void setToReachNextLevel(int toReachNextLevel) { ProgressData.toReachNextLevel = toReachNextLevel; }

    protected static void setCurrentExperience(int currentExperience) { ProgressData.currentExperience = currentExperience; }

    public static void setStartNumber(int number) { startNumber = number; }

    protected static void setBackgroundName(String backgroundName) { ProgressData.backgroundName = backgroundName; }
}
