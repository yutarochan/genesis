/*   1:    */ package carynKrakauer.controlPanels;
/*   2:    */ 
/*   3:    */ import carynKrakauer.RarityUtil;
/*   4:    */ import carynKrakauer.SimilarityProcessor;
/*   5:    */ import java.awt.BorderLayout;
/*   6:    */ import java.awt.Color;
/*   7:    */ import java.awt.Graphics;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.HashMap;
/*  10:    */ import javax.swing.JLabel;
/*  11:    */ import javax.swing.JPanel;
/*  12:    */ 
/*  13:    */ public class RarityVisualPanel
/*  14:    */   extends JPanel
/*  15:    */ {
/*  16:    */   SimilarityProcessor similarityProcessor;
/*  17:    */   String story1;
/*  18:    */   String story2;
/*  19:    */   float rarity;
/*  20:    */   HashMap<String, Float> feature_rarities;
/*  21: 28 */   boolean compared = false;
/*  22:    */   
/*  23:    */   public RarityVisualPanel(SimilarityProcessor similarityProcessor)
/*  24:    */   {
/*  25: 31 */     this.similarityProcessor = similarityProcessor;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void visualizeMatches(String story1, String story2)
/*  29:    */   {
/*  30: 36 */     removeAll();
/*  31:    */     
/*  32: 38 */     this.rarity = RarityUtil.rarity_in_common(story1, story2, this.similarityProcessor.getMemory());
/*  33:    */     
/*  34: 40 */     this.feature_rarities = RarityUtil.feature_rarity_score_in_common(story1, story2, this.similarityProcessor.getMemory());
/*  35:    */     
/*  36: 42 */     this.story1 = story1;
/*  37: 43 */     this.story2 = story2;
/*  38:    */     
/*  39: 45 */     setLayout(new BorderLayout());
/*  40:    */     
/*  41: 47 */     add(new JLabel("Total rarity: " + this.rarity), "First");
/*  42:    */     
/*  43: 49 */     this.compared = true;
/*  44:    */     
/*  45: 51 */     repaint();
/*  46: 52 */     updateUI();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void paintComponent(Graphics g)
/*  50:    */   {
/*  51: 58 */     super.paintComponent(g);
/*  52:    */     
/*  53: 60 */     int top = getHeight() / 6;
/*  54: 61 */     int left = getWidth() / 9;
/*  55:    */     
/*  56: 63 */     int width = getWidth() * 4 / 6;
/*  57: 64 */     int height = getHeight() * 7 / 9;
/*  58:    */     
/*  59:    */ 
/*  60: 67 */     g.setColor(Color.white);
/*  61: 68 */     g.fillRect(left, top, width, height);
/*  62:    */     
/*  63:    */ 
/*  64: 71 */     g.setColor(Color.black);
/*  65: 72 */     g.drawLine(left, top, left, top + height);
/*  66: 73 */     g.drawLine(left, top + height, left + width, top + height);
/*  67: 75 */     if (!this.compared) {
/*  68: 76 */       return;
/*  69:    */     }
/*  70: 79 */     ArrayList<String> features = new ArrayList();
/*  71: 80 */     for (String s : this.feature_rarities.keySet()) {
/*  72: 81 */       features.add(s);
/*  73:    */     }
/*  74: 86 */     int feature_width = width / features.size();
/*  75: 87 */     int start_x = left;
/*  76: 88 */     int feature_y = top + height + 14;
/*  77: 89 */     for (int i = 0; i < features.size(); i++) {
/*  78: 90 */       g.drawString((String)features.get(i), start_x + feature_width * i, feature_y);
/*  79:    */     }
/*  80: 93 */     g.setColor(Color.green);
/*  81: 95 */     for (int i = 0; i < features.size(); i++)
/*  82:    */     {
/*  83: 96 */       int rect_height = (int)(((Float)this.feature_rarities.get(this.feature_rarities.keySet().toArray()[i])).floatValue() * height);
/*  84: 97 */       g.fillRect(start_x + i * feature_width, top + height - rect_height, 
/*  85: 98 */         feature_width, rect_height);
/*  86:    */     }
/*  87:101 */     g.setColor(Color.black);
/*  88:103 */     for (int i = 0; i < features.size(); i++)
/*  89:    */     {
/*  90:104 */       int rect_height = (int)(((Float)this.feature_rarities.get(this.feature_rarities.keySet().toArray()[i])).floatValue() * height);
/*  91:105 */       g.drawRect(start_x + i * feature_width, top + height - rect_height, 
/*  92:106 */         feature_width, rect_height);
/*  93:    */     }
/*  94:110 */     g.setColor(Color.black);
/*  95:111 */     g.drawString("1.0", start_x - 20, top);
/*  96:112 */     g.drawLine(start_x, top, start_x + 5, top);
/*  97:113 */     g.drawString("0.0", start_x - 20, top + height);
/*  98:    */   }
/*  99:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.controlPanels.RarityVisualPanel
 * JD-Core Version:    0.7.0.1
 */