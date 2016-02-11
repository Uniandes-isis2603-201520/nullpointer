
var app = angular.module("mainApp", ["ui.router","ngAnimate"]);

app.config(['$logProvider', function ($logProvider) {
        $logProvider.debugEnabled(true);
    }]);

app.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise("/");
        $stateProvider
                .state('/', {
                    url: '/',
                    templateUrl: "src/modules/iniciosesion/iniciosesion.tpl.html"
                })
                .state('viajero', {
                    url: '/viajero',
                    controller: 'ViajeroController',
                    templateUrl: "src/modules/viajero/viajero.tpl.html"
                })
                .state('itinerario', {
                    url: '/itinerario',
                    controller: 'ItinerarioController',
                    templateUrl: "src/modules/itinerario/itinerario.tpl.html"
                })
                .state('plandia', {
                    url: '/plandia',
                    templateUrl: "src/modules/plandia/plandia.tpl.html"
                })
                .state('multimedia', {
                    url: '/multimedia',
                    controller: 'multimediaController',
                    templateUrl: "src/modules/multimedia/multimedia.tpl.html"
                })
                .state('evento', {
                    url: '/evento',
                    controller: 'EventosController',
                    templateUrl: "src/modules/evento/evento.tpl.html",
                    resolve:{
                    load:function(eventsInfoService){
                        return eventsInfoService.LoadData();
                    }
                }
                });
    }]);
