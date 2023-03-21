package seedu.address.model.timetable.time.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.timetable.util.TypicalTime.EIGHT_AM;
import static seedu.address.model.timetable.util.TypicalTime.EIGHT_PM;
import static seedu.address.model.timetable.util.TypicalTime.ELEVEN_AM;
import static seedu.address.model.timetable.util.TypicalTime.FIVE_PM;
import static seedu.address.model.timetable.util.TypicalTime.FOUR_PM;
import static seedu.address.model.timetable.util.TypicalTime.NINE_AM;
import static seedu.address.model.timetable.util.TypicalTime.NINE_PM;
import static seedu.address.model.timetable.util.TypicalTime.ONE_PM;
import static seedu.address.model.timetable.util.TypicalTime.SEVEN_PM;
import static seedu.address.model.timetable.util.TypicalTime.SIX_PM;
import static seedu.address.model.timetable.util.TypicalTime.TEN_AM;
import static seedu.address.model.timetable.util.TypicalTime.TEN_PM;
import static seedu.address.model.timetable.util.TypicalTime.THREE_PM;
import static seedu.address.model.timetable.util.TypicalTime.TWELVE_PM;
import static seedu.address.model.timetable.util.TypicalTime.TWO_PM;
import static seedu.address.model.timetable.util.TypicalTimetable.FULL_CONFLICT_TIMETABLE_A;
import static seedu.address.model.timetable.util.TypicalTimetable.FULL_CONFLICT_TIMETABLE_B;
import static seedu.address.model.timetable.util.TypicalTimetable.TIMETABLE_A;
import static seedu.address.model.timetable.util.TypicalTimetable.TIMETABLE_B;
import static seedu.address.model.timetable.util.TypicalTimetable.TIMETABLE_C;
import static seedu.address.model.timetable.util.TypicalTimetable.TIMETABLE_D;
import static seedu.address.model.timetable.util.TypicalTimetable.TIMETABLE_E;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.timetable.Timetable;
import seedu.address.model.timetable.time.SchoolDay;
import seedu.address.model.timetable.time.TimeBlock;
import seedu.address.model.timetable.time.TimePeriod;
import seedu.address.model.timetable.time.TimeSlot;


class TimeUtilsTest {

    @Test
    public void getCommonIntervals_twoTimetablesMonday_oneTimeBlock() {
        List<Timetable> timetables = List.of(TIMETABLE_A, TIMETABLE_B);
        List<TimeSlot> intervals = TimeUtils.getFreeCommonIntervals(SchoolDay.MONDAY, timetables);
        List<TimePeriod> mergedIntervals = TimeUtils.mergeTimeSlots(intervals);
        assertFalse(mergedIntervals.isEmpty());
        assertEquals(1, mergedIntervals.size());
        // expecting 12pm - 10pm
        assertEquals(new TimeBlock(TWELVE_PM, TEN_PM, SchoolDay.MONDAY), mergedIntervals.get(0));
    }

    @Test
    public void getCommonIntervals_twoTimetablesTuesday_success() {
        List<Timetable> timetables = List.of(TIMETABLE_A, TIMETABLE_B);
        List<TimeSlot> intervals = TimeUtils.getFreeCommonIntervals(SchoolDay.TUESDAY, timetables);
        List<TimePeriod> mergedIntervals = TimeUtils.mergeTimeSlots(intervals);
        //expecting 8-10am, 12-4pm, 5pm - 10pm
        assertEquals(3, mergedIntervals.size());
        assertArrayEquals(new TimeBlock[]{new TimeBlock(EIGHT_AM, TEN_AM, SchoolDay.TUESDAY),
            new TimeBlock(TWELVE_PM, FOUR_PM, SchoolDay.TUESDAY),
            new TimeBlock(FIVE_PM, TEN_PM, SchoolDay.TUESDAY)}, mergedIntervals.toArray());
    }

