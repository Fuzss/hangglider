package fuzs.hangglider.data;

import fuzs.hangglider.HangGlider;
import fuzs.hangglider.init.ModRegistry;
import fuzs.puzzleslib.api.data.v1.AbstractLanguageProvider;
import net.minecraftforge.data.event.GatherDataEvent;

public class ModLanguageProvider extends AbstractLanguageProvider {

    public ModLanguageProvider(GatherDataEvent evt, String modId) {
        super(evt, modId);
    }

    @Override
    protected void addTranslations() {
        this.addCreativeModeTab(HangGlider.MOD_NAME);
        this.add(ModRegistry.GLIDER_WING_ITEM.get(), "Glider Wing");
        this.add(ModRegistry.GLIDER_FRAMEWORK_ITEM.get(), "Glider Framework");
        this.add(ModRegistry.HANG_GLIDER_ITEM.get(), "Hang Glider");
        this.add(ModRegistry.REINFORCED_HANG_GLIDER_ITEM.get(), "Reinforced Hang Glider");
    }
}
