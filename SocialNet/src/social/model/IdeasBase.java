package social.model;

import java.util.HashMap;
import java.util.Map;

public class IdeasBase {

	public IdeasBase() {
		infuences = new HashMap<Idea, Map<Idea, Integer>>();
	}

	Map<Idea, Map<Idea, Integer>> infuences;

	public Idea createIdea(String name) {
		Idea idea = new Idea(name);

		if (infuences.containsKey(name)) {
			throw new IllegalArgumentException("there is idea in such name in db!");
		} else {
			infuences.put(idea, new HashMap<Idea, Integer>());
		}
		return idea;
	}
	
	public Idea createIdea(String name, Idea paret) {
		Idea idea = createIdea(name);
		
		
		//kopiujemy wpływ
		
		if(infuences.containsKey(paret)){
			HashMap<Idea, Integer>  inner = new HashMap<Idea, Integer>();
			for (Idea a : inner.keySet()) {
				setInfuence(idea, a, inner.get(a));
			}
		}
		
		return idea;
	}

	public void setInfuence(Idea a, Idea b, Integer value){
		
		if(!infuences.containsKey(a)){
			HashMap<Idea, Integer> inner = new HashMap<Idea, Integer>();
			infuences.put(a, inner);
		}
		if(!infuences.containsKey(b)){
			HashMap<Idea, Integer> inner = new HashMap<Idea, Integer>();
			infuences.put(a, inner);
		}
		
		
		Map<Idea, Integer> inner;
		
		inner= infuences.get(a);
		inner.put(b, value);
		
		inner = infuences.get(b);
		inner.put(a, value);
	}
	
	
	public Integer getInfueneBeetween(Idea a, Idea b) {

		if (a == null)
			throw new NullPointerException("a is null!");

		if (b == null)
			throw new NullPointerException("b is null!");

		if (a.equals(b)) {
			return 0;
		}

		if (infuences.containsKey(a)) {
			Map<Idea, Integer> inner = infuences.get(a);
			if (inner.containsKey(b)) {
				return inner.get(b);
			} else {
				return 0;
			}
		} else {
			if (infuences.containsKey(b)) {
				throw new IllegalStateException(
						"bad database!, try add entry for:" + a.getName()
								+ " and " + b.getName());
			} else {
				return 0;
			}
		}

	}

}
