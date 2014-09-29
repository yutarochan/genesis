/*  1:   */ package hibaAwad;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ import connections.Ports;
/*  5:   */ import genesis.Genesis;
/*  6:   */ import utils.Mark;
/*  7:   */ import windowGroup.WindowGroupManager;
/*  8:   */ 
/*  9:   */ public class LocalGenesis
/* 10:   */   extends Genesis
/* 11:   */ {
/* 12:   */   EastWestExpert eastWestExpert;
/* 13:   */   CoherenceViewer coherenceViewer;
/* 14:   */   MagicBox magicBox;
/* 15:   */   
/* 16:   */   public LocalGenesis()
/* 17:   */   {
/* 18:25 */     Mark.say(new Object[] {"LocalGenesis's constructor" });
/* 19:   */     
/* 20:27 */     Connections.wire("viewer", getPersonalityExpert(), new MagicBox(getMentalModel1()));
/* 21:   */     
/* 22:   */ 
/* 23:   */ 
/* 24:   */ 
/* 25:   */ 
/* 26:   */ 
/* 27:   */ 
/* 28:35 */     Connections.wire("final-inferences", getMentalModel1(), "final inferances port", getLocalProcessor());
/* 29:   */     
/* 30:37 */     Connections.wire("Reflection analysis port", getMentalModel1(), "reflection port", getLocalProcessor());
/* 31:   */     
/* 32:   */ 
/* 33:40 */     Connections.wire("complete story analysis port", getMentalModel1(), "complete story input port", getLocalProcessor());
/* 34:   */     
/* 35:   */ 
/* 36:   */ 
/* 37:   */ 
/* 38:45 */     getWindowGroupManager().addJComponent(getCoherenceViewer());
/* 39:   */     
/* 40:   */ 
/* 41:48 */     Connections.wire("coherence data port", getLocalProcessor(), "My Data Port", getCoherenceViewer());
/* 42:49 */     Connections.wire("coherence text port", getLocalProcessor(), "My Text Port", getCoherenceViewer());
/* 43:50 */     Connections.wire("coherence label port", getLocalProcessor(), "My Label Port", getCoherenceViewer());
/* 44:51 */     Connections.wire("coherence axis label port", getLocalProcessor(), "Axis labels", getCoherenceViewer());
/* 45:   */   }
/* 46:   */   
/* 47:   */   public EastWestExpert getLocalProcessor()
/* 48:   */   {
/* 49:56 */     if (this.eastWestExpert == null) {
/* 50:57 */       this.eastWestExpert = new EastWestExpert();
/* 51:   */     }
/* 52:59 */     return this.eastWestExpert;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public CoherenceViewer getCoherenceViewer()
/* 56:   */   {
/* 57:63 */     if (this.coherenceViewer == null)
/* 58:   */     {
/* 59:64 */       this.coherenceViewer = new CoherenceViewer();
/* 60:65 */       this.coherenceViewer.setName("Coherence");
/* 61:   */     }
/* 62:67 */     return this.coherenceViewer;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public static void main(String[] args)
/* 66:   */   {
/* 67:74 */     LocalGenesis myGenesis = new LocalGenesis();
/* 68:75 */     myGenesis.startInFrame();
/* 69:76 */     Connections.getPorts(myGenesis.getLocalProcessor()).transmit("coherence text port", "clear");
/* 70:77 */     Connections.getPorts(myGenesis.getLocalProcessor()).transmit("coherence text port", "Coherence");
/* 71:   */     
/* 72:79 */     String[] labels = { "Longest chain", "Caused Events", "Number of chains" };
/* 73:80 */     Connections.getPorts(myGenesis.getLocalProcessor()).transmit("coherence axis label port", labels);
/* 74:   */   }
/* 75:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     hibaAwad.LocalGenesis
 * JD-Core Version:    0.7.0.1
 */