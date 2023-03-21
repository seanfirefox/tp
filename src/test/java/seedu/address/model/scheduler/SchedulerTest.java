package seedu.address.model.scheduler;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.timetable.util.TypicalTime.EIGHT_AM;
import static seedu.address.model.timetable.util.TypicalTime.TEN_PM;
import static seedu.address.model.timetable.util.TypicalTime.TWELVE_PM;
import static seedu.address.model.timetable.util.TypicalTimetable.FULL_CONFLICT_TIMETABLE_A;
import static seedu.address.model.timetable.util.TypicalTimetable.FULL_CONFLICT_TIMETABLE_B;
import static seedu.address.model.timetable.util.TypicalTimetable.TIMETABLE_A;
import static seedu.address.model.timetable.util.TypicalTimetable.TIMETABLE_B;
import static seedu.address.model.timetable.util.TypicalTimetable.TIMETABLE_C;
import static seedu.address.model.timetable.util.TypicalTimetable.TIMETABLE_D;
import static seedu.address.model.timetable.util.TypicalTimetable.TIMETABLE_E;
import static seedu.address.testutil.TypicalPersons.getTypicalEduMate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.joda.time.Hours;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ContactIndex;
import seedu.address.model.timetable.time.SchoolDay;
import seedu.address.model.timetable.time.TimePeriod;

class SchedulerTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalEduMate(), new UserPrefs());
    }

    @Test
    public void intialise_modelOnly_success() {
        assertDoesNotThrow(() -> new Scheduler(model));
    }

    @Test
    public void initialise_allValidContactIndices_success() {
        ContactIndex[] indices = { new ContactIndex(2),
            new ContactIndex(5),
            new ContactIndex(13)};
        Scheduler scheduler = new Scheduler(model);
        scheduler.initialise(new ArrayList<ContactIndex>(List.of(indices)));
        assertEquals(3, scheduler.getParticipants().size());
    }

    @Test
    public void initialise_someValidContactIndices_success() {
        ContactIndex[] indices = { new ContactIndex(2),
            new ContactIndex(998),
            new ContactIndex(3)};
        Scheduler scheduler = new Scheduler(model);
        scheduler.initialise(new ArrayList<ContactIndex>(List.of(indices)));
        assertEquals(2, scheduler.getParticipants().size());
    }

    @Test
    public void initialise_allInvalidContactIndicies_emptyParticipantList() {
        ContactIndex[] indices = { new ContactIndex(999),
            new ContactIndex(998),
            new ContactIndex(997)};
        Scheduler scheduler = new Scheduler(model);
        scheduler.initialise(new ArrayList<ContactIndex>(List.of(indices)));
        assertTrue(scheduler.getSchedules().isEmpty());
    }

    @Test
    public void testRecommendations_twoTimetables_success() {
        Scheduler scheduler = new Scheduler(model);
        scheduler.addTimetable(TIMETABLE_A);
        scheduler.addTimetable(TIMETABLE_B);
        // expected longest interval is 12noon - 10pm on Monday
        Optional<TimePeriod> longestInterval = scheduler.giveLongestTimingRecommendation();
        assertTrue(longestInterval.isPresent());
        assertEquals(Hours.hours(10), longestInterval.get().getHoursBetween());
        assertEquals(TWELVE_PM, longestInterval.get().getStartTime());
        assertEquals(TEN_PM, longestInterval.get().getEndTime());
        assertEquals(SchoolDay.MONDAY, longestInterval.get().getSchoolDay());
    }

    @Test
    public void testRecommendations_threeTimetablesVariant1_success() {
        Scheduler scheduler = new Scheduler(model);
        scheduler.addTimetable(TIMETABLE_A);
        scheduler.addTimetable(TIMETABLE_B);
        scheduler.addTimetable(TIMETABLE_C);
        // expected longest interval is 12noon - 10pm on Monday
        Optional<TimePeriod> longestInterval = scheduler.giveLongestTimingRecommendation();
        assertTrue(longestInterval.isPresent());
        assertEquals(Hours.hours(10), longestInterval.get().getHoursBetween());
        assertEquals(TWELVE_PM, longestInterval.get().getStartTime());
        assertEquals(TEN_PM, longestInterval.get().getEndTime());
        assertEquals(SchoolDay.MONDAY, longestInterval.get().getSchoolDay());
    }

    @Test
    public void testRecommendations_threeTimetablesVariant2_success() {
        Scheduler scheduler = new Scheduler(model);
        scheduler.addTimetable(TIMETABLE_C);
        scheduler.addTimetable(TIMETABLE_D);
        scheduler.addTimetable(TIMETABLE_E);
        // expected longest interval is 12noon - 10pm on Monday
        Optional<TimePeriod> longestInterval = scheduler.giveLongestTimingRecommendation();
        assertTrue(longestInterval.isPresent());
        assertEquals(Hours.hours(10), longestInterval.get().getHoursBetween());
        assertEquals(TWELVE_PM, longestInterval.get().getStartTime());
        assertEquals(TEN_PM, longestInterval.get().getEndTime());
        assertEquals(SchoolDay.MONDAY, longestInterval.get().getSchoolDay());
    }

    @Test
    public void testRecommendations_threeTimetablesVariant4_success() {
        Scheduler scheduler = new Scheduler(model);
        scheduler.addTimetable(TIMETABLE_A);
        scheduler.addTimetable(TIMETABLE_D);
        scheduler.addTimetable(TIMETABLE_E);
        // expected longest interval is 12noon - 10pm on Monday
        Optional<TimePeriod> longestInterval = scheduler.giveLongestTimingRecommendation();
        assertTrue(longestInterval.isPresent());
        assertEquals(Hours.hours(10), longestInterval.get().getHoursBetween());
        assertEquals(TWELVE_PM, longestInterval.get().getStartTime());
        assertEquals(TEN_PM, longestInterval.get().getEndTime());
        assertEquals(SchoolDay.MONDAY, longestInterval.get().getSchoolDay());
    }

    @Test
    public void testRecommendations_oneDayFullConflict_returnsWholeOtherDayTimeBlock() {
        Scheduler scheduler = new Scheduler(model);
        scheduler.addTimetable(FULL_CONFLICT_TIMETABLE_A);
        scheduler.addTimetable(FULL_CONFLICT_TIMETABLE_B);
        // expected longest interval 8am - 10pm
        Optional<TimePeriod> longestInterval = scheduler.giveLongestTimingRecommendation();
        assertTrue(longestInterval.isPresent());
        assertEquals(Hours.hours(14), longestInterval.get().getHoursBetween());
        assertEquals(EIGHT_AM, longestInterval.get().getStartTime());
        assertEquals(TEN_PM, longestInterval.get().getEndTime());
        assertEquals(SchoolDay.MONDAY, longestInterval.get().getSchoolDay());

    }

    @Test
    public void testRecommendations_oneDayFullConflictWithTimetableA_returnsWholeOtherDayTimeBlock() {
        Scheduler scheduler = new Scheduler(model);
        scheduler.addTimetable(TIMETABLE_A);
        scheduler.addTimetable(FULL_CONFLICT_TIMETABLE_A);
        scheduler.addTimetable(FULL_CONFLICT_TIMETABLE_B);
        // expected longest interval 8am - 10pm
        Optional<TimePeriod> longestInterval = scheduler.giveLongestTimingRecommendation();
        assertTrue(longestInterval.isPresent());
        assertEquals(Hours.hours(14), longestInterval.get().getHoursBetween());
        assertEquals(EIGHT_AM, longestInterval.get().getStartTime());
        assertEquals(TEN_PM, longestInterval.get().getEndTime());
        assertEquals(SchoolDay.WEDNESDAY, longestInterval.get().getSchoolDay());

    }

    @Test
    public void testRecommendations_emptyTimetable_emptyArrayList() {
        Scheduler scheduler = new Scheduler(model);
        Optional<TimePeriod> longestInterval = scheduler.giveLongestTimingRecommendation();
        assertTrue(longestInterval.isEmpty());
    }

    @Test
    public void equalityCheck_sameObjectModel_equals() {
        Scheduler scheduler = new Scheduler(model);
        assertEquals(scheduler.getModel(), model);
    }

}
