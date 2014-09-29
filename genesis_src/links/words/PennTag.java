/*   1:    */ package links.words;
/*   2:    */ 
/*   3:    */ import edu.mit.jwi.item.POS;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Map;
/*   7:    */ 
/*   8:    */ public enum PennTag
/*   9:    */ {
/*  10: 19 */   CC("CC", "Coordinating conjuction", null),  CD("CD", "Cardinal number", null),  DT("DT", "Determiner", null),  EX("EX", "Existential there", null),  FW(
/*  11: 20 */     "FW", "Foreign word", null),  IN("IN", "Preposition/subordinating conjunction", null),  JJ("JJ", "Adjective", 
/*  12: 21 */     POS.ADJECTIVE),  JJR("JJR", "Comparative adjective", "Adjective, comparative", POS.ADJECTIVE),  JJS(
/*  13: 22 */     "JJS", "Superlative adjective", "Adjective, superlative", POS.ADJECTIVE),  LS("LS", "List item", "List item marker", null),  MD(
/*  14: 23 */     "MD", "Modal", POS.VERB),  NN("NN", "Singular noun", "Noun, singular or mass", POS.NOUN),  NNS("NNS", 
/*  15: 24 */     "Plural noun", "Noun, plural", POS.NOUN),  NNP("NNP", "Singular proper noun", "Proper noun, singular", 
/*  16: 25 */     POS.NOUN),  NNPS("NNPS", "Plural  proper noun", "Proper noun, plural", POS.NOUN),  PDT("PDT", 
/*  17: 26 */     "Predeterminer", null),  POS("POS", "Possessive ending", null),  PRP("PRP", "Personal pronoun", null),  PRP$("PRP$", "Possessive pronoun", 
/*  18: 27 */     null),  RB("RB", "Adverb", POS.ADVERB),  RBR("RBR", "Comparative adverb", "Adverb, comparative", POS.ADVERB),  RBS("RBS", 
/*  19: 29 */     "Superlative adverb", "Adverb, superlative", POS.ADVERB),  RP("RP", "Particle", null),  SYM("SYM", "Symbol", 
/*  20: 30 */     "Symbol (mathematical or scientific)", null),  TO("TO", "to", null),  UH("UH", "Interjection", null),  VB("VB", "Verb, base form", 
/*  21: 31 */     POS.VERB),  VBD("VBD", "Verb, past tense", POS.VERB),  VBG("VBG", "Verb, gerund/present participle", 
/*  22: 32 */     POS.VERB),  VBN("VBN", "Verb, past participle", POS.VERB),  VBP("VBP", 
/*  23: 33 */     "Verb, non-3rd person singular present", POS.VERB),  VBZ("VBZ", "Verb, 3rd person singular present", 
/*  24: 34 */     POS.VERB),  WDT("WDT", "wh-determiner", null),  WP("WP", "wh-pronoun", null),  WP$("WP$", "Possessive wh-pronoun", null),  WRB(
/*  25: 35 */     "WRB", "wh-adverb", null),  POUND("#", "Pound sign", null),  DOLLAR("$", "Dollar sign", null),  PERIOD(".", "Period", 
/*  26: 36 */     "Sentence-final punctuation", null),  COMMA(",", "Comma", null),  COLON(":", "Colon or semi-colon", null),  BRACKET_LEFT("(", "Left parens", 
/*  27: 37 */     "Left bracket character", null),  BRACKET_RIGHT(")", "Right parens", "Right bracket character", null),  QUOTE_DOUBLE_STRAIGHT("\"", 
/*  28: 38 */     "Straight double quote", null),  QUOTE_SINGLE_LEFT("`", "Left single quote", "Left open single quote", null),  QUOTE_DOUBLE_LEFT("``", 
/*  29: 39 */     "Left double quote", "Left open double quote", null),  QUOTE_SINGLE_RIGHT("'", "Right single quote", "Right close single quote", null),  QUOTE_DOUBLE_RIGHT(
/*  30: 40 */     "''", "Right double quote", "Right close double quote", null),  ADJP("ADJP", "Adjective phrase", null),  ADVP("ADVP", "Adverb phrase", null),  NP(
/*  31: 41 */     "NP", "Noun phrase", null),  PP("PP", "Prepositional phrase", null),  S("S", "Simple declarative clause", null),  SBAR("SBAR", "S-Bar", 
/*  32: 42 */     "Clause introduced by subordinating conjuction or 0", null),  SBARQ("SBARQ", "S-Bar Question", 
/*  33: 43 */     "Direct question introduced by wh-word or wh-phrase", null),  SINV("SINV", "S-Inv", "Declaritive sentence with subject-aux inversion", 
/*  34: 44 */     null),  SQ("SQ", "SBARQ Subconsituent", "Subconstituent of SBARQ excluding wh-word or wh-phrase", null),  VP("VP", "Verb phrase", null),  WHADVP(
/*  35: 45 */     "WHADVP", "wh-adverb phrase", null),  WHNP("WHNP", "wh-noun phrase", null),  WHPP("WHPP", "wh-prepositional phrase", null),  UNKNOWN("X", 
/*  36: 46 */     "Unknown", "Consituent of unknown or uncertain category", null),  STAR("*", "Understood subject", 
/*  37: 47 */     "`Understood' subject of infinitive or imperative", null),  ZERO("0", "Zero variant", "Zero variant of that in subordinate clauses", null),  TRACE(
/*  38: 48 */     "T", "Trace", "Trace--marks position where moved wh-consituent is interpreted", null),  NIL("NIL", "Interpreted preposition location", 
/*  39: 49 */     "Marks position where preposition is interpreted in pied-piping contexts", null);
/*  40:    */   
/*  41:    */   private final String fTag;
/*  42:    */   private final String fName;
/*  43:    */   private final String fDesc;
/*  44:    */   private final POS fPos;
/*  45:    */   static final Map<String, PennTag> tagMap;
/*  46:    */   
/*  47:    */   static
/*  48:    */   {
/*  49: 58 */     Map<String, PennTag> hidden = new HashMap();
/*  50: 59 */     for (PennTag t : values()) {
/*  51: 60 */       hidden.put(t.getTag(), t);
/*  52:    */     }
/*  53: 61 */     tagMap = Collections.unmodifiableMap(hidden);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public static POS convert(String tag)
/*  57:    */   {
/*  58: 65 */     PennTag ptag = getTag(tag);
/*  59: 66 */     return ptag == null ? null : ptag.toWordnetPOS();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static PennTag getTag(String tag)
/*  63:    */   {
/*  64: 70 */     return (PennTag)tagMap.get(tag);
/*  65:    */   }
/*  66:    */   
/*  67:    */   private PennTag(String tag, String name, POS wordnetPOS)
/*  68:    */   {
/*  69: 74 */     this(tag, name, name, wordnetPOS);
/*  70:    */   }
/*  71:    */   
/*  72:    */   private PennTag(String tag, String name, String description, POS wordnetPOS)
/*  73:    */   {
/*  74: 78 */     this.fTag = tag;
/*  75: 79 */     this.fName = name;
/*  76: 80 */     this.fDesc = description;
/*  77: 81 */     this.fPos = wordnetPOS;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String getName()
/*  81:    */   {
/*  82: 86 */     return this.fName;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String getTag()
/*  86:    */   {
/*  87: 91 */     return this.fTag;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String getDescription()
/*  91:    */   {
/*  92: 96 */     return this.fDesc;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public POS toWordnetPOS()
/*  96:    */   {
/*  97:101 */     return this.fPos;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String toString()
/* 101:    */   {
/* 102:106 */     return this.fName;
/* 103:    */   }
/* 104:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     links.words.PennTag
 * JD-Core Version:    0.7.0.1
 */