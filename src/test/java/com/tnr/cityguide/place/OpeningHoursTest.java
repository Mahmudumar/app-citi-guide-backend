package com.tnr.cityguide.place;
import static org.junit.jupiter.api.Assertions.*;
import java.time.*;import org.junit.jupiter.api.Test;
class OpeningHoursTest {
 @Test void open24HoursIsAlwaysOpen(){Place p=new Place();p.setOpen24h(true);assertTrue(OpeningHours.isOpen(p,ZoneId.of("Africa/Lagos"),Instant.parse("2026-11-11T00:00:00Z")));}
 @Test void eventsWithoutHoursAreUnknown(){Place p=new Place();assertNull(OpeningHours.isOpen(p,ZoneId.of("Asia/Tokyo"),Instant.parse("2026-11-11T00:00:00Z")));}
 @Test void closedDayIsRespected(){Place p=new Place();p.setOpensAt(LocalTime.of(9,0));p.setClosesAt(LocalTime.of(18,0));p.setClosedDays(new short[]{2});Instant t=ZonedDateTime.of(2026,11,10,10,0,0,0,ZoneId.of("Europe/Paris")).toInstant();assertFalse(OpeningHours.isOpen(p,ZoneId.of("Europe/Paris"),t));}
 @Test void overnightOpeningUsesPreviousDaysClosedStatus(){Place p=new Place();p.setOpensAt(LocalTime.of(22,0));p.setClosesAt(LocalTime.of(4,0));p.setClosedDays(new short[]{2});Instant t=ZonedDateTime.of(2026,11,11,2,0,0,0,ZoneId.of("Europe/Paris")).toInstant();assertFalse(OpeningHours.isOpen(p,ZoneId.of("Europe/Paris"),t));}
}
