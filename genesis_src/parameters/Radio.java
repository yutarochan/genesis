/*  1:   */ package parameters;
/*  2:   */ 
/*  3:   */ import gui.JTransparentRadioButton;
/*  4:   */ import javax.swing.JRadioButton;
/*  5:   */ 
/*  6:   */ public class Radio
/*  7:   */ {
/*  8:14 */   public static final JRadioButton normalModeButton = new JRadioButton("Read", true);
/*  9:16 */   public static final JRadioButton tellStoryButton = new JRadioButton("Tell");
/* 10:18 */   public static final JRadioButton calculateSimilarityButton = new JRadioButton("Compare");
/* 11:20 */   public static final JRadioButton alignmentButton = new JTransparentRadioButton("Align");
/* 12:22 */   public static final JRadioButton spoonFeedButton = new JTransparentRadioButton("Spoon feed");
/* 13:24 */   public static final JRadioButton primingButton = new JTransparentRadioButton("Explain");
/* 14:26 */   public static final JRadioButton primingWithIntrospectionButton = new JTransparentRadioButton("Teach");
/* 15:28 */   public static final JRadioButton summarize = new JTransparentRadioButton("Summarize");
/* 16:30 */   public static final JRadioButton conceptSummary = new JTransparentRadioButton("Sumarize using concepts");
/* 17:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     parameters.Radio
 * JD-Core Version:    0.7.0.1
 */