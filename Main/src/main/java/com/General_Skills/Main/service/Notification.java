package com.General_Skills.Main.service;


import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public abstract class Notification {

    /**
     * Code by Doryan BESSIERE
     * https://www.github.com/BDoryan
     * https://twitter.com/Doryqn
     */

    protected String title;
    protected String message;

    protected Image icon = Toolkit.getDefaultToolkit().createImage("blank.png");;
    protected String tooltip;
    protected TrayIcon.MessageType type = TrayIcon.MessageType.NONE;

    private TrayIcon trayIcon = null;

    public Notification(String title, String message) {
        this.title = title;
        this.message = message;

        if(tooltip != null)
            trayIcon = new TrayIcon(icon, tooltip);
        else
            trayIcon = new TrayIcon(icon);
        addActionListener(event -> onAction());

        trayIcon.setImageAutoSize(true);

    }

    public Notification(String title, String message, TrayIcon.MessageType type) {
        this(title, message, null, null, type);
    }

    public Notification(String title, String message, Image icon) {
        this(title, message, icon, null);
    }

    public Notification(String title, String message, Image icon, String tooltip) {
        this(title, message, icon, tooltip, null);
    }

    public Notification(String title, String message, Image icon, String tooltip, TrayIcon.MessageType type) {
        this(title, message);
        this.tooltip = tooltip;

        if(icon != null)
            this.icon = icon;

        if(type != null)
            this.type = type;
    }

    /**
     * Allows you to check if you can use this feature
     *
     * @return
     */
    public static boolean isSupported(){
        return SystemTray.isSupported();
    }

    /**
     *
     * This method allows you to display (launch) your notification
     *
     * @return
     * @throws AWTException
     */
    public boolean push() throws AWTException {
        if (isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();

            tray.add(trayIcon);

            trayIcon.displayMessage(title, message, type);
            return true;
        } else {
            System.err.println("System tray not supported, you can't push notification !");
        }
        return false;
    }

    public void addMouseListener(MouseListener listener){
        trayIcon.addMouseListener(listener);
    }

    public void removeMouseListener(MouseListener listener){
        trayIcon.addMouseListener(listener);
    }

    public void addMouseMotionListener(MouseMotionListener listener){
        trayIcon.addMouseMotionListener(listener);
    }

    public void removeMouseMotionListener(MouseMotionListener listener){
        trayIcon.removeMouseMotionListener(listener);
    }

    public void addActionListener(ActionListener listener){
        trayIcon.addActionListener(listener);
    }

    public void removeActionListener(ActionListener listener){
        trayIcon.removeActionListener(listener);
    }

    public abstract void onAction();
}
