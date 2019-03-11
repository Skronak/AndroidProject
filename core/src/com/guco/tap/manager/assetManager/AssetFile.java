package com.guco.tap.manager.assetManager;

public class AssetFile {

    public String path;
    public Class type;

    public AssetFile(String path, Class type) {
        this.path = path;
        this.type = type;
    }
}