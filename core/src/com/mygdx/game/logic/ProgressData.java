package com.mygdx.game.logic;


public final class ProgressData {
    private static int currentLevel;
    private static int toReachNextLevel;
    private static int currentExperience;
    private static int gapsCount;

    public static int getCurrentLevel() { return currentLevel; }
    public static int getToReachNextLevel() { return toReachNextLevel; }
    public static int getCurrentExperience() { return currentExperience; }
    public static int getGapsCount() { return gapsCount; }

    protected static void setCurrentLevel(int currentLevel) { ProgressData.currentLevel = currentLevel; }
    protected static void setToReachNextLevel(int toReachNextLevel) { ProgressData.toReachNextLevel = toReachNextLevel; }
    protected static void setCurrentExperience(int currentExperience) { ProgressData.currentExperience = currentExperience; }
    public static void setGapsCount(int gapsCount) { ProgressData.gapsCount = gapsCount; }

    public static void addToCurrentExperience(int experienceGot) { currentExperience += experienceGot; }

    public static void levelUp() { SaveUtils.loadNextLevel(); }
}
