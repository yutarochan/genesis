the disambiguation folder holds tom's disambiguation work. 
Disambiguator.java is where the work is. 
I brought in a copy of Gauntlet.java which has the disambiguator wired in. It is unchanged, save a few wirings. 
The other classes are copied without edit. They are only here to make references work. 
-------------------------
ISSUES:
	1. getNeighbors() scores based on all threads, but when we determine a proper word sense in a given trajectory, 
	why consider the other threads? For example, in "The hawk flew", we know "hawk" is a bird. If we recall this 
	trajectory from memory, we should not consider the other senses of "hawk"
	2. Does the disambiguator need to go /before/ the transformer? The transformer uses verb threads. It would be 
	good for the transformer to know the correct sense of the verb before it does its thing. 

TODO: 
	1. Write nice text output, so the process is clear. Display scores as percent likelihoods, rather than 
	   numbers. 
	2. Only pick "unknown word" thread as last resort (done)
	3. Look into multpile "inputs" for a single frame. 
	4. (ongoing): get the disambiguator running for other frames. Possibly do transitions next. 
		Transitions might be harder. Only certain classes of things "fly", but does the same logic hold 
		for "appear" "disappear" etc? It seems to me that anything can appear and disappear. But maybe 
		some threads represent objects more likely to transition...  UPDATE: only working on trajectories for now. 
	5. When a noun has been disambiguated, send only the primed thread to memory (not the entire bundle). Make sure to 
	   create a new thing (maybe?). Just make sure the word is not stripped of its bundle for future sentences. 
	6. 



24.1.08
	-Disambiguator2 works on trajectories, but calls getNeighbors() on a skeleton trajectory (with just the verb filled in)
	to find similar trajectories. 

19.1.08
	- The disambiguator works with trajectories. 
	- Created this folder and file. 
	