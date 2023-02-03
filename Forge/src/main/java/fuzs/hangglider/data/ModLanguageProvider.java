package fuzs.hangglider.data;

import fuzs.hangglider.HangGlider;
import fuzs.hangglider.init.ModRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {

    public ModLanguageProvider(DataGenerator gen, String modId) {
        super(gen, modId, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.add(ModRegistry.CREATIVE_MODE_TAB, HangGlider.MOD_NAME);
        this.add(ModRegistry.GLIDER_WING_ITEM.get(), "Glider Wing");
        this.add(ModRegistry.GLIDER_FRAMEWORK_ITEM.get(), "Glider Framework");
        this.add(ModRegistry.HANG_GLIDER_ITEM.get(), "Hang Glider");
        this.add(ModRegistry.REINFORCED_HANG_GLIDER_ITEM.get(), "Reinforced Hang Glider");
    }

    public void add(CreativeModeTab tab, String name) {
        this.add(((TranslatableContents) tab.getDisplayName().getContents()).getKey(), name);
    }
}