    @Test
    public void getCommonIntervals_twoTimetablesWednesday_success() {
        List<Timetable> timetables = List.of(TIMETABLE_A, TIMETABLE_B);
        List<TimeSlot> intervals = TimeUtils.getFreeCommonIntervals(SchoolDay.WEDNESDAY, timetables);
        List<TimePeriod> mergedIntervals = TimeUtils.mergeTimeSlots(intervals);
        //expecting 8am-6pm, 7-10pm
        assertEquals(2, mergedIntervals.size());
        assertArrayEquals(new TimeBlock[] { new TimeBlock(EIGHT_AM, SIX_PM, SchoolDay.WEDNESDAY),
            new TimeBlock(SEVEN_PM, TEN_PM, SchoolDay.WEDNESDAY)}, mergedIntervals.toArray());
    }

    @Test
    public void getCommonIntervals_twoTimetablesThursday_success() {
        List<Timetable> timetables = List.of(TIMETABLE_A, TIMETABLE_B);
        List<TimeSlot> intervals = TimeUtils.getFreeCommonIntervals(SchoolDay.THURSDAY, timetables);
        List<TimePeriod> mergedIntervals = TimeUtils.mergeTimeSlots(intervals);
        //expecting 8-4pm, 9-10pm
        assertEquals(2, mergedIntervals.size());
        assertArrayEquals(new TimeBlock[] { new TimeBlock(EIGHT_AM, FOUR_PM, SchoolDay.THURSDAY),
            new TimeBlock(NINE_PM, TEN_PM, SchoolDay.THURSDAY)}, mergedIntervals.toArray());
    }

    @Test
    public void getCommonIntervals_twoTimetablesFriday_success() {
        List<Timetable> timetables = List.of(TIMETABLE_A, TIMETABLE_B);
        List<TimeSlot> intervals = TimeUtils.getFreeCommonIntervals(SchoolDay.FRIDAY, timetables);
        List<TimePeriod> mergedIntervals = TimeUtils.mergeTimeSlots(intervals);
        //expecting 11am-7pm
        assertEquals(1, mergedIntervals.size());
        assertArrayEquals(new TimeBlock[] { new TimeBlock(ELEVEN_AM, SEVEN_PM, SchoolDay.FRIDAY)},
            mergedIntervals.toArray());
    }

    @Test
    public void getCommonIntervals_noCommonSlotAvailable_success() {
        List<Timetable> timetables = List.of(FULL_CONFLICT_TIMETABLE_A, FULL_CONFLICT_TIMETABLE_B);
        List<TimeSlot> intervals = TimeUtils.getFreeCommonIntervals(SchoolDay.THURSDAY, timetables);
        assertEquals(0, intervals.size());
        List<TimePeriod> mergedIntervals = TimeUtils.mergeTimeSlots(intervals);
        //expecting 11am-7pm
        assertEquals(0, mergedIntervals.size());
        assertArrayEquals(new TimeBlock[]{}, mergedIntervals.toArray());

    }

    @Test
    public void getCommonIntervals_emptyList_success() {
        List<Timetable> timetables = List.of();
        List<TimeSlot> intervals = TimeUtils.getFreeCommonIntervals(SchoolDay.THURSDAY, timetables);
        assertEquals(0, intervals.size());
        List<TimePeriod> mergedIntervals = TimeUtils.mergeTimeSlots(intervals);
        //expecting 11am-7pm
        assertEquals(0, mergedIntervals.size());
        assertArrayEquals(new TimeBlock[]{}, mergedIntervals.toArray());

    }

    @Test
    public void getCommonIntervals_threeTimetablesMonday_oneTimeBlock() {
        List<Timetable> timetables = List.of(TIMETABLE_A, TIMETABLE_B, TIMETABLE_C);
        List<TimeSlot> intervals = TimeUtils.getFreeCommonIntervals(SchoolDay.MONDAY, timetables);
        List<TimePeriod> mergedIntervals = TimeUtils.mergeTimeSlots(intervals);
        assertFalse(mergedIntervals.isEmpty());
        assertEquals(1, mergedIntervals.size());
        // expecting 12pm - 10pm
        assertEquals(new TimeBlock(TWELVE_PM, TEN_PM, SchoolDay.MONDAY), mergedIntervals.get(0));
    }

