package com.siam.package_common.util;

import com.siam.package_common.enums.TimeTypeEnum;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * 日期工具类
 **/
public class DateUtilsPlus {

    public static final String xinQiNames[] =  {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    private static final int[] DAYS_OF_MONTH = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    /**
     * 获取指定月共有多少天
     *
     * @param yyyy 年份
     * @param mmBased0 月份（1月份为0）
     * @return 该月的天数，如果月份不合法，返回0
     */
    public static int getDaysOfMonth(int yyyy, int mmBased0) {
        if (mmBased0 == 1) {
            return ((yyyy % 4 == 0 && yyyy % 100 != 0) || yyyy % 400 == 0) ? 29 : 28;
        } else if (mmBased0 >= 0 && mmBased0 < 12) {
            return DateUtilsPlus.DAYS_OF_MONTH[mmBased0];
        } else {
            return 0;
        }
    }

    /**
     * 根据毫秒数值创建Date对象
     *
     * @param timeMillis 毫秒数
     * @return java.util.Date 对象
     */
    public static Date getDate(long timeMillis) {
        Date date = new Date();
        date.setTime(timeMillis);
        return date;
    }

    /**
     * 创建日期对象
     *
     * @param yyyy 年
     * @param mmBased0 月份（1月份为0）
     * @return java.util.Date 对象
     */
    public static Date getDate(final int yyyy, final int mmBased0) {
        return new GregorianCalendar(yyyy, mmBased0, 1, 0, 0, 0).getTime();
    }

    /**
     * 创建日期对象
     *
     * @param yyyy 年
     * @param mmBased0 月份（1月份为0）
     * @param dd 日
     * @return java.util.Date 对象
     */
    public static Date getDate(int yyyy, int mmBased0, int dd) {
        return new GregorianCalendar(yyyy, mmBased0, dd).getTime();
    }

    /**
     * 根据给定的年月日时分秒创建Date对象
     *
     * @param yyyy 年
     * @param mmBased0 月份（1月份为0）
     * @param dd 日
     * @param hh24 小时（24时制）
     * @param mi 分
     * @param ss 秒
     * @return java.util.Date 对象
     */
    public static Date getDate(int yyyy, int mmBased0, int dd, int hh24, int mi, int ss) {
        return new GregorianCalendar(yyyy, mmBased0, dd, hh24, mi, ss).getTime();
    }

    /**
     * 复制一个Date对象
     *
     * @param date
     * @return
     */
    public static Date cloneDate(Date date) {
        Date nd = new Date();
        nd.setTime(date.getTime());
        return nd;
    }

    /**
     * 在一个给定日期上增加若干秒
     *
     * @param date
     * @param m
     */
    public static Date addSeconds(Date date, int m) {
        Date resultDate = new Date();
        long t = date.getTime();
        t += m * 1000L;
        resultDate.setTime(t);
        return resultDate;
    }

    /**
     * 在一个给定日期上增加若干分钟
     *
     * @param date
     * @param m
     */
    public static Date addMinutes(Date date, int m) {
        Date resultDate = new Date();
        long t = date.getTime();
        t += m * 60 * 1000L;
        resultDate.setTime(t);
        return resultDate;
    }

    /**
     * 在一个给定日期上减去若干分钟
     *
     * @param date
     * @param m
     */
    public static Date subtractMinutes(Date date, int m) {
        Date resultDate = new Date();
        long t = date.getTime();
        t -= m * 60 * 1000L;
        resultDate.setTime(t);
        return resultDate;
    }

    /**
     * 在一个指定日期上增加若干天
     *
     * @param date
     * @param d
     */
    public static Date addDays(Date date, int d) {
        return DateUtilsPlus.addMinutes(date, d * 1440);
    }

    /**
     * 在一个指定日期上减去若干天
     *
     * @param date
     * @param d
     */
    public static Date subtractDays(final Date date, final int d) {
        return DateUtilsPlus.subtractMinutes(date, d * 1440);
    }

    /**
     * 给定的字符串（yyyy-MM-dd）加指定天数
     *
     * @param begintime
     * @param days
     * @return
     */
    public static String addDays(String begintime, int days) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date tmpDate = null;
        try{
            tmpDate = df.parse(begintime);
        }catch(Exception e){
            System.out.println("日期型字符串格式错误");
        }
        long nDay=(tmpDate.getTime()/(24*60*60*1000)+1+days)*(24*60*60*1000);
        tmpDate.setTime(nDay);
        return df.format(tmpDate);
    }

