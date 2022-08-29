package fuzs.openglider.client.handler;

public class ElytraEquippedHandler {
    private int tickTime;

    public void onClientTick() {
        if (this.tickTime > 0) this.tickTime--;
    }
}