    @Test
    public void getCommonIntervals_threeTimetablesWednesday_oneTimeBlock() {
        List<Timetable> timetables = List.of(TIMETABLE_A, TIMETABLE_B, TIMETABLE_C);
        List<TimeSlot> intervals = TimeUtils.getFreeCommonIntervals(SchoolDay.WEDNESDAY, timetables);
        List<TimePeriod> mergedIntervals = TimeUtils.mergeTimeSlots(intervals);
        assertFalse(mergedIntervals.isEmpty());
        assertEquals(3, mergedIntervals.size());
        // expecting 8am-10am, 1pm-6pm, 7-10pm
        assertArrayEquals(new TimeBlock[]{new TimeBlock(EIGHT_AM, TEN_AM, SchoolDay.WEDNESDAY),
            new TimeBlock(ONE_PM, SIX_PM, SchoolDay.WEDNESDAY),
            new TimeBlock(SEVEN_PM, TEN_PM, SchoolDay.WEDNESDAY)},
            mergedIntervals.toArray());
    }

    @Test
    public void getCommonIntervals_timetablesVariant2Monday_oneTimeBlock() {
        List<Timetable> timetables = List.of(TIMETABLE_C, TIMETABLE_D, TIMETABLE_E);
        List<TimeSlot> intervals = TimeUtils.getFreeCommonIntervals(SchoolDay.MONDAY, timetables);
        List<TimePeriod> mergedIntervals = TimeUtils.mergeTimeSlots(intervals);
        assertFalse(mergedIntervals.isEmpty());
        assertEquals(1, mergedIntervals.size());
        // expecting 12noon-2pm
        assertArrayEquals(new TimeBlock[]{
            new TimeBlock(TWELVE_PM, TEN_PM, SchoolDay.MONDAY)},
            mergedIntervals.toArray());
    }

    @Test
    public void getCommonIntervals_timetablesVariant2Tuesday_twoTimeBlock() {
        List<Timetable> timetables = List.of(TIMETABLE_D, TIMETABLE_C, TIMETABLE_E);
        List<TimeSlot> intervals = TimeUtils.getFreeCommonIntervals(SchoolDay.TUESDAY, timetables);
        List<TimePeriod> mergedIntervals = TimeUtils.mergeTimeSlots(intervals);
        assertFalse(mergedIntervals.isEmpty());
        assertEquals(2, mergedIntervals.size());
        // expecting 12noon-2am, 5-10pm
        assertArrayEquals(new TimeBlock[]{
            new TimeBlock(TWELVE_PM, TWO_PM, SchoolDay.TUESDAY),
            new TimeBlock(FIVE_PM, TEN_PM, SchoolDay.TUESDAY)},
                mergedIntervals.toArray());
    }

    @Test
    public void getCommonIntervals_timetablesVariant2Wednesday_oneTimeBlock() {
        List<Timetable> timetables = List.of(TIMETABLE_C, TIMETABLE_D, TIMETABLE_E);
        List<TimeSlot> intervals = TimeUtils.getFreeCommonIntervals(SchoolDay.WEDNESDAY, timetables);
        List<TimePeriod> mergedIntervals = TimeUtils.mergeTimeSlots(intervals);
        assertFalse(mergedIntervals.isEmpty());
        assertEquals(3, mergedIntervals.size());
        // expecting 8am-10am, 1pm-4pm, 5pm-10pm
        assertArrayEquals(new TimeBlock[]{
            new TimeBlock(EIGHT_AM, TEN_AM, SchoolDay.WEDNESDAY),
            new TimeBlock(ONE_PM, FOUR_PM, SchoolDay.WEDNESDAY),
            new TimeBlock(FIVE_PM, TEN_PM, SchoolDay.WEDNESDAY)},
            mergedIntervals.toArray());
    }

