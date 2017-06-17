package dan200.computercraft.shared.turtle.blocks;

import dan200.computercraft.shared.util.Colour;
import net.minecraft.util.IStringSerializable;

public enum BlockTurtleDyeVariant implements IStringSerializable {

   None("None", 0, "none", (Colour)null),
   Black("Black", 1, "black", Colour.Black),
   Red("Red", 2, "red", Colour.Red),
   Green("Green", 3, "green", Colour.Green),
   Brown("Brown", 4, "brown", Colour.Brown),
   Blue("Blue", 5, "blue", Colour.Blue),
   Purple("Purple", 6, "purple", Colour.Purple),
   Cyan("Cyan", 7, "cyan", Colour.Cyan),
   LightGrey("LightGrey", 8, "light_grey", Colour.LightGrey),
   Grey("Grey", 9, "grey", Colour.Grey),
   Pink("Pink", 10, "pink", Colour.Pink),
   Lime("Lime", 11, "lime", Colour.Lime),
   Yellow("Yellow", 12, "yellow", Colour.Yellow),
   LightBlue("LightBlue", 13, "light_blue", Colour.LightBlue),
   Magenta("Magenta", 14, "magenta", Colour.Magenta),
   Orange("Orange", 15, "orange", Colour.Orange),
   White("White", 16, "white", Colour.Orange);
   private String m_name;
   private Colour m_colour;
   // $FF: synthetic field
   private static final BlockTurtleDyeVariant[] $VALUES = new BlockTurtleDyeVariant[]{None, Black, Red, Green, Brown, Blue, Purple, Cyan, LightGrey, Grey, Pink, Lime, Yellow, LightBlue, Magenta, Orange, White};


   public static BlockTurtleDyeVariant fromColour(Colour colour) {
      if(colour != null) {
         switch(BlockTurtleDyeVariant.NamelessClass201077687.$SwitchMap$dan200$computercraft$shared$util$Colour[colour.ordinal()]) {
         case 1:
            return Black;
         case 2:
            return Red;
         case 3:
            return Green;
         case 4:
            return Brown;
         case 5:
            return Blue;
         case 6:
            return Purple;
         case 7:
            return Cyan;
         case 8:
            return LightGrey;
         case 9:
            return Grey;
         case 10:
            return Pink;
         case 11:
            return Lime;
         case 12:
            return Yellow;
         case 13:
            return LightBlue;
         case 14:
            return Magenta;
         case 15:
            return Orange;
         case 16:
            return White;
         }
      }

      return None;
   }

   private BlockTurtleDyeVariant(String var1, int var2, String name, Colour colour) {
      this.m_name = name;
      this.m_colour = colour;
   }

   public String func_176610_l() {
      return this.m_name;
   }

   public Colour getColour() {
      return this.m_colour;
   }

   public String toString() {
      return this.func_176610_l();
   }


   // $FF: synthetic class
   static class NamelessClass201077687 {

      // $FF: synthetic field
      static final int[] $SwitchMap$dan200$computercraft$shared$util$Colour = new int[Colour.values().length];


      static {
         try {
            $SwitchMap$dan200$computercraft$shared$util$Colour[Colour.Black.ordinal()] = 1;
         } catch (NoSuchFieldError var16) {
            ;
         }

         try {
            $SwitchMap$dan200$computercraft$shared$util$Colour[Colour.Red.ordinal()] = 2;
         } catch (NoSuchFieldError var15) {
            ;
         }

         try {
            $SwitchMap$dan200$computercraft$shared$util$Colour[Colour.Green.ordinal()] = 3;
         } catch (NoSuchFieldError var14) {
            ;
         }

         try {
            $SwitchMap$dan200$computercraft$shared$util$Colour[Colour.Brown.ordinal()] = 4;
         } catch (NoSuchFieldError var13) {
            ;
         }

         try {
            $SwitchMap$dan200$computercraft$shared$util$Colour[Colour.Blue.ordinal()] = 5;
         } catch (NoSuchFieldError var12) {
            ;
         }

         try {
            $SwitchMap$dan200$computercraft$shared$util$Colour[Colour.Purple.ordinal()] = 6;
         } catch (NoSuchFieldError var11) {
            ;
         }

         try {
            $SwitchMap$dan200$computercraft$shared$util$Colour[Colour.Cyan.ordinal()] = 7;
         } catch (NoSuchFieldError var10) {
            ;
         }

         try {
            $SwitchMap$dan200$computercraft$shared$util$Colour[Colour.LightGrey.ordinal()] = 8;
         } catch (NoSuchFieldError var9) {
            ;
         }

         try {
            $SwitchMap$dan200$computercraft$shared$util$Colour[Colour.Grey.ordinal()] = 9;
         } catch (NoSuchFieldError var8) {
            ;
         }

         try {
            $SwitchMap$dan200$computercraft$shared$util$Colour[Colour.Pink.ordinal()] = 10;
         } catch (NoSuchFieldError var7) {
            ;
         }

         try {
            $SwitchMap$dan200$computercraft$shared$util$Colour[Colour.Lime.ordinal()] = 11;
         } catch (NoSuchFieldError var6) {
            ;
         }

         try {
            $SwitchMap$dan200$computercraft$shared$util$Colour[Colour.Yellow.ordinal()] = 12;
         } catch (NoSuchFieldError var5) {
            ;
         }

         try {
            $SwitchMap$dan200$computercraft$shared$util$Colour[Colour.LightBlue.ordinal()] = 13;
         } catch (NoSuchFieldError var4) {
            ;
         }

         try {
            $SwitchMap$dan200$computercraft$shared$util$Colour[Colour.Magenta.ordinal()] = 14;
         } catch (NoSuchFieldError var3) {
            ;
         }

         try {
            $SwitchMap$dan200$computercraft$shared$util$Colour[Colour.Orange.ordinal()] = 15;
         } catch (NoSuchFieldError var2) {
            ;
         }

         try {
            $SwitchMap$dan200$computercraft$shared$util$Colour[Colour.White.ordinal()] = 16;
         } catch (NoSuchFieldError var1) {
            ;
         }

      }
   }
}
