package service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class HelperService {
	
	public String getCurrentDate() {
		return DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDateTime.now());
	}
}