    @Test
    public void getCommonIntervals_timetablesVariant2Thursday_twoTimeBlock() {
        List<Timetable> timetables = List.of(TIMETABLE_C, TIMETABLE_D, TIMETABLE_E);
        List<TimeSlot> intervals = TimeUtils.getFreeCommonIntervals(SchoolDay.THURSDAY, timetables);
        List<TimePeriod> mergedIntervals = TimeUtils.mergeTimeSlots(intervals);
        assertFalse(mergedIntervals.isEmpty());
        assertEquals(2, mergedIntervals.size());
        // expecting 8am-11am, 1pm-10pm
        assertArrayEquals(new TimeBlock[]{
            new TimeBlock(EIGHT_AM, ELEVEN_AM, SchoolDay.THURSDAY),
            new TimeBlock(ONE_PM, TEN_PM, SchoolDay.THURSDAY)},
            mergedIntervals.toArray());
    }

    @Test
    public void getCommonIntervals_timetablesVariant2Friday_twoTimeBlock() {
        List<Timetable> timetables = List.of(TIMETABLE_C, TIMETABLE_E, TIMETABLE_D);
        List<TimeSlot> intervals = TimeUtils.getFreeCommonIntervals(SchoolDay.FRIDAY, timetables);
        List<TimePeriod> mergedIntervals = TimeUtils.mergeTimeSlots(intervals);
        assertFalse(mergedIntervals.isEmpty());
        assertEquals(2, mergedIntervals.size());
        // expecting 8am-9am, 11am-7pm
        assertArrayEquals(new TimeBlock[]{
            new TimeBlock(EIGHT_AM, NINE_AM, SchoolDay.FRIDAY),
            new TimeBlock(ELEVEN_AM, SEVEN_PM, SchoolDay.FRIDAY)},
            mergedIntervals.toArray());
    }

    @Test
    public void getCommonIntervals_timetablesVariant3Monday_twoTimeBlock() {
        List<Timetable> timetables = List.of(TIMETABLE_B, TIMETABLE_D, TIMETABLE_E);
        List<TimeSlot> intervals = TimeUtils.getFreeCommonIntervals(SchoolDay.MONDAY, timetables);
        List<TimePeriod> mergedIntervals = TimeUtils.mergeTimeSlots(intervals);
        assertFalse(mergedIntervals.isEmpty());
        assertEquals(2, mergedIntervals.size());
        // expecting 8-10am, 12-10pm
        assertArrayEquals(new TimeBlock[]{
            new TimeBlock(EIGHT_AM, TEN_AM, SchoolDay.MONDAY),
            new TimeBlock(TWELVE_PM, TEN_PM, SchoolDay.MONDAY)},
            mergedIntervals.toArray());
    }

    @Test
    public void getCommonIntervals_timetablesVariant3Tuesday_threeTimeBlock() {
        List<Timetable> timetables = List.of(TIMETABLE_D, TIMETABLE_B, TIMETABLE_E);
        List<TimeSlot> intervals = TimeUtils.getFreeCommonIntervals(SchoolDay.TUESDAY, timetables);
        List<TimePeriod> mergedIntervals = TimeUtils.mergeTimeSlots(intervals);
        assertFalse(mergedIntervals.isEmpty());
        assertEquals(3, mergedIntervals.size());
        // expecting 8-10am, 12-2pm, 5-10pm
        assertArrayEquals(new TimeBlock[]{
            new TimeBlock(EIGHT_AM, TEN_AM, SchoolDay.TUESDAY),
            new TimeBlock(TWELVE_PM, TWO_PM, SchoolDay.TUESDAY),
            new TimeBlock(FIVE_PM, TEN_PM, SchoolDay.TUESDAY)},
            mergedIntervals.toArray());
    }

