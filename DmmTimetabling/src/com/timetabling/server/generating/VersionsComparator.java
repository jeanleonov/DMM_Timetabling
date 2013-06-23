package com.timetabling.server.generating;

import java.util.Comparator;
import java.util.Map;

public class VersionsComparator implements Comparator<Long> {
	
	private Map<Long, Float> versionsMarks;
	
	public VersionsComparator(Map<Long, Float> versionsMarks) {
		this.versionsMarks = versionsMarks;
	}

	@Override
	public int compare(Long ver1, Long ver2) {
		float mark1 = versionsMarks.get(ver1);
		float mark2 = versionsMarks.get(ver2);
		if (mark1 > mark2)
			return 1;
		if (mark1 == mark2)
			return 0;
		return -1;
	}

}
