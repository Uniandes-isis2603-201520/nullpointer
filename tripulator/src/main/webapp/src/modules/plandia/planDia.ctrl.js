(function (ng) {
    var mod = ng.module("planDiaModule");
    
    mod.controller('PlanDiaController', ['$scope', 'planDiaService', 'dataSvc', function ($scope, svc, dataSvc) {
            
            var self = this;
            
            $scope.events = [];
            
            $scope.editMode = false;
            
            $scope.currentEvent = {
                "id": undefined,
                "title": "",
                "type": "",
                "image": "",
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
            
            this.showError = function (data) {
                alert(data);
            };

            function responseError(response) {
                self.showError(response);
            }
            
            $scope.fetchEvents = function () {
                console.log(dataSvc.userId+', '+dataSvc.tripId+', '+dataSvc.dayId);
                return svc.getEvents(dataSvc.userId, dataSvc.tripId, dataSvc.dayId).then(function (response) {
                    $scope.events = response.data;
                    console.log(response.data);
                    $scope.currentEvent = {"id": undefined};
                    $scope.editMode = false;
                    return response;
                }, responseError); 
            };
            
            $scope.createEvent = function (){
                $scope.editMode = true;
                $scope.currentEvent = {"id": undefined};
                $scope.$broadcast("post-create", $scope.currentRecord);
            };
            
            function indexOf(event) {
                var field = event.id !== undefined ? 'id' : 'cid';
                for (var i in $scope.events) {
                    if ($scope.events.hasOwnProperty(i)) {
                        var current = $scope.events[i];
                        if (current[field] === event[field]) {
                            return i;
                        }
                    }
                }
            }
            
            $scope.saveEvent = function () {
                console.log(dataSvc.userId+', '+dataSvc.tripId+', '+dataSvc.dayId);
                var event = $scope.currentEvent;
                if (event.id || event.cid) {
                    var i = indexOf(event);
                    $scope.events.splice(i, 1, event);
                } else {
                    var id = Math.floor(Math.random() * 10000);
                    event.id = id;
                    $scope.events.push(event);
                }
                $scope.editMode = false;
                var newEvents = $scope.events;
                return svc.replaceEvents(dataSvc.userId, dataSvc.tripId, dataSvc.dayId, newEvents).then(function (response) {
                    $scope.events = response.data;
                    console.log(response.data);
                    $scope.currentEvent = {"id": undefined};
                    return response;
                }, responseError); 
            };
            
            $scope.editEvent = function (event) {
                console.log("EDIT: " + dataSvc.userId+', '+dataSvc.tripId+', '+dataSvc.dayId+', '+event.id);
                $scope.currentEvent= event;
                $scope.editMode = true;
            };
            
            $scope.deleteEvent = function(event) {
                console.log("DELETE: " + dataSvc.userId+', '+dataSvc.tripId+', '+dataSvc.dayId+', '+event.id);
                return svc.removeEvent(dataSvc.userId, dataSvc.tripId, dataSvc.dayId, event.id).then(function () {
                    $scope.fetchEvents();
                }, responseError);
            };
       
            $scope.fetchEvents();
            
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