    @Test
    public void getCommonIntervals_timetablesVariant3Wednesday_threeTimeBlock() {
        List<Timetable> timetables = List.of(TIMETABLE_B, TIMETABLE_D, TIMETABLE_E);
        List<TimeSlot> intervals = TimeUtils.getFreeCommonIntervals(SchoolDay.WEDNESDAY, timetables);
        List<TimePeriod> mergedIntervals = TimeUtils.mergeTimeSlots(intervals);
        assertFalse(mergedIntervals.isEmpty());
        assertEquals(3, mergedIntervals.size());
        // expecting 8am-4pm, 5pm-6pm, 7pm-10pm
        assertArrayEquals(new TimeBlock[]{
            new TimeBlock(EIGHT_AM, FOUR_PM, SchoolDay.WEDNESDAY),
            new TimeBlock(FIVE_PM, SIX_PM, SchoolDay.WEDNESDAY),
            new TimeBlock(SEVEN_PM, TEN_PM, SchoolDay.WEDNESDAY)},
            mergedIntervals.toArray());
    }

    @Test
    public void getCommonIntervals_timetablesVariant3Thursday_threeTimeBlock() {
        List<Timetable> timetables = List.of(TIMETABLE_B, TIMETABLE_D, TIMETABLE_E);
        List<TimeSlot> intervals = TimeUtils.getFreeCommonIntervals(SchoolDay.THURSDAY, timetables);
        List<TimePeriod> mergedIntervals = TimeUtils.mergeTimeSlots(intervals);
        assertFalse(mergedIntervals.isEmpty());
        assertEquals(3, mergedIntervals.size());
        // expecting 8am-11am, 1pm-5pm, 9-10pm
        assertArrayEquals(new TimeBlock[]{
            new TimeBlock(EIGHT_AM, ELEVEN_AM, SchoolDay.THURSDAY),
            new TimeBlock(ONE_PM, FIVE_PM, SchoolDay.THURSDAY),
            new TimeBlock(NINE_PM, TEN_PM, SchoolDay.THURSDAY)},
            mergedIntervals.toArray());
    }

    @Test
    public void getCommonIntervals_timetablesVariant3Friday_twoTimeBlock() {
        List<Timetable> timetables = List.of(TIMETABLE_B, TIMETABLE_E, TIMETABLE_D);
        List<TimeSlot> intervals = TimeUtils.getFreeCommonIntervals(SchoolDay.FRIDAY, timetables);
        List<TimePeriod> mergedIntervals = TimeUtils.mergeTimeSlots(intervals);
        assertFalse(mergedIntervals.isEmpty());
        assertEquals(2, mergedIntervals.size());
        // expecting 8am-9am, 11am-7pm
        assertArrayEquals(new TimeBlock[]{
            new TimeBlock(EIGHT_AM, NINE_AM, SchoolDay.FRIDAY),
            new TimeBlock(ELEVEN_AM, SEVEN_PM, SchoolDay.FRIDAY)},
            mergedIntervals.toArray());
    }

    @Test
    public void getCommonIntervals_fiveTimeTablesTuesday_success() {
        List<Timetable> timetables = List.of(TIMETABLE_A, TIMETABLE_B, TIMETABLE_C, TIMETABLE_D, TIMETABLE_E);
        List<TimeSlot> intervals = TimeUtils.getFreeCommonIntervals(SchoolDay.TUESDAY, timetables);
        List<TimePeriod> mergedIntervals = TimeUtils.mergeTimeSlots(intervals);
        assertFalse(mergedIntervals.isEmpty());
        //expecting 12-2pm, 5pm - 10pm
        assertEquals(2, mergedIntervals.size());
        assertArrayEquals(new TimeBlock[]{
            new TimeBlock(TWELVE_PM, TWO_PM, SchoolDay.TUESDAY),
            new TimeBlock(FIVE_PM, TEN_PM, SchoolDay.TUESDAY)},
            mergedIntervals.toArray());
    }

