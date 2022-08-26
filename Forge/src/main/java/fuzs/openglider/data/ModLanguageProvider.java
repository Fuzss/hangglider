package fuzs.openglider.data;

import fuzs.openglider.OpenGlider;
import fuzs.openglider.init.ModRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {

    public ModLanguageProvider(DataGenerator gen, String modId) {
        super(gen, modId, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.add("itemGroup.openglider.main", OpenGlider.MOD_NAME);
        this.add(ModRegistry.GLIDER_WING_ITEM.get(), "Glider Wing");
        this.add(ModRegistry.GLIDER_FRAMEWORK_ITEM.get(), "Glider Framework");
        this.add(ModRegistry.HANG_GLIDER_ITEM.get(), "Hang Glider");
        this.add(ModRegistry.REINFORCED_HANG_GLIDER_ITEM.get(), "Reinforced Hang Glider");
    }
}
