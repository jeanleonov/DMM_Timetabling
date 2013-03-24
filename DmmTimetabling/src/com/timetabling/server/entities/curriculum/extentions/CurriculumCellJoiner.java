package com.rosinka.tt.server.entities.curriculum.extentions;

import com.googlecode.objectify.annotation.Unindexed;
import com.rosinka.tt.server.base.data.entities.DatastoreLongEntity;

/** CurriculumRecordJoier join CurriculumRecord _s from different curriculums. <br>
 *  For example: <br>
 *  		curriculum of 1st course informatics contains "History of Ukraine"
 *  		and curriculum of 1st course applied mathematics contains "History of Ukraine"..
 * 			two different curriculum recodes, but one real lesson
 * 			with one teacher, one auditory and one time.  <br>
 * CurriculumRecords can be joined to 'Joiner' by referencing to this 'Joiner' */
public class CurriculumCellJoiner extends DatastoreLongEntity {

	@Unindexed private String description;
	
	public CurriculumCellJoiner() {
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
