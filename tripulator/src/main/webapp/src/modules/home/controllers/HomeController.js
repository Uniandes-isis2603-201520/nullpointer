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