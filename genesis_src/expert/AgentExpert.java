/*  1:   */ package expert;
/*  2:   */ 
/*  3:   */ import connections.AbstractWiredBox;
/*  4:   */ import connections.Connections;
/*  5:   */ import connections.Ports;
/*  6:   */ 
/*  7:   */ public class AgentExpert
/*  8:   */   extends AbstractWiredBox
/*  9:   */ {
/* 10:   */   public AgentExpert()
/* 11:   */   {
/* 12:16 */     setName("Agent expert");
/* 13:17 */     Connections.getPorts(this).addSignalProcessor("process");
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void process(Object object)
/* 17:   */   {
/* 18:21 */     if (Recognizers.agent(object))
/* 19:   */     {
/* 20:22 */       Connections.getPorts(this).transmit("viewer", object);
/* 21:23 */       Connections.getPorts(this).transmit("loop", Recognizers.theObject(object));
/* 22:   */     }
/* 23:   */     else
/* 24:   */     {
/* 25:27 */       Connections.getPorts(this).transmit("next", object);
/* 26:   */     }
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     expert.AgentExpert
 * JD-Core Version:    0.7.0.1
 */