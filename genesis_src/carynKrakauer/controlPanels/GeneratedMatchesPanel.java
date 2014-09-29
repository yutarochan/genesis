/*   1:    */ package carynKrakauer.controlPanels;
/*   2:    */ 
/*   3:    */ import bridge.utils.Pair;
/*   4:    */ import carynKrakauer.ReflectionLevelMemory;
/*   5:    */ import carynKrakauer.SimilarityProcessor;
/*   6:    */ import carynKrakauer.VectorMatcher;
/*   7:    */ import carynKrakauer.generatedPatterns.ConceptPattern;
/*   8:    */ import carynKrakauer.generatedPatterns.ConceptPatternMatchWrapper;
/*   9:    */ import java.awt.BorderLayout;
/*  10:    */ import java.awt.event.ActionEvent;
/*  11:    */ import java.awt.event.ActionListener;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.HashMap;
/*  14:    */ import javax.swing.BoxLayout;
/*  15:    */ import javax.swing.ButtonGroup;
/*  16:    */ import javax.swing.JButton;
/*  17:    */ import javax.swing.JComboBox;
/*  18:    */ import javax.swing.JPanel;
/*  19:    */ import javax.swing.JRadioButton;
/*  20:    */ import javax.swing.JScrollPane;
/*  21:    */ import javax.swing.JSpinner;
/*  22:    */ import javax.swing.JTextPane;
/*  23:    */ import javax.swing.SpinnerModel;
/*  24:    */ import javax.swing.SpinnerNumberModel;
/*  25:    */ 
/*  26:    */ public class GeneratedMatchesPanel
/*  27:    */   extends JPanel
/*  28:    */   implements ActionListener
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = 1L;
/*  31:    */   private SimilarityProcessor similarityProcessor;
/*  32:    */   JButton find_matches_button;
/*  33:    */   ButtonGroup match_group;
/*  34:    */   JRadioButton find_highest_match_choice;
/*  35:    */   JRadioButton find_all_matches_choice;
/*  36:    */   JScrollPane scroll_pane;
/*  37:    */   JTextPane match_text_pane;
/*  38:    */   JComboBox story_selector;
/*  39:    */   SpinnerModel model;
/*  40:    */   JSpinner spinner;
/*  41:    */   
/*  42:    */   public GeneratedMatchesPanel(SimilarityProcessor similarityProcessor, JComboBox story_selector)
/*  43:    */   {
/*  44: 52 */     this.similarityProcessor = similarityProcessor;
/*  45: 53 */     this.story_selector = story_selector;
/*  46:    */     
/*  47: 55 */     setupMatchPane();
/*  48:    */   }
/*  49:    */   
/*  50:    */   private void setupMatchPane()
/*  51:    */   {
/*  52: 60 */     setLayout(new BorderLayout());
/*  53:    */     
/*  54: 62 */     JPanel selections_panel = new JPanel();
/*  55: 63 */     selections_panel.setLayout(new BoxLayout(selections_panel, 2));
/*  56:    */     
/*  57: 65 */     this.story_selector = new JComboBox();
/*  58: 66 */     selections_panel.add(this.story_selector);
/*  59: 67 */     this.similarityProcessor.addComboBox(this.story_selector);
/*  60:    */     
/*  61: 69 */     this.find_highest_match_choice = new JRadioButton("Highest Match Only");
/*  62: 70 */     this.find_highest_match_choice.addActionListener(this);
/*  63: 71 */     this.find_all_matches_choice = new JRadioButton("All Matches");
/*  64: 72 */     this.find_all_matches_choice.addActionListener(this);
/*  65:    */     
/*  66: 74 */     this.match_group = new ButtonGroup();
/*  67: 75 */     this.match_group.add(this.find_highest_match_choice);
/*  68: 76 */     this.match_group.add(this.find_all_matches_choice);
/*  69:    */     
/*  70: 78 */     this.find_highest_match_choice.setSelected(true);
/*  71:    */     
/*  72: 80 */     JPanel match_choice_pane = new JPanel();
/*  73: 81 */     match_choice_pane.setLayout(new BoxLayout(match_choice_pane, 3));
/*  74: 82 */     match_choice_pane.add(this.find_highest_match_choice);
/*  75: 83 */     match_choice_pane.add(this.find_all_matches_choice);
/*  76: 84 */     selections_panel.add(match_choice_pane);
/*  77:    */     
/*  78: 86 */     this.model = new SpinnerNumberModel(2, 
/*  79: 87 */       2, 
/*  80: 88 */       2147483647, 
/*  81: 89 */       1);
/*  82: 90 */     this.spinner = new JSpinner(this.model);
/*  83: 91 */     selections_panel.add(this.spinner);
/*  84:    */     
/*  85: 93 */     this.find_matches_button = new JButton("Find match(es)!");
/*  86: 94 */     this.find_matches_button.addActionListener(this);
/*  87: 95 */     selections_panel.add(this.find_matches_button);
/*  88:    */     
/*  89: 97 */     add(selections_panel, "North");
/*  90:    */     
/*  91: 99 */     this.match_text_pane = new JTextPane();
/*  92:100 */     this.match_text_pane.setEditable(false);
/*  93:101 */     this.scroll_pane = new JScrollPane(this.match_text_pane, 22, 31);
/*  94:    */     
/*  95:103 */     add(this.scroll_pane, "Center");
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void actionPerformed(ActionEvent ae)
/*  99:    */   {
/* 100:108 */     if (ae.getSource().equals(this.find_matches_button))
/* 101:    */     {
/* 102:110 */       int size = ((Integer)this.spinner.getValue()).intValue();
/* 103:    */       
/* 104:112 */       String story = (String)this.story_selector.getSelectedItem();
/* 105:113 */       String output = "Matches found for " + story + "\n\n";
/* 106:114 */       output = output + "Story Plot Units: ";
/* 107:115 */       for (ConceptPattern unit : this.similarityProcessor.getMemory().getPlotUnits(story, size)) {
/* 108:116 */         output = output + unit.asString() + ", ";
/* 109:    */       }
/* 110:121 */       output = output + "\n\n";
/* 111:    */       
/* 112:    */ 
/* 113:124 */       HashMap<String, ConceptPatternMatchWrapper> matches = VectorMatcher.highestMatchesPlotUnits((String)this.story_selector.getSelectedItem(), this.find_all_matches_choice.isSelected(), this.similarityProcessor.getMemory(), size);
/* 114:125 */       String highest_match = (String)VectorMatcher.highestMatchesPlotUnits(story, false, this.similarityProcessor.getMemory(), size).keySet().toArray()[0];
/* 115:127 */       if (((ConceptPatternMatchWrapper)matches.get(highest_match)).getMatchStrings().size() != 0)
/* 116:    */       {
/* 117:128 */         output = output.concat("  The highest match is " + highest_match + " because it has the following plot units in common: " + ((ConceptPatternMatchWrapper)matches.get(highest_match)).getMatchesAsString() + "\n\n");
/* 118:130 */         for (String s : matches.keySet())
/* 119:    */         {
/* 120:131 */           output = output.concat(new StringBuilder("  Story: ").append(s).toString()) + "\n";
/* 121:132 */           output = output.concat(new StringBuilder("    Matches: ").append(((ConceptPatternMatchWrapper)matches.get(s)).getMatchesAsString()).toString()) + "\n";
/* 122:133 */           output = output.concat(new StringBuilder("    Value: ").append(((ConceptPatternMatchWrapper)matches.get(s)).getValue()).toString()) + "\n";
/* 123:134 */           for (String matchString : ((ConceptPatternMatchWrapper)matches.get(s)).getMatchStrings())
/* 124:    */           {
/* 125:135 */             output = output.concat("       " + matchString + ":\n");
/* 126:136 */             ConceptPatternMatchWrapper matchWrapper = (ConceptPatternMatchWrapper)matches.get(s);
/* 127:137 */             ArrayList<Pair<ConceptPattern, ConceptPattern>> plotUnitMatches = matchWrapper.getMatches(matchString);
/* 128:138 */             for (Pair<ConceptPattern, ConceptPattern> pair : plotUnitMatches)
/* 129:    */             {
/* 130:139 */               ConceptPattern pu1 = (ConceptPattern)pair.car();
/* 131:140 */               output = output.concat("                " + story + ":\n");
/* 132:141 */               output = output.concat("                    " + pu1.asString() + ":\n");
/* 133:    */               
/* 134:143 */               ConceptPattern pu2 = (ConceptPattern)pair.cdr();
/* 135:144 */               output = output.concat("                " + s + ":\n");
/* 136:145 */               output = output.concat("                    " + pu2.asString() + ":\n");
/* 137:    */             }
/* 138:148 */             output = output.concat("\n\n");
/* 139:    */           }
/* 140:150 */           for (ConceptPattern unit : this.similarityProcessor.getMemory().getPlotUnits(s, size)) {
/* 141:151 */             output = output + unit.asString() + ", ";
/* 142:    */           }
/* 143:153 */           output = output.concat("\n\n");
/* 144:    */         }
/* 145:    */       }
/* 146:    */       else
/* 147:    */       {
/* 148:157 */         for (String title : this.similarityProcessor.getMemory().getStoryNames())
/* 149:    */         {
/* 150:158 */           output = output + title + ": ";
/* 151:159 */           for (ConceptPattern unit : this.similarityProcessor.getMemory().getPlotUnits(title, size)) {
/* 152:160 */             output = output + unit.asString() + ", ";
/* 153:    */           }
/* 154:162 */           output = output + "\n\n";
/* 155:    */         }
/* 156:    */       }
/* 157:165 */       this.match_text_pane.setText(output);
/* 158:    */     }
/* 159:    */   }
/* 160:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.controlPanels.GeneratedMatchesPanel
 * JD-Core Version:    0.7.0.1
 */