package com.snedan.studentassist;

import android.content.DialogInterface;

public class Card
{
    private Integer line1;
    private String line2;

    public Card(Integer line1, String line2) {
        this.line1 = line1;
        this.line2 = line2;
    }

    public Integer getLine1() {
        return line1;
    }

    public String getLine2() {
        return line2;
    }


}
