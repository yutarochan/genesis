/*  1:   */ package bridge.reps.entities;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ 
/*  5:   */ public class IteratorForXML
/*  6:   */ {
/*  7:   */   String input;
/*  8:   */   String start;
/*  9:   */   String end;
/* 10:   */   String nextResult;
/* 11:15 */   boolean test = false;
/* 12:   */   
/* 13:   */   public IteratorForXML(String i, String s, String e)
/* 14:   */   {
/* 15:17 */     this.input = i;
/* 16:18 */     this.start = s;
/* 17:19 */     this.end = e;
/* 18:   */     
/* 19:   */ 
/* 20:   */ 
/* 21:23 */     next();
/* 22:   */   }
/* 23:   */   
/* 24:   */   public IteratorForXML(String i, String tag)
/* 25:   */   {
/* 26:30 */     this(i, tag, tag);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public boolean hasNext()
/* 30:   */   {
/* 31:36 */     return this.nextResult != null;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String next()
/* 35:   */   {
/* 36:42 */     String thisResult = this.nextResult;
/* 37:   */     
/* 38:44 */     this.nextResult = Tags.untagTopLevelString(this.start, this.input);
/* 39:49 */     if (this.nextResult == null) {
/* 40:49 */       return thisResult;
/* 41:   */     }
/* 42:51 */     int index = this.input.indexOf(this.nextResult) + this.nextResult.length();
/* 43:   */     
/* 44:53 */     index = this.input.indexOf("</" + this.start + ">", index) + 3 + this.start.length();
/* 45:   */     
/* 46:55 */     this.input = this.input.substring(index);
/* 47:   */     
/* 48:57 */     this.nextResult = Tags.tag(this.start, this.nextResult);
/* 49:   */     
/* 50:   */ 
/* 51:   */ 
/* 52:   */ 
/* 53:62 */     return thisResult;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public static void main(String[] argv)
/* 57:   */   {
/* 58:68 */     String test = "<foo><foo>1</foo></foo> bar <foo>2</foo> baz <foo> 3";
/* 59:69 */     IteratorForXML iterator = new IteratorForXML(test, "foo");
/* 60:70 */     System.out.println(iterator.next());
/* 61:71 */     System.out.println(iterator.next());
/* 62:72 */     System.out.println(iterator.next());
/* 63:73 */     System.out.println(iterator.next());
/* 64:   */   }
/* 65:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.reps.entities.IteratorForXML
 * JD-Core Version:    0.7.0.1
 */