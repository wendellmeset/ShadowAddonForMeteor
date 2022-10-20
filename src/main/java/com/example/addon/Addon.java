package com.example.addon;

import com.example.addon.commands.CommandExample;
import com.example.addon.hud.HudExample;
import com.example.addon.modules.ModuleExample;
import com.mojang.logging.LogUtils;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.commands.Commands;
import meteordevelopment.meteorclient.systems.hud.Hud;
import meteordevelopment.meteorclient.systems.hud.HudGroup;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Addon extends MeteorAddon {
    public static final Logger LOG = LoggerFactory.getLogger("Meteor Shadow");
    public static final Category CATEGORY = new Category("Shadow", Items.NETHERITE_BLOCK.getDefaultStack());
    public static final HudGroup HUD_GROUP = new HudGroup("Shadow");

    @Override
    public void onInitialize() {
        LOG.info("Initializing Meteor Shadow Client Addon");

        // Modules
        Modules.get().add(new Annihilator());
        Modules.get().add(new AutoTNT());
        Modules.get().add(new AutoRun());
        Modules.get().add(new AutoIgnite());
        Modules.get().add(new AutoFireball());
        Modules.get().add(new MapDestroyer());
        Modules.get().add(new Decimator());
        Modules.get().add(new Explosion());
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY);
    }

    @Override
    public String getPackage() {
        return "com.example.addon";
    }
}
