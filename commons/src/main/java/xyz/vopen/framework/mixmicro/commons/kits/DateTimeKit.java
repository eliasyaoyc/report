/**
 * MIT License
 *
 * <p>Copyright (c) 2020 mixmicro
 *
 * <p>Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * <p>The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * <p>THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package xyz.vopen.framework.mixmicro.commons.kits;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * {@link DateTimeKit} The JSR-310 Date time kit implementation.
 *
 * <p>Java old date and time classes are formatted as strings or strings are parsed into date and
 * time classes based on {@link java.text.DateFormat} and {@link java.text.SimpleDateFormat}.
 *
 * <p>The basic functions of <code>SimpleDateFormat</code> are complete,but there ars two problems:
 *
 * <ul>
 *   <li>The efficiency of parsing and formatting is relatively low. The reason is that it relies on
 *       the inherently inefficiently {@link java.util.Calendar}.There are a large number of string
 *       or character judgment and conversion codes inside,so a lot of loops and switch blocks are
 *       used And so on these factors have caused the efficiency of {@link
 *       java.text.SimpleDateFormat} to be relatively low.
 *   <li>It is not thread safe. This is because {@link java.text.SimpleDateFormat} shares an
 *       internal {@link java.util.Calendar} member calender of DateFormat when doing conversion
 *       operations.
 * </ul>
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/12/7
 */
public abstract class DateTimeKit {
  public static DateTimeFormatter YYYY_MM_DD_HH_MM_SS =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  public static DateTimeFormatter YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  public static DateTimeFormatter YYYY_MM = DateTimeFormatter.ofPattern("yyyy-MM");
  public static DateTimeFormatter HH_MM_SS = DateTimeFormatter.ofPattern("HH:mm:ss");

  /** Prohibit user constructor. */
  private DateTimeKit() {
    throw new RuntimeException();
  }

  /**
   * Get the current time through the given date time formatter.
   *
   * @param formatter
   * @return The formatted String type
   */
  static String getStringLocalDateTime(DateTimeFormatter formatter) {
    return LocalDateTime.now().format(formatter);
  }

  /**
   * Get the current time through the given date time formatter.
   *
   * @param formatter
   * @return The formatted LocalDateTime type
   */
  static LocalDateTime getLocalDateTime(DateTimeFormatter formatter) {
    return LocalDateTime.parse(LocalDateTime.now().toString(), formatter);
  }

  /**
   * Get formatted time through the given the datetime and date time formatter.
   *
   * @param dateTime The concrete date time.
   * @param formatter
   * @return The formatted LocalDateTime type
   */
  static LocalDateTime getLocalDateTime(String dateTime, DateTimeFormatter formatter) {
    return LocalDateTime.parse(dateTime, formatter);
  }

  /**
   * Get formatted time through the given the datetime and date time formatter.
   *
   * @param dateTime The concrete date time.
   * @param formatter
   * @return The formatted String type
   */
  static String getStringLocalDateTime(String dateTime, DateTimeFormatter formatter) {
    return LocalDateTime.parse(dateTime, formatter).toString();
  }

  /**
   * Get formatted time through the given the delta and date time formatter.
   *
   * @param delta Number of days apart
   * @param formatter
   * @return The formatted String type
   */
  static String getStringLocalDateTimeForDays(
      String dateTime, DateTimeFormatter formatter, int delta) {
    return getLocalDateTime(dateTime, formatter).plusDays(delta).toString();
  }

  /**
   * Get formatted time through the given the delta and date time formatter.
   *
   * @param delta Number of days apart
   * @param formatter
   * @return The formatted LocalDateTime type
   */
  static LocalDateTime getLocalDateTimeForDays(
      String dateTime, DateTimeFormatter formatter, int delta) {
    return getLocalDateTime(dateTime, formatter).plusDays(delta);
  }

  /**
   * Get formatted time through the given the delta and date time formatter.
   *
   * @param delta Number of hours apart
   * @param formatter
   * @return The formatted String type
   */
  static String getStringLocalDateTimeForHours(
      String dateTime, DateTimeFormatter formatter, int delta) {
    return getLocalDateTime(dateTime, formatter).plusHours(delta).toString();
  }

