package com.tildapumkins.game.lab.labgame.detect;

public enum Direct {

    NONE(0),
    LEFT(1),
    TOP(2),
    RIGHT(4),
    BOTTOM(8);

    private int value;

    Direct(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Direct fromInt(int value) {
        switch (value) {
            case 1: return LEFT;
            case 2: return TOP;
            case 4: return RIGHT;
            case 8: return BOTTOM;
            default: return NONE;
        }
    }

    public boolean check(int value) {
        return (this.value & value) == this.value;
    }
}
