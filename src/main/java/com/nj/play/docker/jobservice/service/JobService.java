package com.nj.play.docker.jobservice.service;

import com.nj.play.docker.jobservice.dto.JobDto;
import com.nj.play.docker.jobservice.entity.Job;
import com.nj.play.docker.jobservice.repository.JobRepository;
import com.nj.play.docker.jobservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public Flux<JobDto> allJobs() {
        return this.jobRepository.findAll().map(EntityDtoUtil::toDto);
    }

    public Flux<JobDto> jobsBySkillsIn(Set<String> skills) {
        return this.jobRepository.findBySkillsIn(skills).map(EntityDtoUtil::toDto);
    }

    public Mono<JobDto> save(Mono<JobDto> mono) {
        return mono.map(EntityDtoUtil::toEntity)
                   .flatMap(this.jobRepository::save)
                   .map(EntityDtoUtil::toDto);
    }
}
