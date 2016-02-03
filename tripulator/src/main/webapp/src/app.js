
var app = angular.module("mainApp", ["ui.router"]);

app.config(['$logProvider', function ($logProvider) {
        $logProvider.debugEnabled(true);
    }]);

app.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise("/landingpage");
        $stateProvider
                .state('home', {
                    url: '/home',
                    controller: 'HomeController',
                    templateUrl: "src/modules/home/home.tpl.html"
                })
                .state('calendar', {
                    url: '/calendar',
                    controller: 'CalendarController',
                    templateUrl: "src/modules/calendar/calendar.tpl.html"
                })
                .state('day', {
                    url: '/day',
                    templateurl: "src/modules/day/day.tpl.html"
                })
                .state('photogallery', {
                    url: '/photogallery',
                    templateUrl: "src/modules/photogallery/photogallery.tpl.html"
                })
                .state('landingpage', {
                    url: 'landingpage',
                    templateUrl: "src/modules/landingpage/landingpage.tpl.html"
                })
                .state('event', {
                    url: '/event',
                    templateUrl: "src/modules/event/event.tpl.html"
                });
    }]);