package com.evotie.officeportal.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PEDutyDto {

    private Long Id;
    private String Name;
    private Date ElectionYear;
    private String ElectionType;
    private Date From;
    private Date To;
}
