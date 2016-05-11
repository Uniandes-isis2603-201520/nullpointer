(function (ng) {
    var mod = ng.module("planDiaModule");
    
    mod.controller('PlanDiaController', ['$scope', 'planDiaService', function ($scope, svc) {
            
            var self = this;
            
            $scope.events = [];
            
            $scope.editMode = false;
            
            $scope.currentEvent = {
                "id": undefined,
                "title": "",
                "image": "",
                "type": "",
                "start": "",
                "end": "",
                "description": "",
                "comments": []
            };
            
            $scope.style = function(b){
              if (b){
                  return "timeline-inverted";
              }
              else {
                  return "timeline-not-inverted";
              }
            };
            
            $scope.fetchRecords = function () {
                $scope.editMode = false;  
            };
            
            $scope.createRecord = function (){
                $scope.editMode = true;  
            };
            
            $scope.editRecord = function () {
                $scope.editMode = true;
            };
            
            $scope.deleteRecord = function() {
                
            };
            
            /**
                $scope.events = [                
                {
                    "id": "2",
                    "title": "Ev2",
                    "image": "",
                    "type": "",
                    "start": "11:00",
                    "end": "13:00",
                    "description": "mhm mhm mmmmmhm mhm",
                    "comments": []                    
                },
                {
                    "id": "1",
                    "title": "Ev1",
                    "image": "",
                    "type": "",
                    "start": "07:00",
                    "end": "10:00",
                    "description": "blabla blablabla blabla",
                    "comments": []
                },
                {
                    "id": "3",
                    "title": "Ev3",
                    "image": "",
                    "type": "",
                    "start": "15:00",
                    "end": "19:00",
                    "description": "sisi sisisisi sisi",
                    "comments": []                   
                },
                                {
                    "id": "4",
                    "title": "Ev4",
                    "image": "",
                    "type": "",
                    "start": "12:00",
                    "end": "12:30",
                    "description": "adsfasdfasdfasdfasdf",
                    "comments": []                   
                },
                                {
                    "id": "5",
                    "title": "Ev5",
                    "image": "",
                    "type": "",
                    "start": "05:00",
                    "end": "06:00",
                    "description": "el madrugón",
                    "comments": []                   
                }
            ];
             */
        }]);
})(window.angular);