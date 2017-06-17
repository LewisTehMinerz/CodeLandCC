package dan200.computercraft.shared.peripheral.modem;

import dan200.computercraft.shared.peripheral.modem.IReceiver;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public interface INetwork {

   void addReceiver(IReceiver var1);

   void removeReceiver(IReceiver var1);

   void transmit(int var1, int var2, Object var3, World var4, Vec3d var5, double var6, boolean var8, Object var9);

   boolean isWireless();
}
