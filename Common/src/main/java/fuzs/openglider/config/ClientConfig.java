package fuzs.openglider.config;

import fuzs.puzzleslib.config.ConfigCore;
import fuzs.puzzleslib.config.annotation.Config;

public class ClientConfig implements ConfigCore {
    @Config(description = "Enables rendering of the hang glider on the player in third-person perspective (or to others).")
    public boolean thirdPersonRendering = true;
    @Config(description = "Enables rendering of the hang glider above the player's head in first person perspective.")
    public boolean firstPersonRendering = true;
    @Config(description = "How high above the player's head the glider appears as in first person perspective while flying. Lower values will make it more visible/intrusive.")
    @Config.DoubleRange(min = 0.0, max = 4.0)
    public double firstPersonGliderHeight = 1.9;
    @Config(description = "How much the glider should shift visually when in fast/shift mode. 0 is none.")
    @Config.DoubleRange(min = 0.0, max = 1.0)
    public double firstPersonFastGliderShift = 0.05;
}
