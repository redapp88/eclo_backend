package com.redapp.eclo.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.redapp.eclo.dao.ProgramRepository;
import com.redapp.eclo.entities.Program;
import com.redapp.eclo.entities.ProgramRequest;
@Service
@Transactional
public class ProgramsServiceImp implements ProgramsService {
@Autowired
ProgramRepository programRepository;
	@Override
	public List<Program> getPrograms() {
		return this.programRepository.findAllSortedByPeriode();
	}
	@Override
	public Program addPrgram(ProgramRequest programRequest) {
		String programId=programRequest.getMonth().toString()+programRequest.getYear().toString();
		Optional<Program> programOption = this.programRepository.findById(programId);
		if(programOption.isPresent())
			throw new RuntimeException("هذا البرنامج سبق اظافته");
		
		return this.programRepository.save(new Program(programRequest.getMonth(),programRequest.getYear(),programRequest.getHijri()));
	}
	@Override
	public Program editProgram(Long month,Long year,ProgramRequest programRequest) {
		String programId=month.toString()+year.toString();
		Optional<Program> programOption = this.programRepository.findById(programId);
		if(!programOption.isPresent())
			throw new RuntimeException("برنامج غيرموجود");
		Program oldProgram = programOption.get();
		oldProgram.setHijri(programRequest.getHijri());
		oldProgram.setStatus(programRequest.getStatus());
		return oldProgram;
	}
	@Override
	public Program getProgram(Long month, Long year) {
		Optional<Program> programOpt = this.programRepository.findById(month+""+year);
		if(!programOpt.isPresent())
			return null;
		else return programOpt.get();
	}
	@Override
	public void deleteProgram(String programId) {
		if(!this.programRepository.findById(programId).isPresent())
			throw new RuntimeException("برنامج غيرموجود");
		
		this.programRepository.deleteById(programId);
		
	}

	

}
