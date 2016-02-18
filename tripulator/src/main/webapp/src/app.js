(function (ng) {

    var mod = ng.module("mainApp", [
        "ui.router",
        "ngAnimate",
        "itinerarioModule",
        "planDiaModule",
        "multimediaModule",
        "eventoModule",
        "viajeroModule"
    ]);

    mod.config(['$logProvider', function ($logProvider) {
            $logProvider.debugEnabled(true);
        }]);

    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
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
                        controllerAs: "ctrl",
                        templateUrl: "src/modules/itinerario/itinerario.tpl.html",
                    })
                    .state('plandia', {
                        url: '/plandia',
                        templateUrl: "src/modules/plandia/plandia.tpl.html",
                        controller: 'PlanDiaController'
                    })
                    .state('multimedia', {
                        url: '/multimedia',
                        controller: 'multimediaCtrl',
                        templateUrl: "src/modules/multimedia/multimedia.tpl.html"
                    })
                    .state('evento', {
                        url: '/evento',
                        controller: 'EventosController',
                        templateUrl: "src/modules/evento/evento.tpl.html",
                        resolve: {
                            load: function (eventosInfoService) {
                                return eventosInfoService.LoadData();
                            }
                        }
                    });
        }]);
})(window.angular);
