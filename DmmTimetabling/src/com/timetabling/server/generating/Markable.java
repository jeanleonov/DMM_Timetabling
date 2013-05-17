package com.timetabling.server.generating;

import com.timetabling.server.data.entities.timetabling.Version;

public interface Markable {

	float getMarkForVersion(Version version);
	
}
