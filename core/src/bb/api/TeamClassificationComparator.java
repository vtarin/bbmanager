package bb.api;

import java.util.Comparator;

public class TeamClassificationComparator implements Comparator<ClassifiedTeam> {
	@Override
	public int compare(ClassifiedTeam o1, ClassifiedTeam o2) {
		if (o1.points > o2.points) {
			return -1;
		} else if (o1.points < o2.points){
			return 1;
		}else{
			return 0;
		}
	}
}
