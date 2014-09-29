/*   1:    */ package bridge.reps.entities;
/*   2:    */ 
/*   3:    */ import bridge.utils.logging.Logger;
/*   4:    */ import java.awt.Color;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Vector;
/*   7:    */ 
/*   8:    */ public class Tracer
/*   9:    */ {
/*  10: 12 */   private static HashMap<String, Color> colors = new HashMap();
/*  11:    */   
/*  12:    */   public static void trace(Entity t, String color)
/*  13:    */   {
/*  14: 18 */     t.addType(color, "tracers");
/*  15: 19 */     if (t.functionP())
/*  16:    */     {
/*  17: 20 */       Function d = (Function)t;
/*  18: 21 */       trace(((Function)t).getSubject(), color);
/*  19:    */     }
/*  20: 23 */     if (t.relationP())
/*  21:    */     {
/*  22: 24 */       trace(((Relation)t).getSubject(), color);
/*  23: 25 */       trace(((Relation)t).getObject(), color);
/*  24:    */     }
/*  25: 27 */     if (t.sequenceP())
/*  26:    */     {
/*  27: 28 */       Vector v = ((Sequence)t).getElements();
/*  28: 29 */       for (int i = 0; i < v.size(); i++) {
/*  29: 30 */         trace((Entity)v.get(i), color);
/*  30:    */       }
/*  31:    */     }
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static void trace(Entity t, String tracer, Color color)
/*  35:    */   {
/*  36: 40 */     Object current = colors.get(tracer);
/*  37: 41 */     if (current != null) {
/*  38: 42 */       Logger.fine("Tracer", "Changed color for tracer " + tracer);
/*  39:    */     }
/*  40: 44 */     colors.put(tracer, color);
/*  41: 45 */     t.addType(tracer, "tracers");
/*  42: 46 */     if (t.functionP())
/*  43:    */     {
/*  44: 47 */       Function d = (Function)t;
/*  45: 48 */       trace(((Function)t).getSubject(), tracer);
/*  46:    */     }
/*  47: 50 */     if (t.relationP())
/*  48:    */     {
/*  49: 51 */       trace(((Relation)t).getSubject(), tracer);
/*  50: 52 */       trace(((Relation)t).getObject(), tracer);
/*  51:    */     }
/*  52: 54 */     if (t.sequenceP())
/*  53:    */     {
/*  54: 55 */       Vector v = ((Sequence)t).getElements();
/*  55: 56 */       for (int i = 0; i < v.size(); i++) {
/*  56: 57 */         trace((Entity)v.get(i), tracer);
/*  57:    */       }
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public static void untrace(Entity t)
/*  62:    */   {
/*  63: 66 */     t.getBundle().removeThread(t.getThread("tracers"));
/*  64: 67 */     if (t.functionP())
/*  65:    */     {
/*  66: 68 */       Function d = (Function)t;
/*  67: 69 */       untrace(((Function)t).getSubject());
/*  68:    */     }
/*  69: 71 */     if (t.relationP())
/*  70:    */     {
/*  71: 72 */       untrace(((Relation)t).getSubject());
/*  72: 73 */       untrace(((Relation)t).getObject());
/*  73:    */     }
/*  74: 75 */     if (t.sequenceP())
/*  75:    */     {
/*  76: 76 */       Vector v = ((Sequence)t).getElements();
/*  77: 77 */       for (int i = 0; i < v.size(); i++) {
/*  78: 78 */         untrace((Entity)v.get(i));
/*  79:    */       }
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static void untrace(Entity t, String tracer)
/*  84:    */   {
/*  85: 87 */     Thread thread = t.getThread("tracers");
/*  86: 88 */     if (thread == null) {
/*  87: 88 */       return;
/*  88:    */     }
/*  89: 89 */     thread.remove(tracer);
/*  90: 90 */     if (t.functionP())
/*  91:    */     {
/*  92: 91 */       Function d = (Function)t;
/*  93: 92 */       untrace(((Function)t).getSubject(), tracer);
/*  94:    */     }
/*  95: 94 */     else if (t.relationP())
/*  96:    */     {
/*  97: 95 */       untrace(((Relation)t).getSubject(), tracer);
/*  98: 96 */       untrace(((Relation)t).getObject(), tracer);
/*  99:    */     }
/* 100: 98 */     else if (t.sequenceP())
/* 101:    */     {
/* 102: 99 */       Vector v = ((Sequence)t).getElements();
/* 103:100 */       for (int i = 0; i < v.size(); i++) {
/* 104:101 */         untrace((Entity)v.get(i), tracer);
/* 105:    */       }
/* 106:    */     }
/* 107:    */   }
/* 108:    */   
/* 109:    */   public static Color getColor(Entity thing)
/* 110:    */   {
/* 111:110 */     Vector tracers = thing.getThread("tracers");
/* 112:111 */     if (tracers != null)
/* 113:    */     {
/* 114:112 */       String tracer = (String)tracers.lastElement();
/* 115:113 */       Object value = colors.get(tracer);
/* 116:114 */       if (value != null) {
/* 117:114 */         return (Color)value;
/* 118:    */       }
/* 119:115 */       if (tracer.equalsIgnoreCase("tracers")) {
/* 120:115 */         return null;
/* 121:    */       }
/* 122:116 */       if (tracer.equalsIgnoreCase("black")) {
/* 123:116 */         return Color.BLACK;
/* 124:    */       }
/* 125:117 */       if (tracer.equalsIgnoreCase("blue")) {
/* 126:117 */         return Color.BLUE;
/* 127:    */       }
/* 128:118 */       if (tracer.equalsIgnoreCase("cyan")) {
/* 129:118 */         return Color.CYAN;
/* 130:    */       }
/* 131:119 */       if (tracer.equalsIgnoreCase("darkgray")) {
/* 132:119 */         return Color.DARK_GRAY;
/* 133:    */       }
/* 134:120 */       if (tracer.equalsIgnoreCase("gray")) {
/* 135:120 */         return Color.GRAY;
/* 136:    */       }
/* 137:121 */       if (tracer.equalsIgnoreCase("green")) {
/* 138:121 */         return Color.GREEN;
/* 139:    */       }
/* 140:122 */       if (tracer.equalsIgnoreCase("lightgray")) {
/* 141:122 */         return Color.LIGHT_GRAY;
/* 142:    */       }
/* 143:123 */       if (tracer.equalsIgnoreCase("magenta")) {
/* 144:123 */         return Color.MAGENTA;
/* 145:    */       }
/* 146:124 */       if (tracer.equalsIgnoreCase("orange")) {
/* 147:124 */         return Color.ORANGE;
/* 148:    */       }
/* 149:125 */       if (tracer.equalsIgnoreCase("pink")) {
/* 150:125 */         return Color.PINK;
/* 151:    */       }
/* 152:126 */       if (tracer.equalsIgnoreCase("red")) {
/* 153:126 */         return Color.RED;
/* 154:    */       }
/* 155:127 */       if (tracer.equalsIgnoreCase("white")) {
/* 156:127 */         return Color.WHITE;
/* 157:    */       }
/* 158:128 */       if (tracer.equalsIgnoreCase("yellow")) {
/* 159:128 */         return Color.YELLOW;
/* 160:    */       }
/* 161:130 */       Logger.warning("ThingToViwerTranslator", "Tracer is not a known tracer");
/* 162:131 */       return Color.PINK;
/* 163:    */     }
/* 164:134 */     return null;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public static void main(String[] ignore)
/* 168:    */   {
/* 169:138 */     Logger.info("Tracer", "Hello");
/* 170:139 */     Logger.info("Tracer", Color.RED.toString());
/* 171:140 */     trace(new Entity(), "foo", Color.red);
/* 172:141 */     trace(new Entity(), "foo", Color.red);
/* 173:    */   }
/* 174:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.reps.entities.Tracer
 * JD-Core Version:    0.7.0.1
 */