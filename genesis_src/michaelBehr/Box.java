/*   1:    */ package michaelBehr;
/*   2:    */ 
/*   3:    */ import org.w3c.dom.Document;
/*   4:    */ import org.w3c.dom.Element;
/*   5:    */ 
/*   6:    */ public class Box
/*   7:    */   implements Shape
/*   8:    */ {
/*   9:    */   int x;
/*  10:    */   int y;
/*  11:    */   int width;
/*  12:    */   int height;
/*  13:    */   
/*  14:    */   public Box(int argX, int argY, int argWidth, int argHeight)
/*  15:    */   {
/*  16: 18 */     setX(argX);
/*  17: 19 */     setY(argY);
/*  18: 20 */     setWidth(argWidth);
/*  19: 21 */     setHeight(argHeight);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public Box(Element boxElement)
/*  23:    */   {
/*  24: 25 */     setX(Integer.parseInt(boxElement.getAttribute("x")));
/*  25: 26 */     setY(Integer.parseInt(boxElement.getAttribute("y")));
/*  26: 27 */     setWidth(Integer.parseInt(boxElement.getAttribute("w")));
/*  27: 28 */     setHeight(Integer.parseInt(boxElement.getAttribute("h")));
/*  28:    */   }
/*  29:    */   
/*  30:    */   public Element toElement(Document doc)
/*  31:    */   {
/*  32: 32 */     Element out = doc.createElement("ellipse2D");
/*  33: 33 */     out.setAttribute("x", Integer.toString(getX()));
/*  34: 34 */     out.setAttribute("y", Integer.toString(getY()));
/*  35: 35 */     out.setAttribute("width", Integer.toString(getWidth()));
/*  36: 36 */     out.setAttribute("height", Integer.toString(getHeight()));
/*  37: 37 */     return out;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public final int getX()
/*  41:    */   {
/*  42: 47 */     return this.x;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public final void setX(int argX)
/*  46:    */   {
/*  47: 56 */     this.x = argX;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public final int getY()
/*  51:    */   {
/*  52: 65 */     return this.y;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public final void setY(int argY)
/*  56:    */   {
/*  57: 74 */     this.y = argY;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public final int getWidth()
/*  61:    */   {
/*  62: 83 */     return this.width;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public final void setWidth(int argWidth)
/*  66:    */   {
/*  67: 92 */     this.width = argWidth;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public final int getHeight()
/*  71:    */   {
/*  72:101 */     return this.height;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public final void setHeight(int argHeight)
/*  76:    */   {
/*  77:110 */     this.height = argHeight;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String toString()
/*  81:    */   {
/*  82:117 */     int sbSize = 1000;
/*  83:118 */     String variableSeparator = "  ";
/*  84:119 */     StringBuffer sb = new StringBuffer(1000);
/*  85:    */     
/*  86:121 */     sb.append("x=").append(this.x);
/*  87:122 */     sb.append("  ");
/*  88:123 */     sb.append("y=").append(this.y);
/*  89:124 */     sb.append("  ");
/*  90:125 */     sb.append("width=").append(this.width);
/*  91:126 */     sb.append("  ");
/*  92:127 */     sb.append("height=").append(this.height);
/*  93:    */     
/*  94:129 */     return sb.toString();
/*  95:    */   }
/*  96:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     michaelBehr.Box
 * JD-Core Version:    0.7.0.1
 */