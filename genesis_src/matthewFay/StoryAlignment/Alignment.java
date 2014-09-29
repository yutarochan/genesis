/*  1:   */ package matthewFay.StoryAlignment;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.LinkedList;
/*  6:   */ import matthewFay.Utilities.Pair;
/*  7:   */ 
/*  8:   */ public class Alignment<A, B>
/*  9:   */   extends LinkedList<Pair<A, B>>
/* 10:   */ {
/* 11:13 */   public String aName = "";
/* 12:15 */   public String bName = "";
/* 13:   */   public float score;
/* 14:   */   
/* 15:   */   public ArrayList<A> getA()
/* 16:   */   {
/* 17:24 */     ArrayList<A> list = new ArrayList();
/* 18:25 */     for (Pair<A, B> p : this) {
/* 19:26 */       list.add(p.a);
/* 20:   */     }
/* 21:28 */     return list;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public ArrayList<B> getB()
/* 25:   */   {
/* 26:32 */     ArrayList<B> list = new ArrayList();
/* 27:33 */     for (Pair<A, B> p : this) {
/* 28:34 */       list.add(p.b);
/* 29:   */     }
/* 30:36 */     return list;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public String toPrunedString()
/* 34:   */   {
/* 35:40 */     String as = "";
/* 36:41 */     for (Pair<A, B> pair : this)
/* 37:   */     {
/* 38:43 */       Entity a = (Entity)pair.a;
/* 39:44 */       Entity b = (Entity)pair.b;
/* 40:45 */       if ((a != null) && (b != null))
/* 41:   */       {
/* 42:47 */         as = as + a.asString();
/* 43:48 */         as = as + " : ";
/* 44:49 */         as = as + b.asString();
/* 45:50 */         as = as + "\n";
/* 46:   */       }
/* 47:   */     }
/* 48:53 */     return as;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public String toString()
/* 52:   */   {
/* 53:59 */     String as = "";
/* 54:60 */     for (Pair<A, B> pair : this)
/* 55:   */     {
/* 56:62 */       if (pair.a != null) {
/* 57:64 */         as = as + pair.a.toString();
/* 58:   */       } else {
/* 59:66 */         as = as + "---";
/* 60:   */       }
/* 61:68 */       as = as + " : ";
/* 62:69 */       if (pair.b != null) {
/* 63:71 */         as = as + pair.b.toString();
/* 64:   */       } else {
/* 65:73 */         as = as + "---";
/* 66:   */       }
/* 67:75 */       as = as + "\n";
/* 68:   */     }
/* 69:77 */     return as;
/* 70:   */   }
/* 71:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.StoryAlignment.Alignment
 * JD-Core Version:    0.7.0.1
 */