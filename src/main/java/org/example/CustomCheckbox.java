package org.example;

import javafx.scene.control.CheckBox;

public class CustomCheckbox
{

    private CheckBox ch;

    public CheckBox getCheckbox()
    {
        return ch;
    }

    public CustomCheckbox createCheckbox()
    {
        ch = new CheckBox();
        return this;
    }

    public CustomCheckbox setCoordinates(double x, double y)
    {
        ch.setLayoutX(x);
        ch.setLayoutY(y);
        return this;
    }

    public CustomCheckbox setText(String text)
    {
        ch.setText(text);
        return this;
    }

}
