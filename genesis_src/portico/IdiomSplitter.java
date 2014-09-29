/*  1:   */ package portico;
/*  2:   */ 
/*  3:   */ import connections.AbstractWiredBox;
/*  4:   */ import connections.Connections;
/*  5:   */ import connections.Ports;
/*  6:   */ import java.io.PrintStream;
/*  7:   */ 
/*  8:   */ public class IdiomSplitter
/*  9:   */   extends AbstractWiredBox
/* 10:   */ {
/* 11:   */   public static final String LEFT = "left";
/* 12:   */   public static final String RIGHT = "right";
/* 13:   */   public static final String COMBINATOR = "combinator";
/* 14:   */   public static final String NONE = "none";
/* 15:   */   public static final String CAUSE = "cause";
/* 16:   */   public static final String BEFORE = "before";
/* 17:   */   public static final String WHILE = "while";
/* 18:   */   public static final String AFTER = "after";
/* 19:   */   
/* 20:   */   public IdiomSplitter()
/* 21:   */   {
/* 22:30 */     Connections.getPorts(this).addSignalProcessor("process");
/* 23:   */   }
/* 24:   */   
/* 25:   */   public synchronized void process(Object s)
/* 26:   */   {
/* 27:35 */     if (!(s instanceof String)) {
/* 28:36 */       return;
/* 29:   */     }
/* 30:38 */     System.out.println("Working on " + s);
/* 31:39 */     analyze(((String)s).trim());
/* 32:   */   }
/* 33:   */   
/* 34:   */   private void analyze(String words)
/* 35:   */   {
/* 36:46 */     String splitter = " because ";
/* 37:47 */     int index = words.indexOf(splitter);
/* 38:48 */     if (index > 0)
/* 39:   */     {
/* 40:49 */       transmit(words, index, splitter, "cause");
/* 41:50 */       return;
/* 42:   */     }
/* 43:52 */     splitter = " before ";
/* 44:53 */     index = words.indexOf(splitter);
/* 45:54 */     if (index > 0)
/* 46:   */     {
/* 47:55 */       transmit(words, index, splitter, "before");
/* 48:56 */       return;
/* 49:   */     }
/* 50:58 */     splitter = " while ";
/* 51:59 */     index = words.indexOf(splitter);
/* 52:60 */     if (index > 0)
/* 53:   */     {
/* 54:61 */       transmit(words, index, splitter, "while");
/* 55:62 */       return;
/* 56:   */     }
/* 57:64 */     splitter = " after ";
/* 58:65 */     index = words.indexOf(splitter);
/* 59:66 */     if (index > 0)
/* 60:   */     {
/* 61:67 */       transmit(words, index, splitter, "after");
/* 62:68 */       return;
/* 63:   */     }
/* 64:70 */     Connections.getPorts(this).transmit("left", words);
/* 65:71 */     Connections.getPorts(this).transmit("combinator", "none");
/* 66:   */   }
/* 67:   */   
/* 68:   */   private void transmit(String words, int index, String splitter, String combinator)
/* 69:   */   {
/* 70:75 */     Connections.getPorts(this).transmit("left", words.substring(0, index).trim());
/* 71:76 */     Connections.getPorts(this).transmit("right", words.substring(index + splitter.length(), words.length()).trim());
/* 72:77 */     Connections.getPorts(this).transmit("combinator", combinator);
/* 73:   */   }
/* 74:   */   
/* 75:   */   public static void main(String[] ignore)
/* 76:   */   {
/* 77:96 */     new IdiomSplitter().process("This is a  because        test");
/* 78:   */   }
/* 79:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     portico.IdiomSplitter
 * JD-Core Version:    0.7.0.1
 */