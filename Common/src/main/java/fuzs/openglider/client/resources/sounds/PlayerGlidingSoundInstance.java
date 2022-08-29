package fuzs.openglider.client.resources.sounds;

import fuzs.openglider.capability.GlidingPlayerCapability;
import fuzs.openglider.init.ModRegistry;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class PlayerGlidingSoundInstance extends AbstractTickableSoundInstance {
   private final Player player;
   private int time;

   public PlayerGlidingSoundInstance(Player pPlayer) {
      super(SoundEvents.ELYTRA_FLYING, SoundSource.PLAYERS, SoundInstance.createUnseededRandom());
      this.player = pPlayer;
      this.looping = true;
      this.delay = 0;
      this.volume = 0.1F;
   }

   public void tick() {
      ++this.time;
      if (!this.player.isRemoved() && (this.time <= 20 || ModRegistry.GLIDING_PLAYER_CAPABILITY.maybeGet(this.player)
              .map(GlidingPlayerCapability::getIsPlayerGliding)
              .orElse(false))) {
         this.x = (float)this.player.getX();
         this.y = (float)this.player.getY();
         this.z = (float)this.player.getZ();
         float f = (float)this.player.getDeltaMovement().lengthSqr();
         if ((double)f >= 1.0E-7D) {
            this.volume = Mth.clamp(f / 4.0F, 0.0F, 1.0F);
         } else {
            this.volume = 0.0F;
         }

         if (this.time < 20) {
            this.volume = 0.0F;
         } else if (this.time < 40) {
            this.volume *= (float)(this.time - 20) / 20.0F;
         }

         float f1 = 0.8F;
         if (this.volume > 0.8F) {
            this.pitch = 1.0F + (this.volume - 0.8F);
         } else {
            this.pitch = 1.0F;
         }

      } else {
         this.stop();
      }
   }
}