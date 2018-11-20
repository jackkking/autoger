package com.coracle.generator.exception;

import java.util.List;

/**
 * 
 * @Copyright:
 * @Company:
 * @author
 * @version 1.0
 * 
 */
public class InvalidConfigurationException extends Exception {
	private static final long serialVersionUID = 1672594980L;
	private List<String> errors;

	public InvalidConfigurationException(List<String> errors) {
		this.errors = errors;
	}

	public List<String> getErrors() {
		return this.errors;
	}

	public String getMessage() {
		if ((this.errors != null) && (this.errors.size() > 0)) {
			return ((String) this.errors.get(0));
		}

		return super.getMessage();
	}
}