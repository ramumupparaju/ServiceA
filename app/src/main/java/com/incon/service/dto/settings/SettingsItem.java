package com.incon.service.dto.settings;


/**
 * Created on 09 Aug 2017 7:16 PM.
 *
 */
public class SettingsItem extends ListRowType {
    private int icon;
    private String text;

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public int getIcon() {
        return icon;
    }
    public void setIcon(int icon) {
        this.icon = icon;
    }

}
