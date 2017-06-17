package dan200.computercraft.shared.computer.blocks;

import dan200.computercraft.ComputerCraft;
import dan200.computercraft.shared.computer.blocks.BlockComputer;
import dan200.computercraft.shared.computer.blocks.TileComputerBase;
import dan200.computercraft.shared.computer.core.ComputerFamily;
import dan200.computercraft.shared.computer.core.IComputer;
import dan200.computercraft.shared.computer.core.ServerComputer;
import dan200.computercraft.shared.computer.items.ComputerItemFactory;
import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;

public class TileComputer extends TileComputerBase {

   private static final int[] s_remapSide = new int[]{0, 1, 2, 3, 5, 4};


   protected ServerComputer createComputer(int instanceID, int id) {
      ComputerFamily family = this.getFamily();
      ServerComputer computer = new ServerComputer(this.field_145850_b, id, this.m_label, instanceID, family, 51, 19);
      computer.setPosition(this.func_174877_v());
      return computer;
   }

   public void getDroppedItems(List drops, boolean creative) {
      IComputer computer = this.getComputer();
      if(!creative || computer != null && computer.getLabel() != null) {
         drops.add(ComputerItemFactory.create(this));
      }

   }

   public ItemStack getPickedItem() {
      return ComputerItemFactory.create(this);
   }

   public void openGUI(EntityPlayer player) {
      ComputerCraft.openComputerGUI(player, this);
   }

   public final void readDescription(NBTTagCompound nbttagcompound) {
      super.readDescription(nbttagcompound);
      this.updateBlock();
   }

   public boolean isUseableByPlayer(EntityPlayer player) {
      return this.isUsable(player, false);
   }

   public EnumFacing getDirection() {
      IBlockState state = this.getBlockState();
      return (EnumFacing)state.func_177229_b(BlockComputer.Properties.FACING);
   }

   public void setDirection(EnumFacing dir) {
      if(dir.func_176740_k() == Axis.Y) {
         dir = EnumFacing.NORTH;
      }

      this.setBlockState(this.getBlockState().func_177226_a(BlockComputer.Properties.FACING, dir));
      this.updateInput();
   }

   protected int remapLocalSide(int localSide) {
      return s_remapSide[localSide];
   }

}
