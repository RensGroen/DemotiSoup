import {Injectable} from 'angular2/core';
import { Http, Response } from 'angular2/http';
import {Observable} from 'rxjs/Observable';
import {Module} from './Model/module';

@Injectable()
export class ModuleService {
	constructor(private _http: Http) {}
	
	getConfiguredModules() {
		return this._http.get('http://localhost:8080/rest/modules/configuredModules')
			.map(res => <Module[]> res.json().modules)
			.do(modules => console.log(modules))
			.catch(this.handleError);
	}
	
	private handleError(error: Response){
		console.error(error);
		return Observable.throw(error.json().error || 'Server error');
	}
}