package fuzs.openglider.wind;

import fuzs.openglider.OpenGlider;
import fuzs.openglider.config.ServerConfig;
import fuzs.openglider.wind.generator.OpenSimplexNoise;
import fuzs.openglider.api.world.item.Glider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;


/**
 * Applies "wind" effects to the player, moving them around when they fly.
 */
public class WindHelper {

    /** Simplex noise has a better "game feel" than Perlin (the standard for Minecraft), so ti is used here. */
    private static final OpenSimplexNoise NOISE_GENERATOR = new OpenSimplexNoise();

    /**
     * Apply wind effect, buffeting them around pseudo-randomly.
     * Affected by a variety of things, from player height to glider durability to weather, etc.
     *
     * @param player - the player to move around
     * @param glider - the hang glider item
     */
    public static void applyWind(Player player, ItemStack glider) {
        ServerConfig config = OpenGlider.CONFIG.get(ServerConfig.class);

        if (!config.wind.allowWind) return; //if no wind, then do nothing

        double windGustSize = config.wind.gustSize; //18;
        double windFrequency = config.wind.frequency; //0.15;
        double windRainingMultiplier = config.wind.rainingMultiplier; //4;
        double windSpeedMultiplier = config.wind.speedMultiplier; //0.4;
        double windHeightMultiplier = config.wind.heightMultiplier; //1.2;
        double windOverallPower = config.wind.overallPower; //1;

        //downscale for gust size/occurrence amount
        double noise = WindHelper.NOISE_GENERATOR.eval(player.getX() / windGustSize, player.getZ() / windGustSize); //occurrence amount

        //multiply by intensity factor (alter by multiplier if raining)
        noise *= player.level.isRaining() ? windRainingMultiplier * windFrequency : windFrequency;

        //stabilize somewhat depending on velocity
        double velocity = Math.sqrt(Math.pow(player.getDeltaMovement().x, 2) + Math.pow(player.getDeltaMovement().z, 2)); //player's velocity
        double speedStabilized = noise * 1/((velocity * windSpeedMultiplier) + 1); //stabilize somewhat with higher speeds

        //increase wind depending on world height
        double height = player.getY() < 256 ? (player.getY() / 256) * windHeightMultiplier : windHeightMultiplier; //world height clamp

        //apply stabilized speed with height
        double wind = speedStabilized * height;

        //apply durability modifier
        double additionalDamagePercentage = glider.isDamaged() ? config.wind.durabilityMultiplier * ((double)glider.getDamageValue() / (glider.getMaxDamage())) : 0; //1.x where x is the percent of durability used
        wind *= 1 + additionalDamagePercentage;

        //apply overall wind power multiplier
        wind *= windOverallPower;

        //apply tier specific wind power multiplier
        wind *= ((Glider) glider.getItem()).getWindMultiplier(glider);

        //apply final rotation based on all the above
        player.setYRot((float) (player.getYRot() + wind));
    }
}
