package com.guco.tap.manager.assetManager;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.HashMap;

public class Assets {

    private static final AssetManager assetManager = new AssetManager();
    private static HashMap<String, AssetDescriptor<?>> files;

    /**
     * Enter all files from the assets folder in here
     */
    public static void load(){

        files = new HashMap<String, AssetDescriptor<?>>();
        //TextureAtlases
       // files.put("backgroundTextureAtlas", new AssetFile("sprites/background.atlas", TextureAtlas.class));
       // files.put("entitiesTextureAtlas", new AssetFile("sprites/entities.atlas", TextureAtlas.class));
       // files.put("uiTextureAtlas", new AssetFile("sprites/ui.atlas", TextureAtlas.class));

        //Skins
        files.put("defaultSkin", new AssetDescriptor<Skin>("ui/uiskin.json", Skin.class));

        // Texture
        files.put("rockValley.png", new AssetDescriptor<Texture>("sprites/background/rockValley.png", Texture.class));

        //I18Ns
        //files.put("defaultI18N", new AssetFile("i18N/prototype", I18NBundle.class));

        //Loading files
        for(AssetDescriptor asset : files.values()){
            assetManager.load(asset.fileName, asset.type);
        }

        assetManager.finishLoading();
    }

    public static Object get(String hashmapKey){
        return assetManager.get(files.get(hashmapKey).fileName, files.get(hashmapKey).type);
    }

    public static void dispose(){
        assetManager.dispose();
    }
}