(function (ng) {
    var mod = ng.module("viajeroModule");
    mod.controller('ViajeroC', ['$scope', '$element', '$window', 'viajeroS', function ($scope, $element, $window, svc) {
            var userId = 1;
            var self = this;
            $scope.trips = [];
            $scope.currentTrip;
            $scope.today = new Date();
            $scope.menuOptions = [
                {
                    name: "Create",
                    active: false
                },
                {
                    name: "Edit",
                    active: false
                },
                {
                    name: "Delete",
                    active: false
                }
            ];
            $scope.menuActions = [
                {
                    name: "Overview",
                    active: false
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

            function selectFromMenu(element) {
                for (var i = 0; i < $scope.menuActions.length; i++) {
                    if ($scope.menuActions[i] === element)
                        $scope.menuActions[i].active = true;
                    else
                        $scope.menuActions[i].active = false;
                }
                for (var i = 0; i < $scope.menuOptions.length; i++) {
                    if ($scope.menuOptions[i] === element)
                        $scope.menuOptions[i].active = true;
                    else
                        $scope.menuOptions[i].active = false;
                }
            }

            function isMenuOptionSelected(element) {
                if (element.active)
                    return {"background": "rgba(180,209,255,0.5)"};
                return {};
            }

            function toggleMenu() {
                angular.element("#menu-toggle").click(function (e) {
                    e.preventDefault();
                    angular.element("#wrapper").toggleClass("toggled");
                });
            }

            this.showError = function (data) {
                alert("ERROR:" + data);
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
                }, responseError);
            };

            this.getItinerario = function (tripId) {
                svc.getItinerario(userId, tripId).then(function (response) {
                    $scope.currentTrip = response.data;
                    self.generateImage();
                    $scope.menuActions[0].active = true;
                },
                        function (response) {
                            alert("Please create a trip! You currently have none.");
                            $scope.menuOptions[0].active = true;
                            toggleMenu();
                        });
            };

            $scope.isTripSelected = function (trip) {
                if (trip === $scope.currentTrip) {
                    return {"background": "rgba(180, 209, 255, 0.5)"};
                }
                return {};
            };

            $scope.selectAction = function (action) {
                selectFromMenu(action);
            };

            $scope.selectOption = function (option) {
                selectFromMenu(option);
            };

            $scope.isActionSelected = function (action) {
                isMenuOptionSelected(action);
            };

            $scope.isOptionSelected = function (option) {
                isMenuOptionSelected(option);
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

            $scope.chart = {
                type: "GeoChart",
                data: [
                    ['Locale']
                ],
                options: {
                    width: "100%",
                    height: 500,
                    chartArea: {
                        left: 10,
                        top: 10,
                        bottom: 0,
                        height: "100%"
                    },
                    colorAxis: {
                        colors: ['#aec7e8', '#1f77b4']
                    },
                    displayMode: 'regions',
                    enableRegionInteractivity: true
                }
            };

            $scope.readyHandler = function (chartWrapper) {
                $window.google.visualization.events.addListener(chartWrapper.getChart(), 'regionClick', function (r) {
                    $scope.regionClick(r);
                });
            };
            $scope.regionClick = function (region) {
                var index = -1;
                for(var i = 1; i<$scope.chart.data.length; i++){
                    var curCountry = region.region.toString();
                    var clickedCountry = $scope.chart.data[i].toString();
                    if(curCountry === clickedCountry){
                        index = i;
                    }
                }
                if(index > -1)
                    $scope.chart.data.splice(index, 1);
                else
                    $scope.chart.data.push([ region.region ]);                
            };


        }]);
})(window.angular);
