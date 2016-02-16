app.controller('HomeController', ['$scope','dayInformationService', function ($scope, dayInformationService) {
        $scope.setValue = function(value){
          dayInformationService.init += value;  
          alert(dayInformationService.init);
        };
        $scope.setInit = function(value){
          dayInformationService.init = value;  
          alert(dayInformationService.init);
        };
        $scope.setEnd = function(value){
          dayInformationService.end = value;  
          alert(dayInformationService.end);
        };
        
}]);

/**app.controller('MainController', ['$scope', function($scope) {
$scope.viajes = [
  { 
    image: 'http://digital.fespa.com/images/Amsterdam.jpg', 
    nombre: 'Amsterdam', 
    Fecha: '17/05/1996'
  }, 
  { 
    image: 'http://media-cdn.tripadvisor.com/media/photo-s/07/bd/a1/8a/downtown-dubai-cityscape.jpg', 
    nombre: 'Dubai',  
    fecha: '11/22/2005' 
  },
  ]
  }]);*/

angular.module('calendarioAgregar',
    ['ngMaterial', 'ngMessages']).controller('AppCtrl', function($scope) {
  $scope.myDate = new Date();
});