  /**
   * Get formatted time through the given the delta and date time formatter.
   *
   * @param delta Number of hours apart
   * @param formatter
   * @return The formatted LocalDateTime type
   */
  static LocalDateTime getLocalDateTimeForHours(
      String dateTime, DateTimeFormatter formatter, int delta) {
    return getLocalDateTime(dateTime, formatter).plusHours(delta);
  }

  /**
   * Get formatted time through the given the delta and date time formatter.
   *
   * @param delta Number of minutes apart
   * @param formatter
   * @return The formatted String type
   */
  static String getStringLocalDateTimeForMinutes(
      String dateTime, DateTimeFormatter formatter, long delta) {
    return getLocalDateTime(dateTime, formatter).plusMinutes(delta).toString();
  }

  /**
   * Get formatted time through the given the delta and date time formatter.
   *
   * @param delta Number of minutes apart
   * @param formatter
   * @return The formatted LocalDateTime type
   */
  static LocalDateTime getLocalDateTimeForMinutes(
      String dateTime, DateTimeFormatter formatter, long delta) {
    return getLocalDateTime(dateTime, formatter).plusMinutes(delta);
  }

  /**
   * Get formatted time through the given the delta and date time formatter.
   *
   * @param delta Number of seconds apart
   * @param formatter
   * @return The formatted String type
   */
  static String getStringLocalDateTimeForSeconds(
      String dateTime, DateTimeFormatter formatter, long delta) {
    return getLocalDateTime(dateTime, formatter).plusMinutes(delta).toString();
  }

  /**
   * Get formatted time through the given the delta and date time formatter.
   *
   * @param delta Number of seconds apart
   * @param formatter
   * @return The formatted LocalDateTime type
   */
  static LocalDateTime getLocalDateTimeForSeconds(
      String dateTime, DateTimeFormatter formatter, long delta) {
    return getLocalDateTime(dateTime, formatter).plusMinutes(delta);
  }

  /**
   * Get formatted time through the given the delta and date time formatter.
   *
   * @param delta Number of days apart
   * @param formatter
   * @return The formatted String type
   */
  static String getStringLocalDateTimeForDays(DateTimeFormatter formatter, int delta) {
    return getLocalDateTime(formatter).plusDays(delta).toString();
  }

  /**
   * Get formatted time through the given the delta and date time formatter.
   *
   * @param delta Number of days apart
   * @param formatter
   * @return The formatted LocalDateTime type
   */
  static LocalDateTime getLocalDateTimeForDays(DateTimeFormatter formatter, int delta) {
    return getLocalDateTime(formatter).plusDays(delta);
  }

  /**
   * Get formatted time through the given the delta and date time formatter.
   *
   * @param delta Number of hours apart
   * @param formatter
   * @return The formatted String type
   */
  static String getStringLocalDateTimeForHours(DateTimeFormatter formatter, int delta) {
    return getLocalDateTime(formatter).plusHours(delta).toString();
  }

  /**
   * Get formatted time through the given the delta and date time formatter.
   *
   * @param delta Number of hours apart
   * @param formatter
   * @return The formatted LocalDateTime type
   */
  static LocalDateTime getLocalDateTimeForHours(DateTimeFormatter formatter, int delta) {
    return getLocalDateTime(formatter).plusHours(delta);
  }

  /**
   * Get formatted time through the given the delta and date time formatter.
   *
   * @param delta Number of minutes apart
   * @param formatter
   * @return The formatted String type
   */
  static String getStringLocalDateTimeForMinutes(DateTimeFormatter formatter, long delta) {
    return getLocalDateTime(formatter).plusMinutes(delta).toString();
  }

  /**
   * Get formatted time through the given the delta and date time formatter.
   *
   * @param delta Number of minutes apart
   * @param formatter
   * @return The formatted LocalDateTime type
   */
  static LocalDateTime getLocalDateTimeForMinutes(DateTimeFormatter formatter, long delta) {
    return getLocalDateTime(formatter).plusMinutes(delta);
  }

