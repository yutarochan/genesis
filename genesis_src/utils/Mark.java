/*   1:    */ package utils;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Arrays;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.swing.AbstractButton;
/*   8:    */ 
/*   9:    */ public class Mark
/*  10:    */ {
/*  11:    */   public static void a(Object... objects)
/*  12:    */   {
/*  13: 12 */     sayLabel("A", objects);
/*  14:    */   }
/*  15:    */   
/*  16:    */   public static void b(Object... objects)
/*  17:    */   {
/*  18: 16 */     sayLabel("B", objects);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public static void c(Object... objects)
/*  22:    */   {
/*  23: 20 */     sayLabel("C", objects);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public static void d(Object... objects)
/*  27:    */   {
/*  28: 24 */     sayLabel("D", objects);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static void sayLabel(String label, Object... objects)
/*  32:    */   {
/*  33: 28 */     String mark = "Mark " + label + (objects.length > 0 ? ":" : "");
/*  34: 29 */     List<Object> l = new ArrayList(Arrays.asList(objects));
/*  35: 30 */     l.add(0, mark);
/*  36: 31 */     say(l.toArray());
/*  37:    */   }
/*  38:    */   
/*  39:    */   @Deprecated
/*  40:    */   public static void comment(Object o) {}
/*  41:    */   
/*  42:    */   private static String format(Object[] objects, int first)
/*  43:    */   {
/*  44: 41 */     String result = ">>> ";
/*  45: 42 */     for (int i = first; i < objects.length; i++) {
/*  46: 43 */       result = result + " " + objects[i];
/*  47:    */     }
/*  48: 48 */     result = result + " " + link();
/*  49: 49 */     return result;
/*  50:    */   }
/*  51:    */   
/*  52:    */   private static String link()
/*  53:    */   {
/*  54: 54 */     StackTraceElement[] st = Thread.currentThread().getStackTrace();
/*  55: 55 */     for (int i = 2;; i++) {
/*  56: 56 */       if (st[i].getClassName() != Mark.class.getName()) {
/*  57: 60 */         return "(" + st[i].getClassName() + ".java:" + st[i].getLineNumber() + ")";
/*  58:    */       }
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static void err(Object... objects)
/*  63:    */   {
/*  64: 66 */     Object leadObject = objects[0];
/*  65: 67 */     if ((leadObject instanceof Boolean))
/*  66:    */     {
/*  67: 68 */       if (!((Boolean)leadObject).booleanValue()) {
/*  68: 69 */         return;
/*  69:    */       }
/*  70: 72 */       System.err.println(format(objects, 1));
/*  71:    */     }
/*  72:    */     else
/*  73:    */     {
/*  74: 76 */       System.err.println(format(objects, 0));
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static void say(Object... objects)
/*  79:    */   {
/*  80: 88 */     Object leadObject = objects[0];
/*  81: 89 */     if ((leadObject instanceof Boolean))
/*  82:    */     {
/*  83: 90 */       if (!((Boolean)leadObject).booleanValue()) {
/*  84: 91 */         return;
/*  85:    */       }
/*  86: 94 */       System.out.println(format(objects, 1));
/*  87:    */     }
/*  88:    */     else
/*  89:    */     {
/*  90: 98 */       System.out.println(format(objects, 0));
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   @Deprecated
/*  95:    */   public static void betterSay(Object... objects)
/*  96:    */   {
/*  97:107 */     say(objects);
/*  98:    */   }
/*  99:    */   
/* 100:    */   @Deprecated
/* 101:    */   public static void betterErr(Object... objects)
/* 102:    */   {
/* 103:115 */     err(objects);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public static void say(AbstractButton b, Object... o)
/* 107:    */   {
/* 108:123 */     if (b.isSelected()) {
/* 109:124 */       say(o);
/* 110:    */     }
/* 111:    */   }
/* 112:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     utils.Mark
 * JD-Core Version:    0.7.0.1
 */