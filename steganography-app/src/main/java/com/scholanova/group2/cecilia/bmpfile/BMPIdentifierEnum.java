package com.scholanova.group2.cecilia.bmpfile;

import java.util.HashSet;

public enum BMPIdentifierEnum {
	BM, BA, CI, CP, IC, PT; 

	public static HashSet<String> getEnums() {
		
		HashSet<String> values = new HashSet<String>();
		
		for (BMPIdentifierEnum e : BMPIdentifierEnum.values()) {
		    values.add(e.name());
		    //System.out.println("Inserted value: " + e.name());
		}
		
		return values;
	}
	
	public static void main(String[] args) {
		//getEnums();
	}
	
}
