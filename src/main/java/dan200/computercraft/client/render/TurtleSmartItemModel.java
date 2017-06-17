package dan200.computercraft.client.render;

import com.google.common.base.Objects;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.ITurtleUpgrade;
import dan200.computercraft.api.turtle.TurtleSide;
import dan200.computercraft.client.render.TileEntityTurtleRenderer;
import dan200.computercraft.client.render.TurtleMultiModel;
import dan200.computercraft.shared.computer.core.ComputerFamily;
import dan200.computercraft.shared.turtle.items.ItemTurtleBase;
import dan200.computercraft.shared.turtle.items.TurtleItemFactory;
import dan200.computercraft.shared.util.Colour;
import dan200.computercraft.shared.util.Holiday;
import dan200.computercraft.shared.util.HolidayUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.vecmath.Matrix4f;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;

public class TurtleSmartItemModel implements IBakedModel, IResourceManagerReloadListener {

   private ItemStack m_defaultItem;
   private HashMap m_cachedModels;
   private ItemOverrideList m_overrides;


   public TurtleSmartItemModel() {
      this.m_defaultItem = TurtleItemFactory.create(-1, (String)null, (Colour)null, ComputerFamily.Normal, (ITurtleUpgrade)null, (ITurtleUpgrade)null, 0, (ResourceLocation)null);
      this.m_cachedModels = new HashMap();
      this.m_overrides = new ItemOverrideList(new ArrayList()) {
         public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {
            ItemTurtleBase turtle = (ItemTurtleBase)stack.func_77973_b();
            ComputerFamily family = turtle.getFamily(stack);
            Colour colour = turtle.getColour(stack);
            ITurtleUpgrade leftUpgrade = turtle.getUpgrade(stack, TurtleSide.Left);
            ITurtleUpgrade rightUpgrade = turtle.getUpgrade(stack, TurtleSide.Right);
            ResourceLocation overlay = turtle.getOverlay(stack);
            boolean christmas = HolidayUtil.getCurrentHoliday() == Holiday.Christmas;
            TurtleSmartItemModel.TurtleModelCombination combo = new TurtleSmartItemModel.TurtleModelCombination(family, colour, leftUpgrade, rightUpgrade, overlay, christmas);
            if(TurtleSmartItemModel.this.m_cachedModels.containsKey(combo)) {
               return (IBakedModel)TurtleSmartItemModel.this.m_cachedModels.get(combo);
            } else {
               IBakedModel model = TurtleSmartItemModel.this.buildModel(combo);
               TurtleSmartItemModel.this.m_cachedModels.put(combo, model);
               return model;
            }
         }
      };
   }

   public ItemOverrideList func_188617_f() {
      return this.m_overrides;
   }

   public void func_110549_a(IResourceManager resourceManager) {
      this.m_cachedModels.clear();
   }

   private IBakedModel buildModel(TurtleSmartItemModel.TurtleModelCombination combo) {
      Minecraft mc = Minecraft.func_71410_x();
      ModelManager modelManager = mc.func_175599_af().func_175037_a().func_178083_a();
      ModelResourceLocation baseModelLocation = TileEntityTurtleRenderer.getTurtleModel(combo.m_family, combo.m_colour);
      ModelResourceLocation overlayModelLocation = TileEntityTurtleRenderer.getTurtleOverlayModel(combo.m_family, combo.m_overlay, combo.m_christmas);
      IBakedModel baseModel = modelManager.func_174953_a(baseModelLocation);
      IBakedModel overlayModel = overlayModelLocation != null?modelManager.func_174953_a(baseModelLocation):null;
      Pair leftModel = combo.m_leftUpgrade != null?combo.m_leftUpgrade.getModel((ITurtleAccess)null, TurtleSide.Left):null;
      Pair rightModel = combo.m_rightUpgrade != null?combo.m_rightUpgrade.getModel((ITurtleAccess)null, TurtleSide.Right):null;
      return (IBakedModel)(leftModel != null && rightModel != null?new TurtleMultiModel(baseModel, overlayModel, (IBakedModel)leftModel.getLeft(), (Matrix4f)leftModel.getRight(), (IBakedModel)rightModel.getLeft(), (Matrix4f)rightModel.getRight()):(leftModel != null?new TurtleMultiModel(baseModel, overlayModel, (IBakedModel)leftModel.getLeft(), (Matrix4f)leftModel.getRight(), (IBakedModel)null, (Matrix4f)null):(rightModel != null?new TurtleMultiModel(baseModel, overlayModel, (IBakedModel)null, (Matrix4f)null, (IBakedModel)rightModel.getLeft(), (Matrix4f)rightModel.getRight()):(overlayModel != null?new TurtleMultiModel(baseModel, overlayModel, (IBakedModel)null, (Matrix4f)null, (IBakedModel)null, (Matrix4f)null):baseModel))));
   }

