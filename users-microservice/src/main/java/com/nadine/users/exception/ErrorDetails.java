package com.nadine.users.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
    public ErrorDetails(Date date, String message2, String description) {
		// TODO Auto-generated constructor stub
	}
	private Date timestamp;
    private String message;
    private String details;
}
