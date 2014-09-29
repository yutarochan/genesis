/*  1:   */ package giulianoGiacaglia;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ import connections.Connections.NetWireException;
/*  5:   */ import connections.WiredBox;
/*  6:   */ import genesis.Genesis;
/*  7:   */ import matthewFay.StoryAlignment.AlignmentProcessor;
/*  8:   */ import utils.Mark;
/*  9:   */ 
/* 10:   */ public class LocalGenesis
/* 11:   */   extends Genesis
/* 12:   */ {
/* 13:   */   LocalProcessor localProcessor;
/* 14:   */   private AlignmentProcessor alignmentProcessor;
/* 15:   */   private AlignmentProcessor alignmentProcessor2;
/* 16:   */   
/* 17:   */   public LocalGenesis()
/* 18:   */   {
/* 19:24 */     this.alignmentProcessor = createAlignmentProcessor(this.alignmentProcessor);
/* 20:25 */     this.alignmentProcessor2 = createAlignmentProcessor(this.alignmentProcessor2);
/* 21:26 */     Mark.say(new Object[] {"Giuliano's constructor" });
/* 22:   */     
/* 23:   */ 
/* 24:   */ 
/* 25:   */ 
/* 26:   */ 
/* 27:   */ 
/* 28:   */ 
/* 29:   */ 
/* 30:   */ 
/* 31:   */ 
/* 32:   */ 
/* 33:   */ 
/* 34:   */ 
/* 35:   */ 
/* 36:   */ 
/* 37:   */ 
/* 38:43 */     Connections.wire(this.alignmentProcessor, getAlignmentViewer());
/* 39:   */     
/* 40:   */ 
/* 41:   */ 
/* 42:   */ 
/* 43:   */ 
/* 44:   */ 
/* 45:   */ 
/* 46:   */ 
/* 47:   */ 
/* 48:53 */     Connections.wire(this.alignmentProcessor2, getAlignmentViewer());
/* 49:   */   }
/* 50:   */   
/* 51:   */   public LocalProcessor getLocalProcessor()
/* 52:   */   {
/* 53:64 */     if (this.localProcessor == null) {
/* 54:65 */       this.localProcessor = new LocalProcessor();
/* 55:   */     }
/* 56:67 */     return this.localProcessor;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public static void main(String[] args)
/* 60:   */   {
/* 61:72 */     LocalGenesis myGenesis = new LocalGenesis();
/* 62:73 */     myGenesis.startInFrame();
/* 63:   */   }
/* 64:   */   
/* 65:   */   private AlignmentProcessor createAlignmentProcessor(AlignmentProcessor alignmentProcessor)
/* 66:   */   {
/* 67:78 */     if (alignmentProcessor == null) {
/* 68:   */       try
/* 69:   */       {
/* 70:80 */         WiredBox localWiredBox = Connections.subscribe("AlignmentProcessorService", 2.0D);
/* 71:   */       }
/* 72:   */       catch (Connections.NetWireException e)
/* 73:   */       {
/* 74:83 */         Mark.err(new Object[] {"Failed to connect to remote Aligner, falling back to local" });
/* 75:   */       }
/* 76:   */       finally
/* 77:   */       {
/* 78:87 */         if (alignmentProcessor == null) {
/* 79:87 */           alignmentProcessor = new AlignmentProcessor();
/* 80:   */         }
/* 81:   */       }
/* 82:   */     }
/* 83:91 */     return alignmentProcessor;
/* 84:   */   }
/* 85:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     giulianoGiacaglia.LocalGenesis
 * JD-Core Version:    0.7.0.1
 */