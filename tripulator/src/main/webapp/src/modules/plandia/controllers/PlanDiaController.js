app.controller('PlanDiaController', ['$scope', '$window', function ($scope, $window) {

        $scope.hours = ['00:00', '00:30', '01:00', '01:30', '02:00', '02:30', '03:00', '03:30', '04:00',
            '04:30', '05:00', '05:30', '06:00', '06:30', '07:00', '07:30', '08:00', '08:30', '09:00',
            '09:30', '10:00', '10:30', '11:00', '11:30', '12:00', '12:30', '13:00', '13:30', '14:00',
            '14:30', '15:00', '15:30', '16:00', '16:30', '17:00', '17:30', '18:00', '18:30', '19:00',
            '19:30', '20:00', '20:30', '21:00', '21:30', '22:00', '22:30', '23:00', '23:30'];
        $scope.daySections = [];
        $scope.indexClicked;
        $scope.emptyEvent = {
            "title": "",
            "image": "",
            "type": "",
            "start": "",
            "end": "",
            "description": "",
            "comments": []
        };
        $scope.events = [
            {
                "title": "Caminata por la quebrada 'La Vieja'",
                "image": "",
                "type": "nature",
                "start": "7:00",
                "end": "9:00",
                "description": "",
                "comments": []
            },
            {
                "title": "Visita a Moserrate",
                "image": "",
                "type": "chill",
                "start": "9:00",
                "end": "12:30",
                "description": "",
                "comments": []
            },
            {
                "title": "Almuerzo en Juan Chepe",
                "image": "",
                "type": "food",
                "start": "13:00",
                "end": "14:30",
                "description": "",
                "comments": []
            },
            {
                "title": "Museo del oro",
                "image": "",
                "type": "culture",
                "start": "15:00",
                "end": "19:00",
                "description": "",
                "comments": []
            }
        ];

        createSections();
        markOccupiedSections();

        $scope.hourClicked = function (index) {
            $scope.indexClicked = index;
        };

        $scope.createNewEvent = function (startTime) {
            document.getElementById("newEventStartHour").value = $scope.events[startTime];
            document.getElementById("newEventEndHour").value = $scope.events[startTime + 2];
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
            }
        };

        function createSections() {
            for (var i = 0; i < $scope.hours.length; i++) {
                $scope.daySections[i] = {
                    hour: $scope.hours[i],
                    available: true,
                    event: null
                };
            }
        }

        function markOccupiedSections() {
            for (var j = 0; j < $scope.events.length; j++) {
                for (var i = getSectionIndex($scope.events[j].start); i < getSectionIndex($scope.events[j].end); i++) {
                    $scope.daySections[i].available = false;
                    $scope.daySections[i].event = $scope.events[j];
                }
                //TODO
                var middleSection = String(Math.floor(($scope.events[j].start + $scope.events[j].end) / 2));
            }
        }

        function getSectionIndex(time) {
            var components = time.split(":");
            var hour = Number(components[0]);
            var minutes = Number(components[1]);
            return minutes > 0 ? ((hour) * 2) + 1 : (hour) * 2;
        }

    }]);