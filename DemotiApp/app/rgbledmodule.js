System.register(['angular2/core', 'angular2/router', 'angular2/http', './Model/rgbmoduledto'], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_1, router_1, http_1, rgbmoduledto_1;
    var RGBLEDModule;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (router_1_1) {
                router_1 = router_1_1;
            },
            function (http_1_1) {
                http_1 = http_1_1;
            },
            function (rgbmoduledto_1_1) {
                rgbmoduledto_1 = rgbmoduledto_1_1;
            }],
        execute: function() {
            RGBLEDModule = (function () {
                function RGBLEDModule(params, _http) {
                    this._http = _http;
                    this.rgbModuleDTO = new rgbmoduledto_1.RgbModuleDTO();
                    this.rgbModuleDTO.physicalLocationName = params.get('id');
                }
                RGBLEDModule.prototype.setColor = function (event) {
                    var _this = this;
                    this.rgbModuleDTO.color = event.path[0].name;
                    var headers = new http_1.Headers();
                    headers.append('Content-Type', 'application/json');
                    this._http.post('http://localhost:8080/rest/rgbmodule/changeColor', JSON.stringify(this.rgbModuleDTO), { headers: headers })
                        .map(function (res) { return res; })
                        .subscribe(function (res) { return _this.postResponse = res; });
                };
                RGBLEDModule = __decorate([
                    core_1.Component({
                        selector: 'rgbled-module',
                        templateUrl: '../html/rgbledmodule.html'
                    }), 
                    __metadata('design:paramtypes', [router_1.RouteParams, http_1.Http])
                ], RGBLEDModule);
                return RGBLEDModule;
            }());
            exports_1("RGBLEDModule", RGBLEDModule);
        }
    }
});
//# sourceMappingURL=rgbledmodule.js.map