package dan200.computercraft.client.render;

import dan200.computercraft.ComputerCraft;
import dan200.computercraft.client.gui.FixedWidthFontRenderer;
import dan200.computercraft.core.terminal.Terminal;
import dan200.computercraft.core.terminal.TextBuffer;
import dan200.computercraft.shared.common.ClientTerminal;
import dan200.computercraft.shared.peripheral.monitor.TileMonitor;
import dan200.computercraft.shared.util.Colour;
import dan200.computercraft.shared.util.DirectionUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

public class TileEntityMonitorRenderer extends TileEntitySpecialRenderer {

   public void renderTileEntityAt(TileMonitor tileEntity, double posX, double posY, double posZ, float f, int i) {
      if(tileEntity != null) {
         this.renderMonitorAt(tileEntity, posX, posY, posZ, f, i);
      }

   }

   private void renderMonitorAt(TileMonitor monitor, double posX, double posY, double posZ, float f, int i) {
      TileMonitor origin = monitor.getOrigin();
      if(origin != null) {
         long renderFrame = ComputerCraft.getRenderFrame();
         if(origin.m_lastRenderFrame != renderFrame) {
            origin.m_lastRenderFrame = renderFrame;
            boolean redraw = origin.pollChanged();
            BlockPos monitorPos = monitor.func_174877_v();
            BlockPos originPos = origin.func_174877_v();
            posX += (double)(originPos.func_177958_n() - monitorPos.func_177958_n());
            posY += (double)(originPos.func_177956_o() - monitorPos.func_177956_o());
            posZ += (double)(originPos.func_177952_p() - monitorPos.func_177952_p());
            EnumFacing dir = origin.getDirection();
            EnumFacing front = origin.getFront();
            float yaw = DirectionUtil.toYawAngle(dir);
            float pitch = DirectionUtil.toPitchAngle(front);
            GlStateManager.func_179094_E();

            try {
               GlStateManager.func_179137_b(posX + 0.5D, posY + 0.5D, posZ + 0.5D);
               GlStateManager.func_179114_b(-yaw, 0.0F, 1.0F, 0.0F);
               GlStateManager.func_179114_b(pitch, 1.0F, 0.0F, 0.0F);
               GlStateManager.func_179137_b(-0.34375D, (double)origin.getHeight() - 0.5D - 0.15625D, 0.5D);
               double xSize = (double)origin.getWidth() - 0.3125D;
               double ySize = (double)origin.getHeight() - 0.3125D;
               Minecraft mc = Minecraft.func_71410_x();
               Tessellator tessellator = Tessellator.func_178181_a();
               VertexBuffer renderer = tessellator.func_178180_c();
               ClientTerminal clientTerminal = (ClientTerminal)origin.getTerminal();
               Terminal terminal = clientTerminal != null?clientTerminal.getTerminal():null;
               redraw = redraw || clientTerminal != null && clientTerminal.hasTerminalChanged();
               GlStateManager.func_179132_a(false);
               GlStateManager.func_179140_f();

               Colour var125;
               try {
                  if(terminal != null) {
                     if(origin.m_renderDisplayList < 0) {
                        origin.m_renderDisplayList = GL11.glGenLists(3);
                        redraw = true;
                     }

                     boolean colour = !clientTerminal.isColour();
                     int width = terminal.getWidth();
                     int height = terminal.getHeight();
                     int cursorX = terminal.getCursorX();
                     int cursorY = terminal.getCursorY();
                     FixedWidthFontRenderer fontRenderer = (FixedWidthFontRenderer)ComputerCraft.getFixedWidthFontRenderer();
                     GlStateManager.func_179094_E();

                     try {
                        double xScale = xSize / (double)(width * FixedWidthFontRenderer.FONT_WIDTH);
                        double yScale = ySize / (double)(height * FixedWidthFontRenderer.FONT_HEIGHT);
                        GlStateManager.func_179139_a(xScale, -yScale, 1.0D);
                        mc.func_110434_K().func_110577_a(FixedWidthFontRenderer.background);
                        if(redraw) {
                           GL11.glNewList(origin.m_renderDisplayList, 4864);

                           try {
                              double cursor = 0.03125D / xScale;
                              double marginYSize = 0.03125D / yScale;
                              double marginSquash = marginYSize / (double)FixedWidthFontRenderer.FONT_HEIGHT;
                              GL11.glPushMatrix();

                              try {
                                 GL11.glScaled(1.0D, marginSquash, 1.0D);
                                 GL11.glTranslated(0.0D, -marginYSize / marginSquash, 0.0D);
                                 fontRenderer.drawStringBackgroundPart(0, 0, terminal.getBackgroundColourLine(0), cursor, cursor, colour);
                                 GL11.glTranslated(0.0D, (marginYSize + (double)(height * FixedWidthFontRenderer.FONT_HEIGHT)) / marginSquash, 0.0D);
                                 fontRenderer.drawStringBackgroundPart(0, 0, terminal.getBackgroundColourLine(height - 1), cursor, cursor, colour);
                              } finally {
                                 GL11.glPopMatrix();
                              }

                              for(int y = 0; y < height; ++y) {
                                 fontRenderer.drawStringBackgroundPart(0, FixedWidthFontRenderer.FONT_HEIGHT * y, terminal.getBackgroundColourLine(y), cursor, cursor, colour);
                              }
                           } finally {
                              GL11.glEndList();
                           }
                        }

                        GlStateManager.func_179148_o(origin.m_renderDisplayList);
                        mc.func_110434_K().func_110577_a(FixedWidthFontRenderer.font);
                        if(redraw) {
                           GL11.glNewList(origin.m_renderDisplayList + 1, 4864);

                           try {
                              for(int var126 = 0; var126 < height; ++var126) {
                                 fontRenderer.drawStringTextPart(0, FixedWidthFontRenderer.FONT_HEIGHT * var126, terminal.getLine(var126), terminal.getTextColourLine(var126), colour);
                              }
                           } finally {
                              GL11.glEndList();
                           }
                        }

                        GlStateManager.func_179148_o(origin.m_renderDisplayList + 1);
                        mc.func_110434_K().func_110577_a(FixedWidthFontRenderer.font);
                        if(redraw) {
                           GL11.glNewList(origin.m_renderDisplayList + 2, 4864);

                           try {
                              if(terminal.getCursorBlink() && cursorX >= 0 && cursorX < width && cursorY >= 0 && cursorY < height) {
                                 TextBuffer var127 = new TextBuffer("_");
                                 TextBuffer cursorColour = new TextBuffer("0123456789abcdef".charAt(terminal.getTextColour()), 1);
                                 fontRenderer.drawString(var127, FixedWidthFontRenderer.FONT_WIDTH * cursorX, FixedWidthFontRenderer.FONT_HEIGHT * cursorY, cursorColour, (TextBuffer)null, 0.0D, 0.0D, colour);
                              }
                           } finally {
                              GL11.glEndList();
                           }
                        }

                        if(ComputerCraft.getGlobalCursorBlink()) {
                           GlStateManager.func_179148_o(origin.m_renderDisplayList + 2);
                        }
                     } finally {
                        GlStateManager.func_179121_F();
                     }
                  } else {
                     mc.func_110434_K().func_110577_a(FixedWidthFontRenderer.background);
                     renderer.func_181668_a(7, DefaultVertexFormats.field_181709_i);
                     var125 = Colour.Black;
                     renderer.func_181662_b(-0.03125D, -ySize - 0.03125D, 0.0D).func_187315_a(0.0D, 1.0D).func_181666_a(var125.getR(), var125.getG(), var125.getB(), 1.0F).func_181675_d();
                     renderer.func_181662_b(xSize + 0.03125D, -ySize - 0.03125D, 0.0D).func_187315_a(1.0D, 1.0D).func_181666_a(var125.getR(), var125.getG(), var125.getB(), 1.0F).func_181675_d();
                     renderer.func_181662_b(xSize + 0.03125D, 0.03125D, 0.0D).func_187315_a(1.0D, 0.0D).func_181666_a(var125.getR(), var125.getG(), var125.getB(), 1.0F).func_181675_d();
                     renderer.func_181662_b(-0.03125D, 0.03125D, 0.0D).func_187315_a(0.0D, 0.0D).func_181666_a(var125.getR(), var125.getG(), var125.getB(), 1.0F).func_181675_d();
                     renderer.func_181662_b(-0.03125D, -ySize - 0.03125D, 0.0D).func_187315_a(0.0D, 1.0D).func_181666_a(var125.getR(), var125.getG(), var125.getB(), 1.0F).func_181675_d();
                     tessellator.func_78381_a();
                  }
               } finally {
                  GlStateManager.func_179132_a(true);
                  GlStateManager.func_179145_e();
               }

               GlStateManager.func_179135_a(false, false, false, false);

               try {
                  mc.func_110434_K().func_110577_a(FixedWidthFontRenderer.background);
                  renderer.func_181668_a(7, DefaultVertexFormats.field_181709_i);
                  var125 = Colour.Black;
                  renderer.func_181662_b(-0.03125D, -ySize - 0.03125D, 0.0D).func_187315_a(0.0D, 1.0D).func_181666_a(var125.getR(), var125.getG(), var125.getB(), 1.0F).func_181675_d();
                  renderer.func_181662_b(xSize + 0.03125D, -ySize - 0.03125D, 0.0D).func_187315_a(1.0D, 1.0D).func_181666_a(var125.getR(), var125.getG(), var125.getB(), 1.0F).func_181675_d();
                  renderer.func_181662_b(xSize + 0.03125D, 0.03125D, 0.0D).func_187315_a(1.0D, 0.0D).func_181666_a(var125.getR(), var125.getG(), var125.getB(), 1.0F).func_181675_d();
                  renderer.func_181662_b(-0.03125D, 0.03125D, 0.0D).func_187315_a(0.0D, 0.0D).func_181666_a(var125.getR(), var125.getG(), var125.getB(), 1.0F).func_181675_d();
                  renderer.func_181662_b(-0.03125D, -ySize - 0.03125D, 0.0D).func_187315_a(0.0D, 1.0D).func_181666_a(var125.getR(), var125.getG(), var125.getB(), 1.0F).func_181675_d();
                  tessellator.func_78381_a();
               } finally {
                  GlStateManager.func_179135_a(true, true, true, true);
               }
            } finally {
               GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
               GlStateManager.func_179121_F();
            }

         }
      }
   }
}
