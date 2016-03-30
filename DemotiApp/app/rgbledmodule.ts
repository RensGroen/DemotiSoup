import {Component, OnInit} from 'angular2/core';
import { RouteParams } from 'angular2/router';
import {Http, Headers, RequestOptions} from 'angular2/http';

@Component({
  selector: 'rgbled-module',
  templateUrl: '../html/rgbledmodule.html';
})

//@Injectable()
export class RGBLEDModule implements OnInit{  
	  
	  pyhiscalLocation: String;
	  
	  constructor(params: RouteParams, private _http: Http){ 		
		this.pyhiscalLocation = params.get('id');		
	  }	 
	  
	  ngOnInit() {
		
	  }
	  
	  setColor(event){
		//console.log(event.path[0].name);  
		//console.log(JSON.stringify(event.path[0].name));
		let body = JSON.stringify(event.path[0].name);
		let headers = new Headers({ 'Content-Type': 'application/json' });
		let options = new RequestOptions({ headers: headers });
		console.log(body);
		
		this._http.post('http://localhost:8080/rest/rgbmodule/changeColor', body, options);
			//.map(res: Response) => res.json().data);
			//.subscribe((res:Response) => console.log(res));		
	  }
	  
	  setAnotherColor(event){
			var headers = new Headers();
			headers.append('Content-Type', 'application/json');
			this._http.post('http://localhost:8080/rest/rgbmodule/changeColor', 
								   JSON.stringify({color:event.path[0].name}),
								   {headers:headers})
			.map((res: Response) => res)
			.subscribe((res:String) => this.postResponse = res);
	  }
 }