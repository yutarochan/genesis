/*  1:   */ package constants;
/*  2:   */ 
/*  3:   */ import java.util.Arrays;
/*  4:   */ import java.util.List;
/*  5:   */ 
/*  6:   */ public abstract interface RecognizedRepresentations
/*  7:   */ {
/*  8:12 */   public static final Object PICTURE_FILE_NAME = "picture-filename";
/*  9:13 */   public static final Object MOVIE_FILE_NAME = "movie-filename";
/* 10:14 */   public static final Object TITLE_TAG = "title";
/* 11:20 */   public static final Object PATH_ELEMENT_THING = "path-element";
/* 12:21 */   public static final Object PATH_THING = "path";
/* 13:22 */   public static final Object THREAD_THING = "thread-memory";
/* 14:23 */   public static final Object TRAJECTORY_THING = "trajectory";
/* 15:24 */   public static final Object CAUSE_THING = "cause";
/* 16:25 */   public static final Object ROLE_THING = "roles";
/* 17:26 */   public static final Object MENTAL_STATE_THING = "mental-state";
/* 18:27 */   public static final Object QUESTION_THING = "question";
/* 19:28 */   public static final Object ANSWER_THING = "answer";
/* 20:29 */   public static final Object TRANSFER_THING = "transfer";
/* 21:30 */   public static final Object GENERIC_THING = "thing";
/* 22:31 */   public static final Object FORCE_THING = "force";
/* 23:32 */   public static final Object GEOMETRY_THING = "geometry";
/* 24:33 */   public static final Object BLOCK_THING = "blockFrame";
/* 25:35 */   public static final Object GROUND_GEOMETRY_THING = "groundGeometry";
/* 26:36 */   public static final Object CA = "ca";
/* 27:38 */   public static final Object TIME_REPRESENTATION = "time-representation";
/* 28:39 */   public static final Object PLACE_REPRESENTATION = "place-representation";
/* 29:40 */   public static final Object TRANSITION_REPRESENTATION = "transition-representation";
/* 30:41 */   public static final Object SOCIAL_REPRESENTATION = "social-representation";
/* 31:42 */   public static final Object ACTION_REPRESENTATION = "action-representation";
/* 32:45 */   public static final List<Object> ALL_KNOWN_REPS = Arrays.asList(new Object[] { PICTURE_FILE_NAME, 
/* 33:46 */     PATH_ELEMENT_THING, PATH_THING, PLACE_REPRESENTATION, THREAD_THING, TRAJECTORY_THING, CAUSE_THING, QUESTION_THING, 
/* 34:47 */     ANSWER_THING, TRANSITION_REPRESENTATION, GENERIC_THING, FORCE_THING, GEOMETRY_THING, BLOCK_THING, TIME_REPRESENTATION, 
/* 35:48 */     GROUND_GEOMETRY_THING, ROLE_THING, ACTION_REPRESENTATION, SOCIAL_REPRESENTATION, MENTAL_STATE_THING });
/* 36:49 */   public static final List<Object> ALL_THING_REPS = Arrays.asList(new Object[] { PATH_ELEMENT_THING, PATH_THING, 
/* 37:50 */     PLACE_REPRESENTATION, THREAD_THING, TRAJECTORY_THING, CAUSE_THING, ROLE_THING, ACTION_REPRESENTATION, SOCIAL_REPRESENTATION, MENTAL_STATE_THING, QUESTION_THING, ANSWER_THING, TRANSITION_REPRESENTATION, 
/* 38:51 */     TRANSFER_THING, GENERIC_THING, FORCE_THING, GEOMETRY_THING, BLOCK_THING, TIME_REPRESENTATION, GROUND_GEOMETRY_THING, CA });
/* 39:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     constants.RecognizedRepresentations
 * JD-Core Version:    0.7.0.1
 */