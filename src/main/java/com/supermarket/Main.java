package com.supermarket;

import com.supermarket.presentation.App;

public class Main {
    public static void main(String[] args) {
        // Delegate control to the App class, which extends Application
        App.launchApp(args);
    }
}