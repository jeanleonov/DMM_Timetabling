package com.timetabling.server.generating;

import com.timetabling.server.data.entities.tt.Version;

public interface Markable {

	double getMarkForVersion(Version version);
	
}
