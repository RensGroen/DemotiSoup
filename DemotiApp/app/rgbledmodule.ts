import {Component, OnInit} from 'angular2/core';
import { RouteParams } from 'angular2/router';
import {Http, Headers, RequestOptions} from 'angular2/http';
import {RgbModuleDTO} from './Model/rgbmoduledto';

@Component({
  selector: 'rgbled-module',
  templateUrl: '../html/rgbledmodule.html';
})

export class RGBLEDModule implements OnInit{  
	  
	  rgbModuleDTO: RgbModuleDTO;
	  
	  constructor(params: RouteParams, private _http: Http){
		this.rgbModuleDTO = new RgbModuleDTO();		  
		this.rgbModuleDTO.physicalLocationName = params.get('id');		
	  }	 

	  setColor(event){
			this.rgbModuleDTO.color = event.path[0].name;
			var headers = new Headers();
			headers.append('Content-Type', 'application/json');
			this._http.post('http://localhost:8080/rest/rgbmodule/changeColor', 
								   JSON.stringify(this.rgbModuleDTO),
								   {headers:headers})
			.map((res: Response) => res)
			.subscribe((res:String) => this.postResponse = res);
	  }
 }