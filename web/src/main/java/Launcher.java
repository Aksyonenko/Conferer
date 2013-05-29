import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;


public class Launcher {

	public static final String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	public static final DateFormat DATE_FORMATTER = new SimpleDateFormat(ISO_FORMAT);
	
	public static void main(String[] args) {
		TimeZone timeZone = TimeZone.getTimeZone("America/New_York");
		
		Calendar calendar = Calendar.getInstance(timeZone);
		calendar.set(Calendar.YEAR, 2013);
		calendar.set(Calendar.MONTH, 6);
		calendar.set(Calendar.YEAR, 2013);
		calendar.set(Calendar.DAY_OF_MONTH, 1);

		for (int field : new int[] {Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND}) {
			calendar.set(field, 0);
		}
		
		DATE_FORMATTER.setCalendar(calendar);
		System.out.println(DATE_FORMATTER.format(calendar.getTime()));
	}

}
