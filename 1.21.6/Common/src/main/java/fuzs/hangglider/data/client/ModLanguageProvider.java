package fuzs.hangglider.data.client;

import fuzs.hangglider.HangGlider;
import fuzs.hangglider.init.ModRegistry;
import fuzs.puzzleslib.api.client.data.v2.AbstractLanguageProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;

public class ModLanguageProvider extends AbstractLanguageProvider {

    public ModLanguageProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addTranslations(TranslationBuilder builder) {
        builder.add(ModRegistry.CREATIVE_MODE_TAB.value(), HangGlider.MOD_NAME);
        builder.add(ModRegistry.GLIDER_WING_ITEM.value(), "Glider Wing");
        builder.add(ModRegistry.GLIDER_FRAMEWORK_ITEM.value(), "Glider Framework");
        builder.add(ModRegistry.HANG_GLIDER_ITEM.value(), "Hang Glider");
        builder.add(ModRegistry.REINFORCED_HANG_GLIDER_ITEM.value(), "Reinforced Hang Glider");
    }
}
