(function (ng) {
    var mod = ng.module("viajeroModule");
    mod.controller('ViajeroC', ['$scope', '$element', 'viajeroS', function ($scope, $element, svc) {
            mod.service('TripService', function () {
                //to create unique trip id
                var uid = 1;

                //trips array to hold list of all trips
                var trips = [{
                        id: 0,
                        'name': 'Dubai!',
                        'algo': 'n',
                        'algomas': 'n'
                    }];

                //crea un viaje nuevo si no existe
                //modifica de lo contrario
                this.save = function (trip) {
                    if (trip.id == null) {
                        //si encuentra el viaje agregar array
                        trip.id = uid++;
                        trips.push(trip);
                    } else {
                        //modifica un viaje actual
                        for (i in trips) {
                            if (trips[i].id == trip.id) {
                                trips[i] = trip;
                            }
                        }
                    }

                }

                //busca el id de un viaje
                this.get = function (id) {
                    for (i in trips) {
                        if (trips[i].id == id) {
                            return trips[i];
                        }
                    }

                }

                //Busca y borra el viaje
                this.delete = function (id) {
                    for (i in trips) {
                        if (trips[i].id == id) {
                            trips.splice(i, 1);
                        }
                    }
                }

                //Devuelve la Lista de viajes
                this.list = function () {
                    return trips;
                }
            });

            mod.controller('TripController', function ($scope, TripService) {

                $scope.trips = TripService.list();

                $scope.saveTrip = function () {
                    TripService.save($scope.newtrip);
                    $scope.newtrip = {};
                }


                $scope.delete = function (id) {

                    TripService.delete(id);
                    if ($scope.newtrip.id == id)
                        $scope.newtrip = {};
                }


                $scope.edit = function (id) {
                    $scope.newtrip = ng.copy(TripService.get(id));
                }
            })


            var userId = 0;
            var self = this;

            $scope.trips = [];
            $scope.currentTrip;
            $scope.today = new Date();
            $scope.master = {};
            $scope.reset();
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
            $scope.users = UserService.list();

            $scope.saveUser = function () {
                UserService.save($scope.newuser);
                $scope.newuser = {};
            }


            $scope.delete = function (id) {

                UserService.delete(id);
                if ($scope.newuser.id == id)
                    $scope.newuser = {};
            }


            $scope.edit = function (id) {
                $scope.newuser = angular.copy(UserService.get(id));
            }

            function responseError(response) {
                self.showError(response.data);
            }

            this.showError = function (data) {
                alert(data);
            };

            this.generateImage = function () {
                $scope.currentTrip.images = [];
                for (var i = 0; i < 4; i++) {
                    $scope.currentTrip.images.push({
                        id: i,
                        src: 'http://lorempixel.com/' + ($element.width() + i) + '/500',
                        name: "image title"
                    });
                }
            };

            this.getTrips = function () {
                svc.getTrips(userId).then(function (response) {
                    $scope.trips = response;
                },
                        responseError);
            };

            this.getTrips = function () {
                svc.getTrips(userId).then(function (response) {
                    $scope.trips = response;
                },
                        responseError);
            };

            this.getTrip = function (tripId) {
                svc.getTrip(userId, tripId).then(function (response) {
                    $scope.currentTrip = response;
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

            this.getTrips(userId);
            this.getTrip(0);
        }]);
})(window.angular);
