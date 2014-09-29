/*  1:   */ package adamKraft;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.Collections;
/*  6:   */ import java.util.List;
/*  7:   */ 
/*  8:   */ public class TransitionSequence
/*  9:   */   extends ArrayList<Transition>
/* 10:   */ {
/* 11: 9 */   public static final String[] GENERIC_ACTORS = { "A", "B", "C" };
/* 12:10 */   protected static final String[][] ACTOR_PERMUTATIONS = { { "A", "B", "C" }, { "B", "A", "C" } };
/* 13:   */   
/* 14:   */   protected String[][] getActorPermutations()
/* 15:   */   {
/* 16:12 */     return ACTOR_PERMUTATIONS;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String toString()
/* 20:   */   {
/* 21:15 */     return getCanonicalForm();
/* 22:   */   }
/* 23:   */   
/* 24:   */   public String render(String[] permutation)
/* 25:   */   {
/* 26:18 */     String s = "";
/* 27:19 */     for (Transition t : this) {
/* 28:20 */       s = s + t.render(permutation) + "\n";
/* 29:   */     }
/* 30:22 */     return s;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public String getCanonicalForm()
/* 34:   */   {
/* 35:25 */     ArrayList<String> forms = new ArrayList();
/* 36:26 */     for (String[] perm : getActorPermutations()) {
/* 37:27 */       forms.add(render(perm));
/* 38:   */     }
/* 39:29 */     Collections.sort(forms);
/* 40:30 */     return (String)forms.get(0);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public boolean isSubsequenceOf(TransitionSequence other)
/* 44:   */   {
/* 45:33 */     if (size() > other.size()) {
/* 46:34 */       return false;
/* 47:   */     }
/* 48:36 */     String cf = getCanonicalForm();
/* 49:37 */     for (int i = 0; i < other.size() - size() + 1; i++)
/* 50:   */     {
/* 51:38 */       List<Transition> l = other.subList(i, i + size());
/* 52:39 */       TransitionSequence tl = new TransitionSequence();
/* 53:40 */       tl.addAll(l);
/* 54:41 */       if (cf.equals(tl.getCanonicalForm())) {
/* 55:42 */         return true;
/* 56:   */       }
/* 57:   */     }
/* 58:45 */     return false;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public boolean equals(TransitionSequence other)
/* 62:   */   {
/* 63:49 */     return (other != null) && (size() == other.size()) && (isSubsequenceOf(other));
/* 64:   */   }
/* 65:   */   
/* 66:   */   public int hashCode()
/* 67:   */   {
/* 68:52 */     return getCanonicalForm().hashCode();
/* 69:   */   }
/* 70:   */   
/* 71:   */   public static void main(String[] args)
/* 72:   */   {
/* 73:55 */     TransitionSequence s1 = new TransitionSequence();
/* 74:56 */     s1.add(Transition.parse("90\tM0\t0\t1"));
/* 75:57 */     s1.add(Transition.parse("87\tR0,2\t0\t-1"));
/* 76:58 */     s1.add(Transition.parse("0\tR0,2\t?\t0"));
/* 77:59 */     s1.add(Transition.parse("0\tW0,2\t?\t0"));
/* 78:60 */     TransitionSequence s2 = new TransitionSequence();
/* 79:61 */     s2.add(Transition.parse("90\tM1\t0\t1"));
/* 80:62 */     s2.add(Transition.parse("87\tR1,2\t0\t-1"));
/* 81:63 */     s2.add(Transition.parse("0\tR1,2\t?\t0"));
/* 82:64 */     s2.add(Transition.parse("0\tW1,2\t?\t0"));
/* 83:65 */     if ((!s1.equals(s2)) && (s2.equals(s1))) {
/* 84:66 */       System.err.println("actor permutation equality failed");
/* 85:   */     } else {
/* 86:68 */       System.out.println("actor permutation equality: pass");
/* 87:   */     }
/* 88:70 */     TransitionSequence s3 = new TransitionSequence();
/* 89:71 */     s3.add(Transition.parse("90\tM0\t0\t1"));
/* 90:72 */     s3.add(Transition.parse("87\tR1,2\t0\t-1"));
/* 91:73 */     s3.add(Transition.parse("0\tR1,2\t?\t0"));
/* 92:74 */     s3.add(Transition.parse("0\tW1,2\t?\t0"));
/* 93:75 */     if ((s1.equals(s3)) || (s3.equals(s1))) {
/* 94:76 */       System.err.println("actor permutation inequality failed");
/* 95:   */     } else {
/* 96:78 */       System.out.println("actor permutation inequality: pass");
/* 97:   */     }
/* 98:80 */     TransitionSequence s4 = new TransitionSequence();
/* 99:81 */     s4.add(Transition.parse("90\tM1\t0\t1"));
/* :0:82 */     s4.add(Transition.parse("90\tM0\t0\t1"));
/* :1:83 */     s4.add(Transition.parse("87\tR0,2\t0\t-1"));
/* :2:84 */     s4.add(Transition.parse("0\tR0,2\t?\t0"));
/* :3:85 */     s4.add(Transition.parse("0\tW0,2\t?\t0"));
/* :4:86 */     if ((s1.isSubsequenceOf(s4)) && (s2.isSubsequenceOf(s4))) {
/* :5:87 */       System.out.println("subsequence: pass");
/* :6:   */     } else {
/* :7:89 */       System.err.println("subsequence: fail");
/* :8:   */     }
/* :9:91 */     if ((s4.isSubsequenceOf(s1)) || (s4.isSubsequenceOf(s2))) {
/* ;0:92 */       System.err.println("not subsequence: fail");
/* ;1:   */     } else {
/* ;2:94 */       System.out.println("not subsequence: pass");
/* ;3:   */     }
/* ;4:   */   }
/* ;5:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adamKraft.TransitionSequence
 * JD-Core Version:    0.7.0.1
 */