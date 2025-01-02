package com.example.SpringDemo.Scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.SpringDemo.Service.FineService;

@Component
public class FineTaskSheduler {

	@Autowired
	private FineService fineService;
	
	
	@Scheduled(cron = "0 0 0 * * ?")
	public void applyLateFees() {
		fineService.applyLateFeesToOverdueAccounts();
	}
}