    /**
     * 计算 从 ds 到 dt 经过的秒数。
     *
     * @param ds
     * @param dt
     * @return
     */
    public static long diffSeconds(Date ds, Date dt) {
        long s = ds.getTime();
        long t = dt.getTime();
        return (s - t) / 1000;
    }

    /**
     * 计算 从 ds 到 dt 经过的天数
     *
     * @param ds
     * @param dt
     * @return
     */
    public static int diffDays(Date ds, Date dt) {
        long diff = ds.getTime() - dt.getTime();
        long days = diff / (1000 * 60 * 60 * 24);
        return (int)days;
    }

    /**
     * 2个日期之间相差的月数
     *
     * @return
     */
    public static int diffMonths(Date sd, Date ed) {
        int s = sd.getYear() * 12 + sd.getMonth();
        int e = ed.getYear() * 12 + ed.getMonth();
        return e - s;
    }

    /**
     * 根据指定日期得出所属星期
     *
     * @param date
     * @return
     **/
    public static String getXingQi(Date date){
        SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if(dayOfWeek < 0){
            dayOfWeek = 0;
        }
        return xinQiNames[dayOfWeek];
    }

    /**
     * 是否是节假日
     *
     * @param date
     * @return
     */
    public static boolean isHoliday(Date date){
        if(DateUtilsPlus.getXingQi(date).equals("星期日") || DateUtilsPlus.getXingQi(date).equals("星期六") ){
            return true;
        }
        return false;
    }

    /**
     * 字符串转换成日期
     *
     * @param str
     * @return date
     */
    public static Date strToDate(String str) {
        if(org.apache.commons.lang3.StringUtils.isBlank(str)){
            return null;
        }

        //1、符号过滤
        str = str.replaceAll("/", "-");

        //2、格式比对
        Date date = null;
        String[] formatArray = {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd"}; //先长后短
        for (String format : formatArray) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            try {
                date = simpleDateFormat.parse(str);
                break;
            } catch (ParseException e) {
                //e.printStackTrace();
            }
        }
        return date;
    }

