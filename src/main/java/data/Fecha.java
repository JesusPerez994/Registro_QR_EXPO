/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 *
 * @author jesue
 */
public interface Fecha {

    public static Date getDate() {
        Calendar cal = new GregorianCalendar();
        return cal.getTime();
    }
    public static Calendar getCalendar() {
        Calendar cal = new GregorianCalendar();
        return cal;
    }
    public static LocalDate getLocalDate(){
        return LocalDate.now();
    }
    public static Date getFechaSiguienteMes() {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.MONTH, 1);
        return cal.getTime();
    }
    public static Date getFechaMesPasado(){
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.MONTH,-1);
        return cal.getTime();
    }
    public static Date sumarUnDia(Date date){
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR,1);
        return cal.getTime();
    }
    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
    public static String formatoAnoMesDia(Date fecha) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(fecha);
    }
    public static String formatoDiaMesAno(Date fecha){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(fecha);
    }
    public static String formatoAnoMesDiaTiempo(Date fecha){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd k:mm:ss");
        return dateFormat.format(fecha);
    }
}