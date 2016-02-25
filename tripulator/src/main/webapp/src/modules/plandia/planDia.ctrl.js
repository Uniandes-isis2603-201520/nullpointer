(function (ng) {

    var mod = ng.module("planDiaModule");

    mod.controller('PlanDiaController', ['$scope', function ($scope) {

            var self = this;

            $scope.halfHours = ['00:00', '00:30', '01:00', '01:30', '02:00', '02:30', '03:00', '03:30', '04:00',
                '04:30', '05:00', '05:30', '06:00', '06:30', '07:00', '07:30', '08:00', '08:30', '09:00',
                '09:30', '10:00', '10:30', '11:00', '11:30', '12:00', '12:30', '13:00', '13:30', '14:00',
                '14:30', '15:00', '15:30', '16:00', '16:30', '17:00', '17:30', '18:00', '18:30', '19:00',
                '19:30', '20:00', '20:30', '21:00', '21:30', '22:00', '22:30', '23:00', '23:30'];

            $scope.hours = ['00:00', '01:00', '02:00', '03:00', '04:00', '05:00', '06:00', '07:00',
                '08:00', '09:00', '10:00', '11:00', '12:00', '13:00', '14:00', '15:00', '16:00', '17:00',
                '18:00', '19:00', '20:00', '21:00', '22:00', '23:00'];

            $scope.events = [];

            $scope.emptyEvent = {
                "title": "Nuevo evento",
                "image": "",
                "type": "",
                "start": "",
                "end": "",
                "description": "",
                "comments": []
            };

            $scope.daySections = [];

            /*
             * En caso que el service funcionara, se implementarían solo estos métodos y los métodos del $scope.
             * Sin embargo, al agregar el service al controlador no sirve el controlador
             * 
            function responseError(response) {
                self.showError(response.data);
            }
             
             this.getEvents = function () {
             svc.getEvents().then(function (response) {
             
             $scope.photos = response;
             
             }, responseError);
             };
             
            svc.createSections();
            svc.markOccupiedSections();
            
             */

            createSections();
            markOccupiedSections();

            $scope.hourClicked = function (index) {
                $scope.emptyEvent.start = $scope.halfHours[index];
                $scope.emptyEvent.end = $scope.halfHours[index + 2];
            };

            $scope.addNewEvent = function () {
                $scope.newEvent = angular.copy($scope.emptyEvent);
                var startHour = document.getElementById("newEventStartHour").value;
                var endHour = document.getElementById("newEventEndHour").value;
                var available = true;
                for (var i = getSectionIndex(startHour); i < getSectionIndex(endHour); i++) {
                    if (!$scope.daySections[i].available)
                        available = false;
                }
                if (available) {
                    $scope.newEvent.title = document.getElementById("newEventName").value;
                    $scope.newEvent.start = startHour;
                    $scope.newEvent.end = endHour;
                    $scope.events.push($scope.newEvent);

                    markOccupiedSections();
                } else {

                }
            };

            /*
             * COLORES:
             * Default (Gris): #777777
             * Primary (Azul): #337AB7
             * Success (Verde): #5CB85C
             * Info (Azul clarito): #5BC0DE
             * Warning (Naranja): #F0AD4E
             * Danger (Rojo): #D9534F
             */
            $scope.style = function (index) {
                if (!$scope.daySections[index].available) {
                    return {
                        "background-color": "#5CB85C",
                        "color": "#FFFFFF",
                    };
                }
            };

            function createSections() {
                var i = 0;
                while (i < $scope.halfHours.length) {
                    $scope.daySections[i] = {
                        hour: $scope.halfHours[i],
                        available: true,
                        event: null,
                        innerText: "",
                        conflict: false
                    };
                    i++;
                }
            }

            function markOccupiedSections() {
                for (var j = 0; j < $scope.events.length; j++) {
                    for (var i = getSectionIndex($scope.events[j].start); i < getSectionIndex($scope.events[j].end); i++) {
                        $scope.daySections[i].available = false;
                        $scope.daySections[i].event = $scope.events[j];
                    }
                    //TODO
                    var middleSection = Math.floor((getSectionIndex($scope.events[j].start) + getSectionIndex($scope.events[j].end)) / 2);
                    $scope.daySections[middleSection].text = $scope.events[j].title;
                }
            }

            function getSectionIndex(time) {
                var components = time.split(":");
                var hour = Number(components[0]);
                var minutes = Number(components[1]);
                return minutes >= 30 ? ((hour) * 2) + 1 : (hour) * 2;
            }

        }]);
})(window.angular);

/*
 var sectionType = $scope.daySections[index].event.type;
 if (sectionType === "culture") {
 return {
 "background-color": "#337AB7",
 "color": "#FFFFFF"
 };
 } else if (sectionType === "nature") {
 return {
 "background-color": "#5CB85C",
 "color": "#FFFFFF"
 };
 } else if (sectionType === "chill") {
 return {
 "background-color": "#5BC0DE",
 "color": "#FFFFFF"
 };
 } else if (sectionType === "food") {
 return {
 "background-color": "#F0AD4E",
 "color": "#FFFFFF"
 };
 } else if (sectionType === "extreme") {
 return {
 "background-color": "#D9534F",
 "color": "#FFFFFF"
 };
 } else {
 return {
 "background-color": "#777777",
 "color": "#FFFFFF"
 };
 }
 */
