app.controller('PlanDiaController', ['$scope', function ($scope) {

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
                "title": "Visita a Moserrate",
                "image": "",
                "type": "",
                "start": "9:00",
                "end": "12:30",
                "description": "",
                "comments": []
            },
            {
                "title": "Museo del oro",
                "image": "",
                "type": "",
                "start": "13:00",
                "end": "16:00",
                "description": "",
                "comments": []
            }
        ];
        $scope.hourClicked = function (index) {
            $scope.indexClicked = index;
        };

        createSections();
        markOccupiedSections();

        $scope.createNewEvent = function (startTime) {
            $scope.newEvent = angular.copy($scope.emptyEvent);
            document.getElementById("newEventStartHour").value = $scope.events[startTime];
            document.getElementById("newEventEndHour").value = $scope.events[startTime + 2];
        };

        $scope.addNewEvent = function () {
            $scope.newEvent.title = document.getElementById("newEventName").value;
            $scope.newEvent.start = document.getElementById("newEventStartHour").value;
            $scope.newEvent.end = document.getElementById("newEventEndHour").value;
            $scope.events.push($scope.newEvent);
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
                for (var i = getTimeIndex($scope.events[j].start); i < getTimeIndex($scope.events[j].end); i++) {
                    $scope.daySections[i].available = false;
                    $scope.daySections[i].event = $scope.events[j];
                }
                var middleSection = String(Math.floor(($scope.events[j].start+$scope.events[j].end)/2));
                //document.getElementById(middleSection).innerHTML = $scope.events[j].title;
            }
        }

        function getTimeIndex(time) {
            var components = time.split(":");
            var hour = Number(components[0]);
            var minutes = Number(components[1]);
            return minutes > 0 ? ((hour) * 2) + 1 : (hour) * 2;
        }

        $scope.style = function (index) {
            if (!$scope.daySections[index].available) {
                return {
                    "background-color": "#85CA97"
                };
            }
        };
    }]);