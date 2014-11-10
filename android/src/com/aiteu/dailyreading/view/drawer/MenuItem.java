package com.aiteu.dailyreading.view.drawer;

/**
 * menu item : icon, title, ...
 * @author liwei
 *
 */
public class MenuItem {
	public static int DEFAULT_ICON_VALUE = -1;

    private int id;
	private String text;
    private int icon = DEFAULT_ICON_VALUE;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
