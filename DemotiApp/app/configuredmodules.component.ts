import {Component, OnInit} from 'angular2/core';
import { HTTP_PROVIDERS } from 'angular2/http';
import { ModuleService } from './module.service';
import {ROUTER_DIRECTIVES} from 'angular2/router';
import { Module } from './Model/module';

@Component({
  selector: 'model-view',
  templateUrl: '../html/configuredmodules.html',
  directives: [ROUTER_DIRECTIVES]
})

export class ConfiguredModulesComponent implements OnInit{  
	  configuredModules: Module[];
	  errorMessage: String;
	  configuredModulesLoaded = false;
	  
	  constructor(private _moduleService: ModuleService){ }
	  
	  getConfiguredModules(){
		  this._moduleService.getConfiguredModules()
			.subscribe(configuredModules => this.configuredModules = configuredModules;
					   this.configuredModulesLoaded = true);
	  }
	  
	  ngOnInit() {
		this.getConfiguredModules();
	  }
 }