    /**
     * 字符串按照指定格式转化为日期
     *
     * @param str
     * @param style
     * @return
     **/
    public static Date strToDate(String str, String style) {
        if(style==null || style.trim().equals("")){
            style = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat format = new SimpleDateFormat(style);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            //e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取某一天(例如昨天，前天，明天)
     *
     * @return
     */
    public static String LastOrNextDate(int day){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        date = calendar.getTime();
        String newdate = sdf.format(date);
        return  newdate;
    }

    /**
     * 计算 从 ds 到 dt 经过的天数（忽略时分秒）
     *
     * @param ds
     * @param dt
     * @return
     */
    public static int diffDaysIgnoreHours(Date ds, Date dt) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            ds = sdf.parse(sdf.format(ds));
            dt = sdf.parse(sdf.format(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diff = ds.getTime() - dt.getTime();
        long days = diff / (1000 * 60 * 60 * 24);
        return (int)days;
    }

    /**
     * 获取日期的开始时间（0:0:0）
     *
     * @param date
     * @return
     **/
    public static Date getDateStartTime(Date date){
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        return date;
    }

    /**
     * 获取日期的结束时间（23:59:59）
     *
     * @param date
     * @return
     **/
    public static Date getDateEndTime(Date date){
        date.setHours(23);
        date.setMinutes(59);
        date.setSeconds(59);
        return date;
    }

    /**
     * 格式化日期
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 获取两个日期之间的所有日期(字符串格式, 按天计算)
     *
     * @param startTime String 开始时间 yyyy-MM-dd
     * @param endTime  String 结束时间 yyyy-MM-dd
     * @return
     */
    public static List<String> getBetweenDays(String startTime, String endTime) {
        if (StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)) {
            return null;
        }
        //1、定义转换格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date start = null;
        Date end = null;
        try {
            start = df.parse(startTime);
            end = df.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (start == null || end == null) {
            return null;
        }
        List<String> result = new ArrayList<String>();

        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);

        tempStart.add(Calendar.DAY_OF_YEAR, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        result.add(sdf.format(start));
        while (!tempStart.after(tempEnd)) {
            result.add(sdf.format(tempStart.getTime()));
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        return result;
    }

    /**
     * 获取两个日期之间的所有月份 (年月)
     *
     * @param startTime
     * @param endTime
     * @return：YYYY-MM
     */
    public static List<String> getMonthBetweenDate(String startTime, String endTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        // 声明保存日期集合
        List<String> list = new ArrayList<String>();
        try {
            // 转化成日期类型
            Date startDate = sdf.parse(startTime);
            Date endDate = sdf.parse(endTime);

            //用Calendar 进行日期比较判断
            Calendar calendar = Calendar.getInstance();
            while (startDate.getTime()<=endDate.getTime()){
                // 把日期添加到集合
                list.add(sdf.format(startDate));
                // 设置日期
                calendar.setTime(startDate);
                //把日期增加一天
                calendar.add(Calendar.MONTH, 1);
                // 获取增加后的日期
                startDate=calendar.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取两个时间段的时间段值
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param typeEnum 时间类型枚举，决定返回的是之间的年份还是月份
     * @return
     */
    public static List<String> getTimePeriodFromTwoTime(Long startTime, Long endTime, TimeTypeEnum typeEnum) {
        LocalDate start = Instant.ofEpochMilli(startTime).atZone(ZoneOffset.ofHours(8)).toLocalDate();
        LocalDate end = Instant.ofEpochMilli(endTime).atZone(ZoneOffset.ofHours(8)).toLocalDate();

        List<String> result = new ArrayList<>();

        // 年
        if (typeEnum.getType().equals(TimeTypeEnum.YEAR.getType())) {
            Year startyear = Year.from(start);
            Year endYear = Year.from(end);
            // 包含最后一个时间
            for (long i = 0; i <= ChronoUnit.YEARS.between(startyear, endYear); i++) {
                result.add(startyear.plusYears(i).toString());
            }
        }
        // 月
        else if (TimeTypeEnum.MONTH.getType().equals(typeEnum.getType())) {
            YearMonth from = YearMonth.from(start);
            YearMonth to = YearMonth.from(end);
            for (long i = 0; i <= ChronoUnit.MONTHS.between(from, to); i++) {
                result.add(from.plusMonths(i).toString());
            }
        }
        // 日
        else {
            for (long i = 0; i <= ChronoUnit.DAYS.between(start, end); i++) {
                result.add(start.plus(i, ChronoUnit.DAYS).toString());
            }
        }
        return result;
    }

    /**
     * 格式化日期
     *
     * @return
     */
    public static Date parseDate(String date) {
        date = date.replaceAll("-", "/");
        String[] array = {"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss"};
        for (String pattern : array) {
            try {
                return DateUtils.parseDate(date, pattern);
            } catch (ParseException e) {
                continue;
            }
        }
        return null;
    }

    /**
     * 格式化日期
     *
     * @return
     */
    public static Date parse(String date, String pattern) {
        try {
            return DateUtils.parseDate(date, pattern);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当月第一天
     *
     */
    public static LocalDateTime getFirstLocalDayOfMonth(LocalDateTime localDateTime) {
        return localDateTime.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
    }

    /**
     * 获取当月第一周  以第一个周一为准
     *
     */
    private static LocalDateTime getFirstMonday(LocalDateTime sourceTime) {
        LocalDateTime firstMondayOfMonth = getFirstLocalDayOfMonth(sourceTime);
        for (int i = 0; i < 6; i++) {
            DayOfWeek dayOfWeekTemp = firstMondayOfMonth.getDayOfWeek();
            if (dayOfWeekTemp.equals(DayOfWeek.MONDAY)) {
                break;
            }
            //往后推一天
            firstMondayOfMonth = firstMondayOfMonth.plusDays(1);
        }
        return firstMondayOfMonth;
    }

    /**
     * 获取几月份的第几周 [X月份第X周]
     *
     */
    public static String getMonthNoAndWeekNo(LocalDateTime sourceTime) {
        String monthNoAndWeekNo;
        //获取当月的第一天
        //获取月第一个周一,从当月第一天开始找

        LocalDateTime firstMondayOfMonth = getFirstMonday(sourceTime);

        //比较当月的第一个星期一 < = 参数时间
        if (firstMondayOfMonth.isBefore(sourceTime) || firstMondayOfMonth.isEqual(sourceTime)) {
            //当月第一个周一在当前时间之前 firstMondayOfMonth<=sourceTime
            //计算两个时间间隔天数
            int dayOfMonthFirstMonday = firstMondayOfMonth.getDayOfMonth();
            int dayOfMonthSourceTime = sourceTime.getDayOfMonth();

            int diffDays = dayOfMonthSourceTime - dayOfMonthFirstMonday;
            //第几周weekNo
            int weekNo = (diffDays / 7) + 1;
            //月份
            int monthNo = sourceTime.getMonth().getValue();

            monthNoAndWeekNo = monthNo + "月份" + "第" + weekNo + "周";
        } else {
            //如果当月的第一个周一大于参数时间,则要计算到上个月份去
            //获取上一个月的第一个周一
            LocalDateTime lastMontLocalDateTime = sourceTime.minusMonths(1);
            //上个月的第一天
            //从上个月的第一天开始找周一
            LocalDateTime firstMondayOfMonthLast = getFirstMonday(lastMontLocalDateTime);

            //  计算两个时间间隔天数 (上月第一个周一 减去 当前时间)
            Duration duration = Duration.between(firstMondayOfMonthLast, sourceTime);
            long diffDays = duration.toDays(); //相差的天数
            //第几周weekNo
            long weekNo = (diffDays / 7) + 1;
            //月份
            int monthNo = firstMondayOfMonthLast.getMonth().getValue();//汉字版月份

            monthNoAndWeekNo = monthNo + "月份" + "第" + weekNo + "周";
        }

        return monthNoAndWeekNo;
    }

    /**
     * 获取指定某年某月有多少周
     *
     * @param year
     * @param month
     * @return
     * @author 暹罗
     */
    public static int getWeeks(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DATE, 1);
        month = month - 1;
        calendar.set(Calendar.MONTH, month);
        int count = 0;
        while (calendar.get(Calendar.MONTH) == month) {
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                ++count;
            }
            calendar.add(Calendar.DATE, 1);
        }
        return count;
    }

    public static void main(String[] args) throws ParseException {
//        List<String> list = DateUtils.getBetweenDays("2020-05-10", "2020-05-10");
//        System.out.println(list);
//        Date startDate = new Date("1970/1/1 00:00:00");

//        Date startDate = DateUtilsPlus.parseDate("2021/11/01");
//        System.out.println(startDate);
//        Date date = strToDate("2021-01-01 19");
//        System.out.println(date);

        /*for(int i = 1; i <= 31; i++){
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime localDateTime = now.withDayOfMonth(i).withMonth(6);
            System.out.println(i + " -- " + getMonthNoAndWeekNo(localDateTime));
        }*/

        /*for(int i = 1; i <= 12; i++){
            System.out.println(i + " -- " + getWeeks(2023, i));
        }*/


        /*String startStr = "2021-02-26";
        String endStr = "2022-03-09";
        List<String> list = getMonthBetweenDate(startStr, endStr);
        System.out.println(list);*/

        /*LocalDate start = LocalDate.of(2016, 1, 1);
        LocalDate end = LocalDate.of(2022, 3, 31);
        long timstrap = start.atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
        long endtime = end.atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
        for (String s : getTimePeriodFromTwoTime(timstrap, endtime, TimeTypeEnum.YEAR)) {
            System.out.println(s);
        }*/
    }
}