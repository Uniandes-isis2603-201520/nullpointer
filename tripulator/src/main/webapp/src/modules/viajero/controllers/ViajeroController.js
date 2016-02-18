app.controller('HomeController', ['$scope', 'dayInformationService', function ($scope, dayInformationService) {
        $scope.setValue = function (value) {
            dayInformationService.init += value;
            alert(dayInformationService.init);
        };
        $scope.setInit = function (value) {
            dayInformationService.init = value;
            alert(dayInformationService.init);
        };
        $scope.setEnd = function (value) {
            dayInformationService.end = value;
            alert(dayInformationService.end);
        };
        
        $scope.myDate = new Date();
        
        $scope.viajes = [
       /** {
        image: 'http://digital.fespa.com/images/Amsterdam.jpg',
                nombre: 'Amsterdam',
                Fecha: '17/05/1996'
        },
        {
        image: 'http://media-cdn.tripadvisor.com/media/photo-s/07/bd/a1/8a/downtown-dubai-cityscape.jpg',
                nombre: 'Dubai',
                fecha: '11/22/2005'
        },
        {
        image: 'https://i.imgur.com/6EUAQg4.png',
                nombre: 'PlaceHolder',
                fecha: 'DatePlaceHolder'
        },*/
        ]
        
    }]);