  /**
   * Get formatted time through the given the delta and date time formatter.
   *
   * @param delta Number of seconds apart
   * @param formatter
   * @return The formatted String type
   */
  static String getStringLocalDateTimeForSeconds(DateTimeFormatter formatter, long delta) {
    return getLocalDateTime(formatter).plusMinutes(delta).toString();
  }

  /**
   * Get formatted time through the given the delta and date time formatter.
   *
   * @param delta Number of seconds apart
   * @param formatter
   * @return The formatted LocalDateTime type
   */
  static LocalDateTime getLocalDateTimeForSeconds(DateTimeFormatter formatter, long delta) {
    return getLocalDateTime(formatter).plusMinutes(delta);
  }

  /**
   * Get the offset date time.
   *
   * @return {@link OffsetDateTime} instance.
   */
  static OffsetDateTime getCurrentOffsetDateTime() {
    return OffsetDateTime.now(TimeZoneConstant.CHINA.getZoneId());
  }

  /**
   * Get the start time in days apart.
   *
   * @param delta Number of days apart
   * @return {@link OffsetDateTime} instance.
   */
  static OffsetDateTime getDeltaDayOffsetDateTimeStart(long delta) {
    return getCurrentOffsetDateTime()
        .plusDays(delta)
        .withHour(0)
        .withMinute(0)
        .withSecond(0)
        .withNano(0);
  }

  /**
   * Get the end time in days apart.
   *
   * @param delta Number of days apart
   * @return {@link OffsetDateTime} instance.
   */
  static OffsetDateTime getDeltaDayOffsetDateTimeEnd(long delta) {
    return getCurrentOffsetDateTime()
        .plusDays(delta)
        .withHour(23)
        .withMinute(59)
        .withSecond(59)
        .withNano(0);
  }

  /**
   * Get the start time in yesterday.
   *
   * @return {@link OffsetDateTime} instance.
   */
  static OffsetDateTime getYesterdayOffsetDateTimeStart() {
    return getDeltaDayOffsetDateTimeStart(-1);
  }

  /**
   * Get the end time in yesterday.
   *
   * @return {@link OffsetDateTime} instance.
   */
  static OffsetDateTime getYesterdayOffsetDateTimeEnd() {
    return getDeltaDayOffsetDateTimeEnd(-1);
  }

  /**
   * Get the difference between start time and end time
   *
   * @param start
   * @param end
   * @return {@link OffsetDateTime} instance.
   */
  static long durationInDays(OffsetDateTime start, OffsetDateTime end) {
    return Duration.between(start, end).toDays();
  }

  /**
   * Get the start time in the first day of month.
   *
   * @return {@link OffsetDateTime} instance.
   */
  static OffsetDateTime getCurrentMonthOffsetDateTimeStart() {
    OffsetDateTime offsetDateTime = getCurrentOffsetDateTime();
    return offsetDateTime
        .with(TemporalAdjusters.firstDayOfMonth())
        .withHour(0)
        .withMinute(0)
        .withSecond(0)
        .withNano(0);
  }

  /**
   * Get the end time in the last day of month.
   *
   * @return {@link OffsetDateTime} instance.
   */
  static OffsetDateTime getCurrentMonthOffsetDateTimeEnd() {
    OffsetDateTime offsetDateTime = getCurrentOffsetDateTime();
    return offsetDateTime
        .with(TemporalAdjusters.lastDayOfMonth())
        .withHour(23)
        .withMinute(59)
        .withSecond(59)
        .withNano(0);
  }

  enum TimeZoneConstant {
    CHINA(ZoneId.of("Asia/Shanghai"), "上海-中国时区");
    private final ZoneId zoneId;
    private final String description;

    TimeZoneConstant(ZoneId zoneId, String description) {
      this.zoneId = zoneId;
      this.description = description;
    }

    public ZoneId getZoneId() {
      return zoneId;
    }

    public String getDescription() {
      return description;
    }
  }
}
