package fuzs.openglider.config;

import fuzs.openglider.world.item.GliderMaterial;
import fuzs.puzzleslib.config.ConfigCore;
import fuzs.puzzleslib.config.annotation.Config;

public class ServerConfig implements ConfigCore {
    @Config
    public GliderConfig hangGlider = new GliderConfig();
    @Config
    public GliderConfig reinforcedHangGlider = new GliderConfig();
    @Config
    public WindConfig wind = new WindConfig();

    public ServerConfig() {
        this.reinforcedHangGlider.horizSpeed = 0.04;
        this.reinforcedHangGlider.shiftHorizSpeed = 0.08;
        this.reinforcedHangGlider.windModifier = 0.75;
        this.reinforcedHangGlider.airResistance = 0.99;
    }

    public static class GliderConfig implements ConfigCore, GliderMaterial {
        @Config(name = "normal_forward_movement", description = "The amount of blocks to move forwards (per-tick) while gliding normally.")
        @Config.DoubleRange(min = 0.0, max = 100.0)
        public double horizSpeed = 0.025;
        @Config(name = "normal_fall_movement", description = "The amount of blocks a player falls (per-tick) while gliding normally.")
        @Config.DoubleRange(min = 0.0, max = 100.0)
        public double vertSpeed = 0.55;
        @Config(name = "fast_forward_movement", description = "The amount of blocks to move forwards (per-tick) while gliding fast (pressing 'Shift').")
        @Config.DoubleRange(min = 0.0, max = 100.0)
        public double shiftHorizSpeed = 0.05;
        @Config(name = "fast_fall_movement", description = "The amount of blocks to fall (per-tick) while gliding fast (pressing 'Shift').")
        @Config.DoubleRange(min = 0.0, max = 100.0)
        public double shiftVertSpeed = 0.675;
        @Config(name = "overall_wind_power", description = {"A quality-of-life option to quickly change the overall power of the wind effect for this glider. Default is an overall relatively weak wind, with moderate gusts that occur semi-commonly.", "Note that this value can be a decimal (i.e. 0.5 would be half as strong). More fine-grained options are available in the 'wind' section of this config."})
        @Config.DoubleRange(min = 0.001, max = 10.0)
        public double windModifier = 1.4;
        @Config(name = "air_resistance", description = "The rate at which air resistance hinders your movement. 1 is no resistance, 0.5 is 1/2 as fast each tick.")
        @Config.DoubleRange(min = 0.0, max = 1.0)
        public double airResistance = 0.985;
        @Config(description = "Enables durability usage of the hang glider when gliding.")
        public boolean consumeDurability = true;
        @Config(description = {"The timeframe for durability usage, in ticks. Recall that there are 20 ticks in a second, so a value of 20 would damage the item about once a second.", "Default is 1 damage about every 10 seconds of flight, so with a durability of 618 means about 15 minutes of flight time with an undamaged glider."})
        @Config.IntRange(min = 1, max = 10_000)
        public int durabilityUseInterval = 200;

        @Override
        public double getHorizontalFlightSpeed() {
            return this.horizSpeed;
        }

        @Override
        public double getVerticalFlightSpeed() {
            return this.vertSpeed;
        }

        @Override
        public double getShiftHorizontalFlightSpeed() {
            return this.shiftHorizSpeed;
        }

        @Override
        public double getShiftVerticalFlightSpeed() {
            return this.shiftVertSpeed;
        }

        @Override
        public double getWindMultiplier() {
            return this.windModifier;
        }

        @Override
        public double getAirResistance() {
            return this.airResistance;
        }

        @Override
        public boolean consumesDurability() {
            return this.consumeDurability;
        }

        @Override
        public int getDurabilityUseInterval() {
            return this.durabilityUseInterval;
        }
    }

    public static class WindConfig implements ConfigCore {
        @Config(description = "Enables air resistance, making the player slow down over time when flying. Values conditional on tier of glider.")
        public boolean allowAirResistance = true;
        @Config(description = "Enables wind, making the player move unpredictably around when gliding.")
        public boolean allowWind = true;
        @Config(description = "A quality-of-life option to quickly change the overall power of the wind effect for all gliders. Default is an overall relatively weak wind, with moderate gusts that occur semi-commonly. Note that this value can be a decimal (i.e. 0.5 would be half as strong). More fine-grained options are available below.")
        @Config.DoubleRange(min = 0.001, max = 10.0)
        public double overallPower = 1.0;
        @Config(description = "The size of the wind gusts, larger values mean the gusts push the player around in greater angles from their intended direction. Default is moderately sized. Observable gameplay effects are highly tied with wind frequency.")
        @Config.DoubleRange(min = 1.0, max = 100.0)
        public double gustSize = 19.0;
        @Config(description = "The frequency of the wind gusts, larger values mean the wind effects occur more often. 0 removes wind. Default is semi-common. Observable gameplay effects are highly tied with gust size.")
        @Config.DoubleRange(min = 0.0, max = 5.0)
        public double frequency = 0.15;
        @Config(description = "How much stronger the wind should be while it is raining. 1 means the wind is the same if raining or not, 10 means the wind is 10x stronger while it is raining.")
        @Config.DoubleRange(min = 1.0, max = 10.0)
        public double rainingMultiplier = 3.0;
        @Config(description = "When going fast, the overall wind effect is changed by this multiplier. Default is that going fast reduces the wind effect by a moderate amount. 0 means the player's speed has no effect on the wind.")
        @Config.DoubleRange(min = -10.0, max = 10.0)
        public double speedMultiplier = 0.4;
        @Config(description = "The player's y-level/height changes the overall wind effect by this multiplier. Default is that the higher you are up in the world the stronger the wind is, but only by a moderate amount. 0 means the player's height has no effect on the wind.")
        @Config.DoubleRange(min = -10.0, max = 10.0)
        public double heightMultiplier = 1.5;
        @Config(description = "The glider's durability remaining changes the overall wind effect by this additional amount. 0 means the glider's durability won't effect the wind power, whereas 1 will mean a nearly broken glider is affected by wind about twice as much as a new one.")
        @Config.DoubleRange(min = 0.0, max = 5.0)
        public double durabilityMultiplier = 0.7;
    }
}
