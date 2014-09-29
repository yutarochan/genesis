/*   1:    */ package leonidFedorov;
/*   2:    */ 
/*   3:    */ public class FuzzyRule
/*   4:    */ {
/*   5: 10 */   private String text = "";
/*   6: 11 */   private double value = 0.0D;
/*   7:    */   
/*   8:    */   private String Validate(String text)
/*   9:    */   {
/*  10: 15 */     int count = 0;
/*  11: 16 */     int position = text.indexOf("(");
/*  12: 17 */     String[] tokens = text.replace("(", "").replace(")", "").split(" ");
/*  13: 19 */     while (position >= 0)
/*  14:    */     {
/*  15: 21 */       count++;
/*  16: 22 */       position = text.indexOf("(", position + 1);
/*  17:    */     }
/*  18: 25 */     position = text.indexOf(")");
/*  19: 26 */     while (position >= 0)
/*  20:    */     {
/*  21: 28 */       count--;
/*  22: 29 */       position = text.indexOf(")", position + 1);
/*  23:    */     }
/*  24: 32 */     if (count > 0) {
/*  25: 34 */       throw new RuntimeException("missing right parenthesis: " + text);
/*  26:    */     }
/*  27: 36 */     if (count < 0) {
/*  28: 38 */       throw new RuntimeException("missing left parenthesis: " + text);
/*  29:    */     }
/*  30: 41 */     if (!tokens[0].equals("IF")) {
/*  31: 43 */       throw new RuntimeException("'IF' not found: " + text);
/*  32:    */     }
/*  33: 46 */     if (!tokens[(tokens.length - 4)].equals("THEN")) {
/*  34: 48 */       throw new RuntimeException("'THEN' not found: " + text);
/*  35:    */     }
/*  36: 51 */     if (!tokens[(tokens.length - 2)].equals("IS")) {
/*  37: 53 */       throw new RuntimeException("'IS' not found: " + text);
/*  38:    */     }
/*  39: 56 */     for (int i = 2; i < tokens.length - 5; i += 2) {
/*  40: 58 */       if ((!tokens[i].equals("IS")) && (!tokens[i].equals("AND")) && (!tokens[i].equals("OR"))) {
/*  41: 60 */         throw new RuntimeException("Syntax error: " + tokens[i]);
/*  42:    */       }
/*  43:    */     }
/*  44: 64 */     return text;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public FuzzyRule() {}
/*  48:    */   
/*  49:    */   public FuzzyRule(String text)
/*  50:    */   {
/*  51: 74 */     setText(text);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public final String getText()
/*  55:    */   {
/*  56: 81 */     return this.text;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public final void setText(String value)
/*  60:    */   {
/*  61: 85 */     this.text = Validate(value);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public final double getValue()
/*  65:    */   {
/*  66: 91 */     return this.value;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public final void setValue(double value)
/*  70:    */   {
/*  71: 95 */     this.value = value;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public final String Conditions()
/*  75:    */   {
/*  76:101 */     return this.text.substring(this.text.indexOf("IF ") + 3, this.text.indexOf("IF ") + 3 + this.text.indexOf(" THEN") - 3);
/*  77:    */   }
/*  78:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     leonidFedorov.FuzzyRule
 * JD-Core Version:    0.7.0.1
 */