package com.example.addon.modules;

import com.example.addon.Addon;
import meteordevelopment.meteorclient.systems.modules.Module;

public class ModuleExampleThingy extends Module {
    public ModuleExample() {
        super(Addon.CATEGORY, "example", "An example module in a custom category.");
    }
}
