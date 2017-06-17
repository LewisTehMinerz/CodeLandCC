package dan200.computercraft.client.render;

import java.util.ArrayList;
import java.util.List;
import javax.vecmath.Matrix4f;
import javax.vecmath.Point3f;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.client.renderer.vertex.VertexFormatElement.EnumType;
import net.minecraft.util.EnumFacing;

public class TurtleMultiModel implements IBakedModel {

   private IBakedModel m_baseModel;
   private IBakedModel m_overlayModel;
   private IBakedModel m_leftUpgradeModel;
   private Matrix4f m_leftUpgradeTransform;
   private IBakedModel m_rightUpgradeModel;
   private Matrix4f m_rightUpgradeTransform;
   private List m_generalQuads;
   private List[] m_faceQuads;


   public TurtleMultiModel(IBakedModel baseModel, IBakedModel overlayModel, IBakedModel leftUpgradeModel, Matrix4f leftUpgradeTransform, IBakedModel rightUpgradeModel, Matrix4f rightUpgradeTransform) {
      this.m_baseModel = baseModel;
      this.m_overlayModel = overlayModel;
      this.m_leftUpgradeModel = leftUpgradeModel;
      this.m_leftUpgradeTransform = leftUpgradeTransform;
      this.m_rightUpgradeModel = rightUpgradeModel;
      this.m_rightUpgradeTransform = rightUpgradeTransform;
      this.m_generalQuads = null;
      this.m_faceQuads = new List[6];
   }

   public List func_188616_a(IBlockState state, EnumFacing side, long rand) {
      if(side != null) {
         if(this.m_faceQuads[side.ordinal()] == null) {
            ArrayList quads = new ArrayList();
            if(this.m_overlayModel != null) {
               quads.addAll(this.m_overlayModel.func_188616_a(state, side, rand));
            }

            if(this.m_leftUpgradeModel != null) {
               quads.addAll(this.transformQuads(this.m_leftUpgradeModel.func_188616_a(state, side, rand), this.m_leftUpgradeTransform));
            }

            if(this.m_rightUpgradeModel != null) {
               quads.addAll(this.transformQuads(this.m_rightUpgradeModel.func_188616_a(state, side, rand), this.m_rightUpgradeTransform));
            }

            this.m_faceQuads[side.ordinal()] = quads;
         }

         return this.m_faceQuads[side.ordinal()];
      } else {
         if(this.m_generalQuads == null) {
            this.m_generalQuads = new ArrayList();
            this.m_generalQuads.addAll(this.m_baseModel.func_188616_a(state, side, rand));
            if(this.m_overlayModel != null) {
               this.m_generalQuads.addAll(this.m_overlayModel.func_188616_a(state, side, rand));
            }

            if(this.m_leftUpgradeModel != null) {
               this.m_generalQuads.addAll(this.transformQuads(this.m_leftUpgradeModel.func_188616_a(state, side, rand), this.m_leftUpgradeTransform));
            }

            if(this.m_rightUpgradeModel != null) {
               this.m_generalQuads.addAll(this.transformQuads(this.m_rightUpgradeModel.func_188616_a(state, side, rand), this.m_rightUpgradeTransform));
            }
         }

         return this.m_generalQuads;
      }
   }

   public boolean func_177555_b() {
      return this.m_baseModel.func_177555_b();
   }

   public boolean func_177556_c() {
      return this.m_baseModel.func_177556_c();
   }

   public boolean func_188618_c() {
      return this.m_baseModel.func_188618_c();
   }

   public TextureAtlasSprite func_177554_e() {
      return this.m_baseModel.func_177554_e();
   }

   public ItemCameraTransforms func_177552_f() {
      return this.m_baseModel.func_177552_f();
   }

   public ItemOverrideList func_188617_f() {
      return ItemOverrideList.field_188022_a;
   }

   private List transformQuads(List input, Matrix4f transform) {
      if(transform != null && input.size() != 0) {
         ArrayList output = new ArrayList(input.size());

         for(int i = 0; i < input.size(); ++i) {
            BakedQuad quad = (BakedQuad)input.get(i);
            output.add(this.transformQuad(quad, transform));
         }

         return output;
      } else {
         return input;
      }
   }

   private BakedQuad transformQuad(BakedQuad quad, Matrix4f transform) {
      int[] vertexData = (int[])quad.func_178209_a().clone();
      int offset = 0;
      BakedQuad copy = new BakedQuad(vertexData, quad.func_178211_c(), quad.func_178210_d(), quad.func_187508_a(), quad.shouldApplyDiffuseLighting(), quad.getFormat());
      VertexFormat format = copy.getFormat();

      for(int i = 0; i < format.func_177345_h(); ++i) {
         VertexFormatElement element = format.func_177348_c(i);
         if(element.func_177374_g() && element.func_177367_b() == EnumType.FLOAT && element.func_177370_d() == 3) {
            for(int j = 0; j < 4; ++j) {
               int start = offset + j * format.func_177338_f();
               if(start % 4 == 0) {
                  start /= 4;
                  Point3f pos = new Point3f(Float.intBitsToFloat(vertexData[start]), Float.intBitsToFloat(vertexData[start + 1]), Float.intBitsToFloat(vertexData[start + 2]));
                  transform.transform(pos);
                  vertexData[start] = Float.floatToRawIntBits(pos.x);
                  vertexData[start + 1] = Float.floatToRawIntBits(pos.y);
                  vertexData[start + 2] = Float.floatToRawIntBits(pos.z);
               }
            }
         }

         offset += element.func_177368_f();
      }

      return copy;
   }
}