   public List func_188616_a(IBlockState state, EnumFacing facing, long rand) {
      return this.getDefaultModel().func_188616_a(state, facing, rand);
   }

   public boolean func_177555_b() {
      return this.getDefaultModel().func_177555_b();
   }

   public boolean func_177556_c() {
      return this.getDefaultModel().func_177556_c();
   }

   public boolean func_188618_c() {
      return this.getDefaultModel().func_188618_c();
   }

   public TextureAtlasSprite func_177554_e() {
      return this.getDefaultModel().func_177554_e();
   }

   public ItemCameraTransforms func_177552_f() {
      return this.getDefaultModel().func_177552_f();
   }

   private IBakedModel getDefaultModel() {
      return this.m_overrides.handleItemState(this, this.m_defaultItem, (World)null, (EntityLivingBase)null);
   }

   private static class TurtleModelCombination {

      public final ComputerFamily m_family;
      public final Colour m_colour;
      public final ITurtleUpgrade m_leftUpgrade;
      public final ITurtleUpgrade m_rightUpgrade;
      public final ResourceLocation m_overlay;
      public final boolean m_christmas;


      public TurtleModelCombination(ComputerFamily family, Colour colour, ITurtleUpgrade leftUpgrade, ITurtleUpgrade rightUpgrade, ResourceLocation overlay, boolean christmas) {
         this.m_family = family;
         this.m_colour = colour;
         this.m_leftUpgrade = leftUpgrade;
         this.m_rightUpgrade = rightUpgrade;
         this.m_overlay = overlay;
         this.m_christmas = christmas;
      }

      public boolean equals(Object other) {
         if(other == this) {
            return true;
         } else {
            if(other instanceof TurtleSmartItemModel.TurtleModelCombination) {
               TurtleSmartItemModel.TurtleModelCombination otherCombo = (TurtleSmartItemModel.TurtleModelCombination)other;
               if(otherCombo.m_family == this.m_family && otherCombo.m_colour == this.m_colour && otherCombo.m_leftUpgrade == this.m_leftUpgrade && otherCombo.m_rightUpgrade == this.m_rightUpgrade && Objects.equal(otherCombo.m_overlay, this.m_overlay) && otherCombo.m_christmas == this.m_christmas) {
                  return true;
               }
            }

            return false;
         }
      }

      public int hashCode() {
         boolean prime = true;
         byte result = 1;
         int result1 = 31 * result + this.m_family.hashCode();
         result1 = 31 * result1 + (this.m_colour != null?this.m_colour.hashCode():0);
         result1 = 31 * result1 + (this.m_leftUpgrade != null?this.m_leftUpgrade.hashCode():0);
         result1 = 31 * result1 + (this.m_rightUpgrade != null?this.m_rightUpgrade.hashCode():0);
         result1 = 31 * result1 + (this.m_overlay != null?this.m_overlay.hashCode():0);
         result1 = 31 * result1 + (this.m_christmas?1:0);
         return result1;
      }
   }
}
