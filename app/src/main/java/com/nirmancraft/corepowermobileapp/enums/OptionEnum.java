package com.nirmancraft.corepowermobileapp.enums;

/**
 * Created by Shreyas on 7/6/2014.
 */
public enum OptionEnum {
    SEARCH(1),BROWSE(2),CALL(3);

    private final int value;

    private OptionEnum(final int newValue) {
        value = newValue;
    }

    public int getValue(){return value;}
}
