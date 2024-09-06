package com.vn.topcv.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class RequestValidation {

  public static Boolean isValidEmail(String email) {
	String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

	Pattern pattern = Pattern.compile(emailRegex);

	if (email == null) {
	  return false;
	}
	return pattern.matcher(email).matches();
  }

  public static Boolean isValidPassword(String password) {
	if (password == null) {
	  return false;
	}
	String passwordRegex = "^.{8,30}$";
	return password.matches(passwordRegex);
  }

  public static Boolean isValidName(String name) {
	return name != null && !name.isEmpty() && name.length() <= 30;
  }

  public static Boolean isValidPhoneNumber(String phoneNumber) {
	if (phoneNumber.length() < 10 || phoneNumber.length() > 11) {
	  return false;
	}

	return phoneNumber.matches("^[0-9]{10,11}$");
  }

  public static boolean isBoolean(String value) {
	String lowerValue = value.toLowerCase();

	return "true".equals(lowerValue) || "false".equals(lowerValue);
  }

  public static boolean isValidLongNumber(String number) {
	if (number == null || number.isEmpty()) {
	  return false;
	}

	try {
	  Long.parseLong(number);
	  return true;
	} catch (NumberFormatException e) {
	  return false;
	}
  }

  public static boolean isValidAndNotFutureLocalDate(String value) {
	if (value == null || value.trim().isEmpty()) {
	  return false;
	}

	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	try {
	  LocalDate birthDay = LocalDate.parse(value, dateFormatter);

	  if (birthDay.isAfter(LocalDate.now())) {
		return false;
	  }

	  return true;
	} catch (DateTimeParseException e) {
	  return false;
	}
  }

  public static boolean isValidLocalDate(String value) {
	if (value == null || value.trim().isEmpty()) {
	  return false;
	}

	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	try {
	  LocalDate birthDay = LocalDate.parse(value, dateFormatter);

	  return true;
	} catch (DateTimeParseException e) {
	  return false;
	}
  }

}
