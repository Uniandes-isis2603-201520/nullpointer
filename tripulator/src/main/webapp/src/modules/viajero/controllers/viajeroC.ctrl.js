(function (ng) {
    var mod = ng.module("viajeroModule");
    mod.controller('ViajeroC', ['$scope', '$element', 'viajeroS', function ($scope, $element, svc) {
            var userId = 1;
            var self = this;
            $scope.trips = [];
            $scope.currentTrip;
            $scope.today = new Date();
            $scope.menuActions = [
                {
                    name: "Overview",
                    active: true
                },
                {
                    name: "Calendar",
                    active: false
                },
                {
                    name: "Map",
                    active: false
                },
                {
                    name: "Gallery",
                    active: false
                }
            ];

            function responseError(response) {
                self.showError(response.data);
            }

            this.showError = function (data) {
                alert("ERROR:" +  data);
            };

            this.generateImage = function () {
                $scope.currentTrip.multimedia = [];
                for (var i = 0; i < 4; i++) {
                    $scope.currentTrip.multimedia.push({
                        id: i,
                        src: 'http://lorempixel.com/' + ($element.width() + i) + '/500',
                        name: "image title"
                    });
                }
            };

            this.getItinerarios = function () {
                svc.getItinerarios(userId).then(function (response) {
                    $scope.trips = response.data;
                    return response;
                },
                        responseError);
            };

            this.getItinerario = function (tripId) {
                svc.getItinerario(userId, tripId).then(function (response) {
                    $scope.currentTrip = response.data;
                    self.generateImage();
                    $scope.$apply();
                },
                        responseError);
            };

            $scope.isTripSelected = function (trip) {
                if (trip === $scope.currentTrip) {
                    return {"background": "rgba(180, 209, 255, 0.5)"};
                }
                return {};
            };

            $scope.selectAction = function (action) {
                for (var i = 0; i < $scope.menuActions.length; i++) {
                    if ($scope.menuActions[i] === action)
                        $scope.menuActions[i].active = true;
                    else
                        $scope.menuActions[i].active = false;
                }
            };

            $scope.isActionSelected = function (action) {
                if (action.active)
                    return {"background": "rgba(180,209,255,0.5)"};
                return {};
            };

            $scope.selectView = function (action) {
                switch (action.name) {
                    case "Calendar":
                        return "viajero.itinerario()";
                        break;
                    case "Gallery":
                        return "viajero.multimedia()";
                        break;
                    case "Map":
                        return "viajero.mapa()";
                        break;
                    default:
                        return "";
                }
            };

            this.getItinerarios();
            this.getItinerario(1);
        }]);
})(window.angular);