    @Test
    public void getIntervals_nineDisjointIntervalsNonIntersecting_threeTimeBlocks() {
        SchoolDay schoolDay = SchoolDay.MONDAY;
        List<TimeSlot> timeSlots = List.of(new TimeSlot(EIGHT_AM, schoolDay),
            new TimeSlot(NINE_AM, schoolDay), new TimeSlot(ONE_PM, schoolDay), new TimeSlot(TWO_PM, schoolDay),
            new TimeSlot(THREE_PM, schoolDay), new TimeSlot(FIVE_PM, schoolDay), new TimeSlot(SIX_PM, schoolDay),
            new TimeSlot(SEVEN_PM, schoolDay), new TimeSlot(EIGHT_PM, schoolDay));
        List<TimePeriod> mergedIntervals = TimeUtils.mergeTimeSlots(timeSlots);
        //expecting 8am-10am, 1pm-4pm, 5pm-9pm
        assertFalse(mergedIntervals.isEmpty());
        assertEquals(3, mergedIntervals.size());
        assertArrayEquals(new TimeBlock[]{new TimeBlock(EIGHT_AM, TEN_AM, schoolDay),
            new TimeBlock(ONE_PM, FOUR_PM, schoolDay), new TimeBlock(FIVE_PM, NINE_PM, schoolDay)},
            mergedIntervals.toArray());
    }

    @Test
    public void getIntervals_thirteenDisjointIntervalIntersecting_oneTimeBlock() {
        SchoolDay schoolDay = SchoolDay.MONDAY;
        List<TimeSlot> timeSlots = List.of(new TimeSlot(EIGHT_AM, schoolDay),
            new TimeSlot(NINE_AM, schoolDay), new TimeSlot(TEN_AM, schoolDay),
            new TimeSlot(ELEVEN_AM, schoolDay), new TimeSlot(TWELVE_PM, schoolDay),
            new TimeSlot(ONE_PM, schoolDay), new TimeSlot(TWO_PM, schoolDay),
            new TimeSlot(THREE_PM, schoolDay), new TimeSlot(FOUR_PM, schoolDay),
            new TimeSlot(FIVE_PM, schoolDay), new TimeSlot(SIX_PM, schoolDay),
            new TimeSlot(SEVEN_PM, schoolDay), new TimeSlot(EIGHT_PM, schoolDay));
        List<TimePeriod> mergedIntervals = TimeUtils.mergeTimeSlots(timeSlots);
        //expecting 8am-9pm
        assertFalse(mergedIntervals.isEmpty());
        assertEquals(1, mergedIntervals.size());
        assertArrayEquals(new TimeBlock[]{new TimeBlock(EIGHT_AM, NINE_PM, schoolDay)},
                mergedIntervals.toArray());
    }

    @Test
    public void testIntervals_emptyTimeSlots_emptyMerge() {
        List<TimeSlot> timeSlots = List.of();
        List<TimePeriod> mergedIntervals = TimeUtils.mergeTimeSlots(timeSlots);
        assertTrue(mergedIntervals.isEmpty());
    }

    @Test
    public void testFormating_success() {
        assertEquals("8 AM", TimeUtils.formatLocalTime(EIGHT_AM));
        assertEquals("9 AM", TimeUtils.formatLocalTime(NINE_AM));
        assertEquals("10 AM", TimeUtils.formatLocalTime(TEN_AM));
        assertEquals("11 AM", TimeUtils.formatLocalTime(ELEVEN_AM));
        assertEquals("12 PM", TimeUtils.formatLocalTime(TWELVE_PM));
        assertEquals("1 PM", TimeUtils.formatLocalTime(ONE_PM));
        assertEquals("2 PM", TimeUtils.formatLocalTime(TWO_PM));
        assertEquals("3 PM", TimeUtils.formatLocalTime(THREE_PM));
        assertEquals("4 PM", TimeUtils.formatLocalTime(FOUR_PM));
        assertEquals("5 PM", TimeUtils.formatLocalTime(FIVE_PM));
        assertEquals("6 PM", TimeUtils.formatLocalTime(SIX_PM));
        assertEquals("7 PM", TimeUtils.formatLocalTime(SEVEN_PM));
        assertEquals("8 PM", TimeUtils.formatLocalTime(EIGHT_PM));
        assertEquals("9 PM", TimeUtils.formatLocalTime(NINE_PM));
        assertEquals("10 PM", TimeUtils.formatLocalTime(TEN_PM));
    }
}
