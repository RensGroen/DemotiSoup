import {Component} from 'angular2/core';
import { HTTP_PROVIDERS } from 'angular2/http';
import {ROUTER_PROVIDERS, RouteConfig, ROUTER_DIRECTIVES} from 'angular2/router';
import { ModuleService } from './module.service';
import { ConfiguredModulesComponent } from './configuredmodules.component');

@Component({
  selector: 'my-app',
  templateUrl: '../html/app.html',
  directives: [ROUTER_DIRECTIVES],
  providers: [ModuleService, HTTP_PROVIDERS, ROUTER_PROVIDERS]
})
@RouteConfig([
{path: '/configuredModules', name: 'ConfiguredModules', component: ConfiguredModulesComponent, useAsDefault: true}
//{path: '/availableModules', name: 'AvailableModules', component: AvailableModulesComponent}
])
export class AppComponent{
}