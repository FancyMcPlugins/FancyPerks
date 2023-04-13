package de.oliver.fancyperks.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;

public class MessageHelper {

    public static Component removeDecoration(Component component, TextDecoration decoration){
        return  component.decoration(decoration, TextDecoration.State.FALSE);
    }

}
