package comv2.aunwesha.lfutil;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class GenericUtil {

	public static String getClientIP(final HttpServletRequest request) {
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		return ipAddress;
	}

	public static <T> String listToString(final List<T> array,
			final String separator) {
		final StringBuffer buffer = new StringBuffer();
		if (!isListNullOrEmpty(array)) {
			for (final T data : array) {
				buffer.append((data == null) ? "" : data.toString());
				buffer.append(separator);
			}
		}
		final int length = buffer.toString().length();
		return buffer.toString().substring(0,
				(length == 0) ? 0 : (length - separator.length()));
	}

	public static String formatDoubleToString(final Double number) {
		String value = "";
		if (number != null) {
			final NumberFormat format = NumberFormat.getInstance();
			format.setGroupingUsed(false);
			format.setMaximumFractionDigits(2);
			value = format.format(number);
		}
		return value;
	}

	public static <T1> List<T1> deepCopyList(final List<T1> listToCopy) {
		final List<T1> newList = new ArrayList<T1>(listToCopy.size());
		for (final T1 data : listToCopy) {
			newList.add(data);
		}
		return newList;
	}

	private static <T> boolean isNull(final T obj) {
		return obj == null;
	}

	public static <T> boolean isEmptyString(final T obj) {
		return isNull((Object) obj) || "".equalsIgnoreCase(obj.toString());
	}

	public static <T> boolean hasString(final T obj) {
		return !isEmptyString((Object) obj);
	}

	public static String multiStringGenerator(final String str,
			final String separator, final int size) {
		String result = "";
		if (size > 0) {
			for (int ii = 1; ii < size; ++ii) {
				result = String.valueOf(result) + str + separator;
			}
			result = String.valueOf(result) + str;
		}
		return result;
	}

	public static <T> boolean isListNullOrEmpty(final List<T> list) {
		return list == null || list.size() <= 0;
	}

	public static <T> boolean hasListData(final List<T> list) {
		return !isListNullOrEmpty((List<T>) list);
	}

	public static <T> boolean isCollectionNullOrEmpty(
			final Collection<T> collection) {
		return collection == null || collection.size() <= 0;
	}

	public static <T> boolean hasCollectionData(final Collection<T> collection) {
		return !isCollectionNullOrEmpty((Collection<T>) collection);
	}

	public static <T1, T2> boolean isMapNullOrEmpty(final Map<T1, T2> map) {
		return map == null || map.isEmpty();
	}

	public static <T1, T2> boolean hasMapData(final Map<T1, T2> map) {
		return !isMapNullOrEmpty((Map<T1, T2>) map);
	}

	public static Integer convertStringToInt(final String stringValue) {
		Integer intValue = null;
		if (!isEmptyString(stringValue)) {
			try {
				intValue = Integer.valueOf(stringValue);
			} catch (NumberFormatException ne) {
				intValue = null;
			}
		}
		return intValue;
	}

	public static Long convertStringToLong(final String stringValue) {
		Long intValue = null;
		if (!isEmptyString(stringValue)) {
			try {
				intValue = Long.valueOf(stringValue);
			} catch (NumberFormatException ne) {
				intValue = null;
			}
		}
		return intValue;
	}

	public static boolean convertIntStatusToBoolean(final Integer intValue) {
		return intValue != null && intValue == 1;
	}

	public static boolean convertStringToBoolean(final String stringVal) {
		return !isEmptyString(stringVal) && ("Y".equalsIgnoreCase(stringVal) || "true".equalsIgnoreCase(stringVal));
	}

	public static String convertBooleanToString(final Boolean booleanVal) {
		return (booleanVal != null && booleanVal) ? "Y" : "N";
	}

	public static <T extends Comparable<T>> boolean inList(final T checkData,
			final T... dataList) {
		boolean found = false;
		if (checkData != null) {
			for (final T data : dataList) {
				if (data != null && data.compareTo(checkData) == 0) {
					found = true;
					break;
				}
			}
		}
		return found;
	}

	public static <T> boolean inList(final T checkData, final List<T> dataList) {
		boolean found = false;
		if (checkData != null) {
			for (final T data : dataList) {
				if (data != null && data.equals(checkData)) {
					found = true;
					break;
				}
			}
		}
		return found;
	}

	public static List<Integer> stringToIntegerList(final String strData,
			final String separator) {
		final List<Integer> retList = new ArrayList<Integer>();
		if (!isEmptyString(strData)) {
			final String[] csvArray = strData.split(separator);
			String[] array;
			for (int length = (array = csvArray).length, i = 0; i < length; ++i) {
				final String data = array[i];
				retList.add(convertStringToInt(data.trim()));
			}
		}
		return retList;
	}

	public static List<String> stringToStringList(final String csvData,
			final String separator) {
		return stringToStringList(csvData, separator, true);
	}

	public static List<String> stringToStringList(final String csvData,
			final String separator, final boolean trim) {
		final List<String> retList = new ArrayList<String>();
		if (!isEmptyString(csvData)) {
			final String[] csvArray = csvData.split(Pattern.quote(separator));
			String[] array;
			for (int length = (array = csvArray).length, i = 0; i < length; ++i) {
				final String data = array[i];
				retList.add(trim ? data.trim() : data);
			}
		}
		return retList;
	}

	public static boolean equalInteger(final Integer a, final Integer b) {
		boolean check = false;
		check = ((a == null && b == null) || (!(a == null ^ b == null) && a == (int) b));
		return check;
	}

	public static boolean isPastDate(final Date date) {
		final Calendar today = Calendar.getInstance();
		today.set(11, 0);
		today.set(10, 0);
		today.set(9, 0);
		today.set(12, 0);
		today.set(13, 0);
		today.set(14, 0);
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(11, 0);
		cal.set(10, 0);
		cal.set(9, 0);
		cal.set(12, 0);
		cal.set(13, 0);
		cal.set(14, 0);
		return today.after(cal);
	}

	public static boolean isFutureDate(final Date date) {
		final Calendar today = Calendar.getInstance();
		today.set(11, 0);
		today.set(10, 0);
		today.set(9, 0);
		today.set(12, 0);
		today.set(13, 0);
		today.set(14, 0);
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(11, 0);
		cal.set(10, 0);
		cal.set(9, 0);
		cal.set(12, 0);
		cal.set(13, 0);
		cal.set(14, 0);
		return today.before(cal);
	}

	public static List<String> unrollDBStringOuter(final String stringData) {
		final List<String> resultList = new ArrayList<String>();
		if (isEmptyString(stringData) || stringData.charAt(0) != '['
				|| stringData.charAt(stringData.length() - 1) != ']') {
			return resultList;
		}
		StringBuffer buffer = new StringBuffer();
		boolean start = false;
		boolean end = true;
		char[] charArray;
		for (int length = (charArray = stringData.toCharArray()).length, i = 0; i < length; ++i) {
			final Character character = charArray[i];
			if (end && !start && character == '[') {
				start = true;
				end = false;
			}
			if (start) {
				buffer.append(character);
			}
			if (start && !end && character == ']') {
				end = true;
				start = false;
			}
			if (end) {
				resultList.add(buffer.toString());
				buffer = new StringBuffer();
			}
		}
		return resultList;
	}

	public static List<String> unrollDBStringInner(final String stringData) {
		final List<String> resultList = new ArrayList<String>();
		if (isEmptyString(stringData) || stringData.charAt(0) != '['
				|| stringData.charAt(stringData.length() - 1) != ']') {
			return resultList;
		}
		final String data = stringData.substring(1, stringData.length() - 1);
		final StringTokenizer st = new StringTokenizer(data, "^^");
		while (st.hasMoreElements()) {
			resultList.add(st.nextElement().toString());
		}
		return resultList;
	}

	public static List<List<String>> unrollDBString(final String dbString) {
		final List<List<String>> resultList = new ArrayList<List<String>>();
		final List<String> innerList = unrollDBStringOuter(dbString);
		for (final String stringData : innerList) {
			final List<String> dataList = unrollDBStringInner(stringData);
			resultList.add(dataList);
		}
		return resultList;
	}

	public static String escapeScript(final String stringValue) {
		String result = stringValue;
		if (!isEmptyString(stringValue)) {
			result = result.replaceAll("<[^>]*>", "");
			result = result.replaceAll("%3C", "");
			result = result.replaceAll("%3D", "");
		}
		return result;
	}

	public static long secondsSince(final Date fromDate, final Date toDate) {
		final long miliSec = toDate.getTime() - fromDate.getTime();
		return miliSec / 1000L;
	}

	public static long hoursSince(final Date fromDate, final Date toDate) {
		final Long seconds = secondsSince(fromDate, toDate);
		return seconds / 3600L;
	}

	public static long secondsSinceNow(final Date fromDate) {
		final long miliSec = new Date().getTime() - fromDate.getTime();
		return miliSec / 1000L;
	}

	public static long hoursSinceNow(final Date fromDate) {
		final Long seconds = secondsSinceNow(fromDate);
		return seconds / 3600L;
	}

	public static Integer getIntegerPart(final Double doubleNumber,
			final boolean replaceZero) {
		Integer decimalPart = null;
		final String[] splitArr = doubleNumber.toString().split("\\.");
		if (splitArr != null && splitArr.length > 0) {
			decimalPart = Integer.valueOf(splitArr[1]);
			if (decimalPart == 0 && replaceZero) {
				decimalPart = Integer.valueOf(splitArr[0]);
			}
		} else {
			decimalPart = ((splitArr == null) ? 0 : Integer
					.valueOf(splitArr[0]));
		}
		return decimalPart;
	}

	public static Integer maxInteger(final Integer number1,
			final Integer number2) {
		Integer max = null;
		if (number1 != null) {
			if (number2 != null) {
				max = Math.max(number1, number2);
			} else {
				max = number1;
			}
		} else {
			max = number2;
		}
		return max;
	}

	public static InternetAddress[] prepareInternetAddress(
			final List<String> mailIdList) throws AddressException {
		List<InternetAddress> internetAddressList = null;
		if (!isListNullOrEmpty(mailIdList)) {
			internetAddressList = new ArrayList<InternetAddress>();
			for (final String mailId : mailIdList) {
				internetAddressList.add(new InternetAddress(mailId));
			}
		}
		return (InternetAddress[]) (isListNullOrEmpty(internetAddressList) ? null
				: ((InternetAddress[]) internetAddressList
						.toArray(new InternetAddress[0])));
	}

	public static String getMD5Hash(final String data) {
		String hash = null;
		try {
			final MessageDigest messageDigest = MessageDigest
					.getInstance("MD5");
			final BigInteger bigInt = new BigInteger(1,
					messageDigest.digest(data.getBytes("UTF-8")));
			for (hash = bigInt.toString(); hash.length() < 32; hash = "0"
					.concat(hash)) {
			}
		} catch (NoSuchAlgorithmException e) {
			hash = data;
		} catch (UnsupportedEncodingException e2) {
			hash = data;
		}
		return hash;
	}

	public static String prepareDataForExcel(final String data,
			final String separator) {
		String temp = data;
		if (!isEmptyString(data) && !isEmptyString(separator)) {
			temp = data.replaceAll(separator,
					separator.concat(System.getProperty("line.separator")));
		}
		return temp;
	}

	public static String prepareDataForExcel(final String data) {
		return prepareDataForExcel(data, ",");
	}

	public static String wrapString(final String data,
			final String wrapperLeft, final String wrapperRight) {
		return wrapperLeft.concat(data).concat(wrapperRight);
	}

	public static Map<String, String> stringToMap(final String csvData,
			final String keySeparator, final String mapSeparator) {
		final Map<String, String> retMap = new HashMap<String, String>();
		final List<String> retList = new ArrayList<String>();
		if (!isEmptyString(csvData)) {
			final String[] csvArray = csvData
					.split(Pattern.quote(mapSeparator));
			String[] array;
			for (int length = (array = csvArray).length, i = 0; i < length; ++i) {
				final String data = array[i];
				retList.add(data);
			}
		}
		for (final String mapData : retList) {
			final String[] mapEntry = mapData
					.split(Pattern.quote(keySeparator));
			retMap.put(mapEntry[0], mapEntry[1]);
		}
		return retMap;
	}

	public static <T> T nvl(final T object, final T nullValue) {
		return nvl(object, nullValue, false);
	}

	public static <T> T nvl(final T object, final T nullValue,
			final boolean emptyStringIsNull) {
		if (isNull(object) || (emptyStringIsNull && isEmptyString(object))) {
			return nullValue;
		}
		return object;
	}
	
	public static final String generateJsonString(GridModificationStatus status,String message){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> returnMap=new HashMap<>();
		returnMap.put(status.getStatus(), message);
		String jsonString=null;
		try {
			jsonString = mapper.writeValueAsString(returnMap);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return jsonString;
	}

}
