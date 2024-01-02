package com.example.plugin;


import org.gradle.api.provider.Property;

interface Extension {
    Property<Boolean> getEnable();
}
