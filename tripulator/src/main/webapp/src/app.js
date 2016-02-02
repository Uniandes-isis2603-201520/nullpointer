(function (ng) {

    var mod = ng.module("mainApp", ["ui.router"]);
    
    mod.config(['$logProvider', function ($logProvider) {
            $logProvider.debugEnabled(true);
        }]);

    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            $urlRouterProvider.otherwise("/home");
            $stateProvider
                    .state('home',{
                        url: '/home',
                        templateUrl: "src/modules/home/home.tpl.html"
                    })
                    .state('calendar',{
                        url: '/calendar',
                        templateUrl: "src/modules/calendar/calendar.tpl.html"
                    })
                    .state('evento', {
                        url: '/evento',
                        templateUrl: "src/modules/evento/evento.tpl.html"
                    });
        }]);
})(window.angular);