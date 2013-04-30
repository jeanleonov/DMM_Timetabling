package com.timetabling.client.ui.widgets.chosen;

import java.util.List;

public interface ChosenDataProvider {
	
	boolean isDataGroupable();
	
	boolean isAsync();
	
	boolean isMultipleSelect();
	
	List<String> getLastLoadedFilteredList();
	
	List<Pair<String, List<String>>> getLastLoadedFilteredMap();
	
	public static class Pair<T, S> {
		public T first;
		public S second;
		public Pair(T t, S s) {
			first = t;
			second = s;
		}
	}
}
