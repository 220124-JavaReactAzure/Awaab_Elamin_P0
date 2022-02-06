package com.revature.bank.models;

public abstract class AbsModel {
	public String printTofile(String[] elements) {
		StringBuilder buildFileString = new StringBuilder();
		for (String element : elements) {
			buildFileString.append(element).append(":");
		}
		return buildFileString.toString();
	}

}
