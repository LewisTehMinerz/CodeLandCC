package dan200.computercraft.shared.peripheral.modem;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public interface IReceiver {

   int getChannel();

   World getWorld();

   Vec3d getWorldPosition();

   boolean isInterdimensional();

   double getReceiveRange();

   void receiveSameDimension(int var1, Object var2, double var3, Object var5);

   void receiveDifferentDimension(int var1, Object var2, Object var3);
}
