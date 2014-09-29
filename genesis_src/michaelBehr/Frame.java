/*   1:    */ package michaelBehr;
/*   2:    */ 
/*   3:    */ import org.w3c.dom.Document;
/*   4:    */ import org.w3c.dom.Element;
/*   5:    */ 
/*   6:    */ public class Frame
/*   7:    */   implements AnnotationElement
/*   8:    */ {
/*   9:    */   int frameNumber;
/*  10:    */   String status;
/*  11:    */   Shape shape;
/*  12:    */   
/*  13:    */   public Frame(int argFrameNumber, String argStatus, Shape argShape)
/*  14:    */   {
/*  15: 17 */     setFrameNumber(argFrameNumber);
/*  16: 18 */     setStatus(argStatus);
/*  17: 19 */     setShape(argShape);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public Frame(Element frameElement)
/*  21:    */     throws AnnotationParseException
/*  22:    */   {
/*  23: 27 */     setFrameNumber(Integer.parseInt(frameElement.getAttribute("frameNumber")));
/*  24: 28 */     setStatus(frameElement.getAttribute("status"));
/*  25:    */     
/*  26: 30 */     Element[] shapeElements = AnnotationUtils.getTypedChildren(frameElement, "shape");
/*  27: 31 */     if (shapeElements.length != 1) {
/*  28: 32 */       throw new AnnotationParseException("Wrong number of shape elements in a frame");
/*  29:    */     }
/*  30: 34 */     Element[] shapeChildren = AnnotationUtils.getElementChildren(shapeElements[0]);
/*  31: 35 */     if (shapeChildren.length != 1) {
/*  32: 36 */       throw new AnnotationParseException("Wrong number of children of a shape element");
/*  33:    */     }
/*  34: 38 */     Element shapeChild = shapeChildren[0];
/*  35: 39 */     if (shapeChild.getTagName() == "ellipse2D") {
/*  36: 40 */       setShape(new Ellipse2D(shapeChild));
/*  37: 41 */     } else if (shapeChild.getTagName() == "box") {
/*  38: 42 */       setShape(new Box(shapeChild));
/*  39:    */     } else {
/*  40: 44 */       throw new AnnotationParseException("Unrecognized shape type");
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Element toElement(Document doc)
/*  45:    */   {
/*  46: 49 */     Element out = doc.createElement("frame");
/*  47: 50 */     out.setAttribute("frameNumber", Integer.toString(getFrameNumber()));
/*  48: 51 */     out.setAttribute("status", getStatus());
/*  49: 52 */     Element s = doc.createElement("shape");
/*  50: 53 */     s.appendChild(getShape().toElement(doc));
/*  51: 54 */     out.appendChild(s);
/*  52: 55 */     return out;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public final int getFrameNumber()
/*  56:    */   {
/*  57: 65 */     return this.frameNumber;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public final void setFrameNumber(int argFrameNumber)
/*  61:    */   {
/*  62: 74 */     this.frameNumber = argFrameNumber;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public final String getStatus()
/*  66:    */   {
/*  67: 83 */     return this.status;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public final void setStatus(String argStatus)
/*  71:    */   {
/*  72: 92 */     this.status = argStatus;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public final Shape getShape()
/*  76:    */   {
/*  77:101 */     return this.shape;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public final void setShape(Shape argShape)
/*  81:    */   {
/*  82:110 */     this.shape = argShape;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String toString()
/*  86:    */   {
/*  87:117 */     int sbSize = 1000;
/*  88:118 */     String variableSeparator = "  ";
/*  89:119 */     StringBuffer sb = new StringBuffer(1000);
/*  90:    */     
/*  91:121 */     sb.append("frameNumber=").append(this.frameNumber);
/*  92:122 */     sb.append("  ");
/*  93:123 */     sb.append("status=").append(this.status);
/*  94:124 */     sb.append("  ");
/*  95:125 */     sb.append("shape=").append(this.shape);
/*  96:    */     
/*  97:127 */     return sb.toString();
/*  98:    */   }
/*  99:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     michaelBehr.Frame
 * JD-Core Version:    0.7.0.1
 */