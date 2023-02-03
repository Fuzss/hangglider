package fuzs.hangglider.api.client.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;

import java.util.Optional;

public class ComputeCameraAngleEvents {
    public static final Event<CameraAngle> PITCH = EventFactory.createArrayBacked(CameraAngle.class, listeners -> (GameRenderer renderer, Camera camera, float tickDelta) -> {
        for (CameraAngle event : listeners) {
            Optional<Float> result = event.onComputeCameraAngle(renderer, camera, tickDelta);
            if (result.isPresent()) return result;
        }
        return Optional.empty();
    });
    public static final Event<CameraAngle> YAW = EventFactory.createArrayBacked(CameraAngle.class, listeners -> (GameRenderer renderer, Camera camera, float tickDelta) -> {
        for (CameraAngle event : listeners) {
            Optional<Float> result = event.onComputeCameraAngle(renderer, camera, tickDelta);
            if (result.isPresent()) return result;
        }
        return Optional.empty();
    });
    public static final Event<CameraAngle> ROLL = EventFactory.createArrayBacked(CameraAngle.class, listeners -> (GameRenderer renderer, Camera camera, float tickDelta) -> {
        for (CameraAngle event : listeners) {
            Optional<Float> result = event.onComputeCameraAngle(renderer, camera, tickDelta);
            if (result.isPresent()) return result;
        }
        return Optional.empty();
    });

    @FunctionalInterface
    public interface CameraAngle {

        /**
         * Runs before camera angle setup is done, allows for additional control over roll in addition to pitch and yaw.
         *
         * @param renderer the game renderer instance
         * @param camera    the camera instance
         * @param tickDelta partial ticks
         * @return  potential new angle for current axis
         */
        Optional<Float> onComputeCameraAngle(GameRenderer renderer, Camera camera, float tickDelta);
    }
}
