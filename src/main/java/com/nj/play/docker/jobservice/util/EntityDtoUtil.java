package com.nj.play.docker.jobservice.util;


import com.nj.play.docker.jobservice.dto.JobDto;
import com.nj.play.docker.jobservice.entity.Job;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {

    public static JobDto toDto(Job job){
        JobDto dto = new JobDto();
        BeanUtils.copyProperties(job, dto);
        dto.setHostName(AppUtil.getHostName());
        return dto;
    }

    public static Job toEntity(JobDto dto){
        Job job = new Job();
        BeanUtils.copyProperties(dto, job);
        return job;
    }

}

