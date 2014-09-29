/*   1:    */ package matching;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import matchers.EntityMatcher;
/*   7:    */ import matchers.original.BasicMatcherOriginal;
/*   8:    */ import matchers.representations.EntityMatchResult;
/*   9:    */ import org.junit.Assert;
/*  10:    */ import org.junit.Test;
/*  11:    */ import start.Generator;
/*  12:    */ import translator.Translator;
/*  13:    */ import utils.Mark;
/*  14:    */ 
/*  15:    */ public class MatcherConsistencyTests
/*  16:    */ {
/*  17:    */   public static final String simple0 = "Sally and Mary are cats.";
/*  18:    */   public static final String simple1 = "John and Gary are dogs.";
/*  19:    */   public static final String simple2 = "John likes Sally.";
/*  20:    */   public static final String simple3 = "Gary likes Mary.";
/*  21:    */   public static final String simple4 = "Mary likes Sally.";
/*  22:    */   public static final String partialTypeless0 = "Neil and Sarah are people.";
/*  23:    */   public static final String partialTypeless1 = "Neil likes Sarah.";
/*  24:    */   public static final String partialTypeless2 = "Bob likes Molly.";
/*  25:    */   public static final String typeless0 = "Doug likes Doug.";
/*  26:    */   public static final String typeless1 = "Jake likes Jake.";
/*  27:    */   public static final String sovereignty0 = "Macbeth, Duncan, Macduff, and George are persons.";
/*  28:    */   public static final String sovereignty1 = "Macbeth is not the king.";
/*  29:    */   public static final String sovereignty2 = "Duncan is the king.";
/*  30:    */   public static final String sovereignty3 = "Macduff is not the king.";
/*  31:    */   public static final String sovereignty4 = "George is the king.";
/*  32:    */   public static final String hansel0 = "Hansel and Duncan are persons.";
/*  33:    */   public static final String hansel1 = "The candy_house eats Hansel";
/*  34:    */   public static final String hansel2 = "Duncan eats Hansel";
/*  35:    */   public static final String hansel3 = "Hansel eats Duncan";
/*  36:    */   
/*  37:    */   public static void testMatching(String[] sentences1, String[] sentences2, int match1, int match2, boolean match)
/*  38:    */   {
/*  39: 29 */     testMatching(sentences1, sentences2, match1, match2, match, false);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public static void testMatching(String[] sentences1, String[] sentences2, int match1, int match2, boolean match, boolean old_in_error)
/*  43:    */   {
/*  44: 32 */     Translator translator = Translator.getTranslator();
/*  45: 33 */     Generator generator = Generator.getGenerator();
/*  46: 34 */     generator.setStoryMode();
/*  47:    */     
/*  48: 36 */     List<Entity> story1 = new ArrayList();
/*  49: 37 */     String[] arrayOfString = sentences1;int j = sentences1.length;
/*  50: 37 */     for (int i = 0; i < j; i++)
/*  51:    */     {
/*  52: 37 */       String sentence = arrayOfString[i];
/*  53:    */       try
/*  54:    */       {
/*  55: 39 */         Entity elt = translator.translate(sentence).getElement(0);
/*  56: 40 */         story1.add(elt);
/*  57:    */       }
/*  58:    */       catch (Exception e)
/*  59:    */       {
/*  60: 43 */         Assert.fail("Parsing Problem!");
/*  61:    */       }
/*  62:    */     }
/*  63: 47 */     generator.setStoryMode();
/*  64: 48 */     generator.flush();
/*  65:    */     
/*  66: 50 */     List<Entity> story2 = new ArrayList();
/*  67: 51 */     for (String sentence : sentences2) {
/*  68:    */       try
/*  69:    */       {
/*  70: 53 */         Entity elt = translator.translate(sentence).getElement(0);
/*  71: 54 */         story2.add(elt);
/*  72:    */       }
/*  73:    */       catch (Exception e)
/*  74:    */       {
/*  75: 57 */         Assert.fail("Parsing Problem!");
/*  76:    */       }
/*  77:    */     }
/*  78: 62 */     BasicMatcherOriginal bm = BasicMatcherOriginal.getBasicMatcher();
/*  79: 63 */     EntityMatcher em = new EntityMatcher();
/*  80:    */     
/*  81: 65 */     Entity elt1 = (Entity)story1.get(match1);
/*  82: 66 */     Entity elt2 = (Entity)story2.get(match2);
/*  83:    */     
/*  84: 68 */     EntityMatchResult emr = em.match(elt1, elt2);
/*  85: 69 */     bm.match(elt1, elt2);
/*  86:    */     
/*  87: 71 */     Assert.assertTrue("EntityMatcher Match Result Failure on\n" + 
/*  88: 72 */       elt1 + elt2 + "\n" + 
/*  89: 73 */       em.match(elt1, elt2) + "\n" + 
/*  90: 74 */       elt1.getSubject().getPrimedThread() + "\n" + 
/*  91: 75 */       elt2.getSubject().getPrimedThread() + "\n" + 
/*  92: 76 */       em.match(elt1, elt2).isMatch() + "!=" + match, em.match(elt1, elt2).isMatch() == match);
/*  93: 77 */     if (!old_in_error) {
/*  94: 78 */       Assert.assertTrue(
/*  95:    */       
/*  96:    */ 
/*  97:    */ 
/*  98:    */ 
/*  99: 83 */         "BasicMatch NOT EQUIVALENT TO EntityMatcher" + elt1 + elt2 + "\n" + em.match(elt1, elt2) + "\n" + elt1.getSubject().getPrimedThread() + "\n" + elt2.getSubject().getPrimedThread() + "\n" + em.match(elt1, elt2).isMatch() + "!=" + (bm.match(elt1, elt2) != null), em.match(elt1, elt2).isMatch() == (bm.match(elt1, elt2) != null));
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   @Test(timeout=20000L)
/* 104:    */   public void testSimpleMatching()
/* 105:    */   {
/* 106: 96 */     Mark.say(
/* 107:    */     
/* 108:    */ 
/* 109:    */ 
/* 110:    */ 
/* 111:    */ 
/* 112:    */ 
/* 113:    */ 
/* 114:    */ 
/* 115:    */ 
/* 116:    */ 
/* 117:107 */       new Object[] { "Testing basic matching..." });testMatching(new String[] { "Sally and Mary are cats.", "John and Gary are dogs.", "John likes Sally.", "Gary likes Mary.", "Mary likes Sally." }, new String[] { "Sally and Mary are cats.", "John and Gary are dogs.", "John likes Sally.", "Gary likes Mary.", "Mary likes Sally." }, 2, 3, true);Mark.say(new Object[] { "Testing basic match fail..." });testMatching(new String[] { "Sally and Mary are cats.", "John and Gary are dogs.", "John likes Sally.", "Gary likes Mary.", "Mary likes Sally." }, new String[] { "Sally and Mary are cats.", "John and Gary are dogs.", "John likes Sally.", "Gary likes Mary.", "Mary likes Sally." }, 1, 3, false);
/* 118:    */   }
/* 119:    */   
/* 120:    */   @Test(timeout=20000L)
/* 121:    */   public void testTypeMismatch()
/* 122:    */   {
/* 123:111 */     Mark.say(
/* 124:    */     
/* 125:    */ 
/* 126:    */ 
/* 127:    */ 
/* 128:116 */       new Object[] { "Testing type mismatch..." });testMatching(new String[] { "Sally and Mary are cats.", "John and Gary are dogs.", "John likes Sally.", "Gary likes Mary.", "Mary likes Sally." }, new String[] { "Sally and Mary are cats.", "John and Gary are dogs.", "John likes Sally.", "Gary likes Mary.", "Mary likes Sally." }, 2, 4, false);
/* 129:    */   }
/* 130:    */   
/* 131:    */   @Test(timeout=20000L)
/* 132:    */   public void testPartialTypelessMatch()
/* 133:    */   {
/* 134:124 */     Mark.say(
/* 135:    */     
/* 136:    */ 
/* 137:    */ 
/* 138:    */ 
/* 139:129 */       new Object[] { "Testing partial type-less match..." });testMatching(new String[] { "Neil and Sarah are people.", "Neil likes Sarah." }, new String[] { "Bob likes Molly." }, 1, 0, false);
/* 140:    */   }
/* 141:    */   
/* 142:    */   @Test(timeout=20000L)
/* 143:    */   public void testTypelessMatch()
/* 144:    */   {
/* 145:136 */     Mark.say(
/* 146:    */     
/* 147:    */ 
/* 148:    */ 
/* 149:    */ 
/* 150:141 */       new Object[] { "Testing type-less match..." });testMatching(new String[] { "Doug likes Doug." }, new String[] { "Jake likes Jake." }, 0, 0, false);
/* 151:    */   }
/* 152:    */   
/* 153:    */   @Test(timeout=20000L)
/* 154:    */   public void testNegationMatching()
/* 155:    */   {
/* 156:151 */     Mark.say(
/* 157:    */     
/* 158:    */ 
/* 159:    */ 
/* 160:    */ 
/* 161:    */ 
/* 162:    */ 
/* 163:    */ 
/* 164:    */ 
/* 165:    */ 
/* 166:    */ 
/* 167:    */ 
/* 168:    */ 
/* 169:    */ 
/* 170:    */ 
/* 171:    */ 
/* 172:    */ 
/* 173:    */ 
/* 174:    */ 
/* 175:    */ 
/* 176:171 */       new Object[] { "Testing double positives..." });testMatching(new String[] { "Macbeth, Duncan, Macduff, and George are persons.", "Duncan is the king." }, new String[] { "Macbeth, Duncan, Macduff, and George are persons.", "George is the king." }, 1, 1, true);Mark.say(new Object[] { "Testing double negative..." });testMatching(new String[] { "Macbeth, Duncan, Macduff, and George are persons.", "Macbeth is not the king." }, new String[] { "Macbeth, Duncan, Macduff, and George are persons.", "Macduff is not the king." }, 1, 1, true);Mark.say(new Object[] { "Testing negative-positive..." });testMatching(new String[] { "Macbeth, Duncan, Macduff, and George are persons.", "Macbeth is not the king." }, new String[] { "Macbeth, Duncan, Macduff, and George are persons.", "Duncan is the king." }, 1, 1, false);Mark.say(new Object[] { "Testing positive-negative..." });testMatching(new String[] { "Macbeth, Duncan, Macduff, and George are persons.", "George is the king." }, new String[] { "Macbeth, Duncan, Macduff, and George are persons.", "Macduff is not the king." }, 1, 1, false);
/* 177:    */   }
/* 178:    */   
/* 179:    */   @Test
/* 180:    */   public void testThreadMatching()
/* 181:    */   {
/* 182:180 */     Mark.say(
/* 183:    */     
/* 184:    */ 
/* 185:    */ 
/* 186:    */ 
/* 187:    */ 
/* 188:    */ 
/* 189:    */ 
/* 190:    */ 
/* 191:    */ 
/* 192:    */ 
/* 193:    */ 
/* 194:    */ 
/* 195:    */ 
/* 196:    */ 
/* 197:    */ 
/* 198:    */ 
/* 199:197 */       new Object[] { "Testing thread sensitive matching..." });testMatching(new String[] { "Hansel and Duncan are persons.", "The candy_house eats Hansel", "Duncan eats Hansel", "Hansel eats Duncan" }, new String[] { "Hansel and Duncan are persons.", "The candy_house eats Hansel", "Duncan eats Hansel", "Hansel eats Duncan" }, 2, 3, true);testMatching(new String[] { "Hansel and Duncan are persons.", "The candy_house eats Hansel", "Duncan eats Hansel", "Hansel eats Duncan" }, new String[] { "Hansel and Duncan are persons.", "The candy_house eats Hansel", "Duncan eats Hansel", "Hansel eats Duncan" }, 3, 2, true);testMatching(new String[] { "Hansel and Duncan are persons.", "The candy_house eats Hansel", "Duncan eats Hansel", "Hansel eats Duncan" }, new String[] { "Hansel and Duncan are persons.", "The candy_house eats Hansel", "Duncan eats Hansel", "Hansel eats Duncan" }, 1, 2, true, true);testMatching(new String[] { "Hansel and Duncan are persons.", "The candy_house eats Hansel", "Duncan eats Hansel", "Hansel eats Duncan" }, new String[] { "Hansel and Duncan are persons.", "The candy_house eats Hansel", "Duncan eats Hansel", "Hansel eats Duncan" }, 2, 1, false, true);
/* 200:    */   }
/* 201:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matching.MatcherConsistencyTests
 * JD-Core Version:    0.7.0.1
 */