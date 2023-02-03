package fuzs.hangglider.config;

import fuzs.puzzleslib.config.ConfigCore;
import fuzs.puzzleslib.config.annotation.Config;

public class ClientConfig implements ConfigCore {
    @Config(description = "Tilt camera depending on gliding turn angle.")
    public boolean glidingCameraTilt = true;
    @Config(description = "Multiplier for camera tilt amount when gliding.")
    @Config.DoubleRange(min = 0.1, max = 1.0)
    public double glidingTiltAmount = 0.5;
    @Config(description = "Multiplier for camera tilt speed when gliding.")
    @Config.DoubleRange(min = 0.1, max = 1.0)
    public double glidingTiltSpeed = 0.4;
    @Config(description = "Auto-switch to third-person mode while gliding.")
    public boolean thirdPersonGliding = true;
}
