package com.evotie.officeportal.Repository;

import com.evotie.officeportal.Entity.PEDutyLog;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PEDutyRepository extends JpaRepository <PEDutyLog, Long>{

}
