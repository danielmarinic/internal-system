package sk.itaps.portal.domain.jpa.custom;

public interface DaysStatisticsTimesheet {
    Integer getWorkedDays();
    Integer getWorkDays();
    Integer getTotalDays();
    Double getTimeTotal();
}
