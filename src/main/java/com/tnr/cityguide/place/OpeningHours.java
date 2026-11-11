package com.tnr.cityguide.place;

import java.time.*;
import java.util.Arrays;

public final class OpeningHours {
 private OpeningHours(){}
 public static Boolean isOpen(Place place,ZoneId zone,Instant now){
  if(place.isOpen24h()) return true;
  if(place.getOpensAt()==null||place.getClosesAt()==null) return null;
  ZonedDateTime local=now.atZone(zone); LocalTime time=local.toLocalTime();
  int today=local.getDayOfWeek().getValue();
  int yesterday=LocalDate.ofInstant(now,zone).minusDays(1).getDayOfWeek().getValue();
  short[] closed=place.getClosedDays()==null?new short[0]:place.getClosedDays();
  boolean todayClosed=Arrays.binarySearch(sorted(closed), (short)today)>=0 || contains(closed,today);
  boolean yesterdayClosed=contains(closed,yesterday);
  if(place.getClosesAt().isAfter(place.getOpensAt()))
   return !todayClosed&&!time.isBefore(place.getOpensAt())&&time.isBefore(place.getClosesAt());
  return (!todayClosed&&!time.isBefore(place.getOpensAt())) || (!yesterdayClosed&&time.isBefore(place.getClosesAt()));
 }
 private static boolean contains(short[] values,int day){for(short v:values)if(v==day)return true;return false;}
 private static short[] sorted(short[] values){return values;}
}
