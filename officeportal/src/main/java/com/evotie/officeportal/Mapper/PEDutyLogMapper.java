package com.evotie.officeportal.Mapper;

import com.evotie.officeportal.DTO.PEDutyDto;
import com.evotie.officeportal.Entity.PEDutyLog;

public class PEDutyLogMapper {

    public static PEDutyDto mapToPEDutyDto(PEDutyLog peDutyLog){
        return new PEDutyDto(
                peDutyLog.getID(),
                peDutyLog.getName(),
                peDutyLog.getElectionYear(),
                peDutyLog.getElectionType(),
                peDutyLog.getTo(),
                peDutyLog.getFrom()
        );
    }

    public static  PEDutyLog maptoPEDutyLog(PEDutyDto peDutyDto){
        return new PEDutyLog();
    }
}
