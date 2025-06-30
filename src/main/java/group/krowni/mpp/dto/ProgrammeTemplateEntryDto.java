package group.krowni.mpp.dto;

import java.time.DayOfWeek;

public class ProgrammeTemplateEntryDto {
    private DayOfWeek dayOfWeek;
    private Long seanceId;

    public DayOfWeek getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(DayOfWeek dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public Long getSeanceId() { return seanceId; }
    public void setSeanceId(Long seanceId) { this.seanceId = seanceId; }
}