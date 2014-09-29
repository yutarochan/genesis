/*   1:    */ package memory.utilities;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Bundle;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Thread;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import m2.datatypes.Chain;
/*   9:    */ import m2.datatypes.DoubleBundle;
/*  10:    */ import magicLess.utils.EntityUtils;
/*  11:    */ 
/*  12:    */ public class SubSetUtils
/*  13:    */ {
/*  14:    */   public static boolean isSubSet(Entity subThing, Entity superThing)
/*  15:    */   {
/*  16: 35 */     if ((subThing == null) && (superThing == null)) {
/*  17: 35 */       return true;
/*  18:    */     }
/*  19: 36 */     if ((subThing == null) || (superThing == null)) {
/*  20: 36 */       return false;
/*  21:    */     }
/*  22: 37 */     if (isSubSet(subThing.getBundle(), superThing.getBundle())) {
/*  23: 38 */       return checkChildren(subThing, superThing);
/*  24:    */     }
/*  25: 40 */     return false;
/*  26:    */   }
/*  27:    */   
/*  28:    */   private static boolean checkChildren(Entity subThing, Entity superThing)
/*  29:    */   {
/*  30: 44 */     List<Entity> c1 = EntityUtils.getOrderedChildren(subThing);
/*  31: 45 */     List<Entity> c2 = EntityUtils.getOrderedChildren(superThing);
/*  32: 46 */     for (int i = 0; i < c1.size(); i++)
/*  33:    */     {
/*  34: 47 */       if (!isSubSet(((Entity)c1.get(i)).getBundle(), ((Entity)c2.get(i)).getBundle())) {
/*  35: 48 */         return false;
/*  36:    */       }
/*  37: 51 */       boolean check = checkChildren((Entity)c1.get(i), (Entity)c2.get(i));
/*  38: 52 */       if (!check) {
/*  39: 52 */         return false;
/*  40:    */       }
/*  41:    */     }
/*  42: 55 */     return true;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public static boolean isSubSet(Bundle subBundle, Bundle superBundle)
/*  46:    */   {
/*  47: 66 */     if ((subBundle == null) && (superBundle == null)) {
/*  48: 66 */       return true;
/*  49:    */     }
/*  50: 67 */     if ((subBundle == null) || (superBundle == null)) {
/*  51: 67 */       return false;
/*  52:    */     }
/*  53: 68 */     Bundle temp1 = (Bundle)subBundle.clone();
/*  54: 69 */     for (Thread t1 : subBundle) {
/*  55: 70 */       if (temp1.contains(t1)) {
/*  56: 71 */         for (Thread t2 : superBundle) {
/*  57: 72 */           if (isSubSet(t1, t2)) {
/*  58: 73 */             temp1.remove(t1);
/*  59:    */           }
/*  60:    */         }
/*  61:    */       }
/*  62:    */     }
/*  63: 77 */     if (temp1.isEmpty()) {
/*  64: 77 */       return true;
/*  65:    */     }
/*  66: 78 */     return false;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public static boolean isSubSet(Thread subThread, Thread superThread)
/*  70:    */   {
/*  71: 87 */     if ((subThread == null) && (superThread == null)) {
/*  72: 87 */       return true;
/*  73:    */     }
/*  74: 88 */     if ((subThread == null) || (superThread == null)) {
/*  75: 88 */       return false;
/*  76:    */     }
/*  77: 89 */     Thread temp = new Thread(subThread);
/*  78: 90 */     Thread temp2 = new Thread(superThread);
/*  79:    */     Iterator localIterator2;
/*  80: 91 */     for (Iterator localIterator1 = subThread.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/*  81:    */     {
/*  82: 91 */       String s = (String)localIterator1.next();
/*  83: 92 */       localIterator2 = superThread.iterator(); continue;String s2 = (String)localIterator2.next();
/*  84: 93 */       if ((temp.contains(s)) && (temp2.contains(s2))) {
/*  85: 96 */         if (s.equals(s2))
/*  86:    */         {
/*  87: 97 */           temp2.remove(s2);
/*  88: 98 */           temp.remove(s);
/*  89:    */         }
/*  90:    */         else
/*  91:    */         {
/*  92:101 */           temp2.remove(s2);
/*  93:    */         }
/*  94:    */       }
/*  95:    */     }
/*  96:105 */     if (temp.isEmpty()) {
/*  97:105 */       return true;
/*  98:    */     }
/*  99:106 */     return false;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public static boolean isChainSubSet(Entity subThing, Entity superThing)
/* 103:    */   {
/* 104:110 */     Chain c1 = new Chain(superThing);
/* 105:111 */     Chain c2 = new Chain(subThing);
/* 106:112 */     for (int i = 0; i < c2.size(); i++) {
/* 107:114 */       if (((DoubleBundle)c1.get(i)).getDistance((DoubleBundle)c2.get(i)) > 0) {
/* 108:115 */         return false;
/* 109:    */       }
/* 110:    */     }
/* 111:118 */     return true;
/* 112:    */   }
/* 113:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.utilities.SubSetUtils
 * JD-Core Version:    0.7.0.1
 */