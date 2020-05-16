package com.redapp.eclo.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.redapp.eclo.entities.Program;
import com.redapp.eclo.entities.ProgramRequest;
import com.redapp.eclo.entities.UserProgCount;


public interface ProgramsService {

	public List<Program> getPrograms();
	public Program addPrgram(ProgramRequest programRequest);
	public Program editProgram(Long month,Long year,ProgramRequest programRequest);
	
	
}
