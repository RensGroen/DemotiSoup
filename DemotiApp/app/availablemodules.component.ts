import {Component, OnInit} from 'angular2/core';
import { HTTP_PROVIDERS } from 'angular2/http';
import { ModuleService } from './module.service';
import {ROUTER_PROVIDERS, RouteConfig} from 'angular2/router';
import { Module } from './Model/module';

@Component({
  selector: 'model-view',
  templateUrl: '../html/availablemodules.html'
})

export class AvailableModulesComponent implements OnInit{  
	  availableModules: Module[];
	  errorMessage: String;
	  
	  constructor(private _moduleService: ModuleService){ }
	  
	  getAvailableModules(){	  
		  this._moduleService.getAvailableModules()
			.subscribe(
			availableModules => this.availableModules = availableModules),
			error => this.errorMessage = <any>error);				
	  }	 
	  
	  ngOnInit() {
		this.getAvailableModules();
	  }
 }