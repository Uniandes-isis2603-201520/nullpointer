(function (ng) {
    var mod = ng.module("viajeroModule");
    mod.controller('ViajeroC', ['$scope', '$element', '$window', '$mdDialog', '$mdMedia', 'viajeroS',
        function ($scope, $element, $window, $mdDialog, $mdMedia, svc) {
            var userId = 1;
            var numTrips = 1;
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
                angular.element("#wrapper").toggleClass("toggled");
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
                    if ($scope.trips.length === 0) {
                        $scope.menuOptions[0].active = true;
                        initGeoChart();
                        toggleMenu();
                        $scope.showNoTripAlert(response);
                    }
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
                            $scope.menuOptions[0].active = true;
                            initGeoChart();
                            toggleMenu();
                            $scope.showNoTripAlert(response);
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
                if (option.name === 'Create') {
                    toggleMenu();
                    initGeoChart();
                    $scope.showNoTripAlert("none");
                }
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

            // CREATE A TRIP MODULE

            /**
             * Tracks whether the current chart has already a listener attached to it.
             * @type Boolean
             */
            var firstClick;

            /**
             * Holds all chart elements.
             */
            $scope.chart;

            /**
             * Tracks the current create trip page the user is on.
             */
            $scope.optionScreen;

            /**
             * Object that holds an array of cities for each country the trip has.
             */
            $scope.tripDetails;

            /**
             * Initializes all variables associated with creating a new trip.
             * @returns {undefined}
             */
            function initGeoChart() {
                $scope.optionScreen = 1;
                $scope.tripDetails = {};
                firstClick = false;
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
                            bottom: 0
                        },
                        colorAxis: {
                            colors: ['#aec7e8', '#1f77b4']
                        },
                        displayMode: 'regions',
                        enableRegionInteractivity: true
                    }
                };
            }

            /**
             * 
             * @param {String} country
             * @param {String} city
             * @param {String} arrivalDate
             * @param {String} departureDate
             * @returns {undefined}
             */
            $scope.addCity = function (country, city, arrivalDate, departureDate) {
                var citiesInCountry = $scope.tripDetails[country];
                for (var i = 0; i < citiesInCountry.length; i++) {
                    if (citiesInCountry[i].city === city &&
                            citiesInCountry[i].arrivalDate === arrivalDate &&
                            citiesInCountry[i].departureDate === departureDate) {
                        return;
                    }
                }
                citiesInCountry.push({
                    country: country,
                    city: city,
                    arrivalDate: arrivalDate,
                    departureDate: departureDate
                });
            };

            /**
             * Deletes a city it had already added.
             * @param {type} city
             * @returns {undefined}
             */
            $scope.deleteCity = function (city) {
                var citiesInCountry = $scope.tripDetails[city.country];
                alert("entro");
                if (city.city === 'Add a city to visit')
                    return;
                for (var i = 0; i < citiesInCountry.length; i++) {
                    if (citiesInCountry[i].city === city.city &&
                            citiesInCountry[i].arrivalDate === city.arrivalDate &&
                            citiesInCountry[i].departureDate === city.departureDate) {
                        citiesInCountry.splice(i, 1);
                    }
                    alert(citiesInCountry[i].country + " " + city.country + "\n"
                            + citiesInCountry[i].city + " " + city.city + "\n"
                            + citiesInCountry[i].arrivalDate + " " + city.arrivalDate + "\n"
                            + citiesInCountry[i].departureDate + " " + city.departureDate);
                }
            };

            /**
             * Removes the first element from the data array since it contains
             * the chart's metainfo header.
             * @returns {Array}
             */
            $scope.getCountryData = function () {
                return $scope.chart.data.slice(1, $scope.chart.data.length);
            };

            /**
             * Returns true if the city was already added. Otherwise, false.
             * @param {type} city
             * @returns {Boolean}
             */
            $scope.disableForm = function (city) {
                return !(city === 'Add a city to visit');
            };

            /**
             * Decides whether the geochart should be currently displayed.
             * @returns {Boolean}
             */
            $scope.showChart = function () {
                return $scope.optionScreen === 1;
            };

            /**
             * Decides whether the arrow that moves back in the create trip page
             * is shown.
             * @returns {Boolean}
             */
            $scope.showPrevArrow = function () {
                return $scope.optionScreen > 1;
            };
            /**
             * Decides whether the arrow that moves forward in the create trip
             * page is shown.
             * @returns {Boolean}
             */
            $scope.showNextArrow = function () {
                return $scope.optionScreen < 3;
            };

            /**
             * Moves to the next create trip page.
             * @returns {undefined}
             */
            $scope.nextPage = function (ev) {
                firstClick = false;
                if ($scope.optionScreen === 2) {
                    $scope.showCreateTripConfirm(ev);
                } else {
                    $scope.optionScreen++;
                }
            };
            /**
             * Moves to the previous create trip page.
             * @returns {undefined}
             */
            $scope.prevPage = function () {
                $scope.optionScreen--;
            };

            /**
             * Creates a listener for the chart.
             * @param {type} chartWrapper
             * @returns {undefined}
             */
            $scope.readyHandler = function (chartWrapper) {
                if (!firstClick) {
                    $window.google.visualization.events.addListener(chartWrapper.getChart(), 'regionClick', function (r) {
                        $scope.regionClick(r);
                    });
                    firstClick = true;
                }
            };

            /**
             * Function to be called every time an event is triggered on the geochart.
             * @param {type} region
             * @returns {undefined}
             */
            $scope.regionClick = function (region) {
                var index = -1;
                for (var i = 1; i < $scope.chart.data.length; i++) {
                    var curCountry = region.region.toString();
                    var clickedCountry = $scope.chart.data[i].toString();
                    if (curCountry === clickedCountry) {
                        index = i;
                    }
                }
                if (index > -1) {
                    var deleted = $scope.chart.data.splice(index, 1).toString();
                    $scope.tripDetails[deleted] = [];
                } else {
                    $scope.chart.data.push([region.region]);
                    var regionString = region.region.toString();
                    if (typeof $scope.tripDetails[regionString] === 'undefined') {
                        $scope.tripDetails[regionString] = [];
                        $scope.tripDetails[regionString].push({
                            country: regionString,
                            city: 'Add a city to visit',
                            arrivalDate: 'Arrival date',
                            departureDate: 'Departure date'
                        });
                    }
                }
                $scope.$apply();
            };

            function finishCreation() {
                toggleMenu();
                $scope.menuOptions[0].active = false;
            }
            /**
             * Given all the input, it will organize the data and generate a
             * trip. (Compatible with ItinerarioDTO)
             * @returns {undefined}
             */
            function getRelevantData() {
                var completeTrip = [];
                Object.keys($scope.tripDetails).forEach(function (key) {
                    completeTrip.concat($scope.tripDetails[key]);
                });

                for (var i = 0; i < completeTrip.length; i++) {
                    alert(completeTrip[i]);
                }

                completeTrip.sort(function (x, y) {
                    return x.arrivalDate - y.arrivalDate;
                });
                for (var i = 0; i < completeTrip.length; i++) {
                    alert(completeTrip[i]);
                }

                var fechaInicio = completeTrip[0];
                var fechaFin = completeTrip[completeTrip.length - 1];
                var nombre = "Not Supported";
                var id = numTrips++;
                var mapa = [];
                var multimedia = [];
                var planDias = []; // TO DO: Crear cada día de los rangos de fecha.

                var itinerario = {
                    id: id,
                    nombre: nombre,
                    fechaInicio: fechaInicio,
                    fechaFin: fechaFin,
                    planDias: planDias,
                    multimedia: multimedia,
                    mapa: mapa
                };

                return itinerario;
            }

            // DIALOG WINDOWS CONSTRUCTION
            $scope.showNoTripAlert = function (ev) {
                // Appending dialog to document.body to cover sidenav in docs app
                // Modal dialogs should fully cover application
                // to prevent interaction outside of dialog
                $mdDialog.show(
                        $mdDialog.alert()
                        .parent(angular.element(document.querySelector('#popupContainer')))
                        .clickOutsideToClose(true)
                        .title('It appears you have no trips to see!')
                        .textContent('We are going to start the creating process.')
                        .ariaLabel('Alert Dialog Demo')
                        .ok('Got it!')
                        .targetEvent(ev)
                        );
            };

            $scope.showCreateTripConfirm = function (ev) {
                // Appending dialog to document.body to cover sidenav in docs app
                var confirm = $mdDialog.confirm()
                        .title('Would you like to create your trip?')
                        .textContent('Make sure all the information you entered is correct.')
                        .ariaLabel('Lucky day')
                        .targetEvent(ev)
                        .ok('Yes!')
                        .cancel('Not yet');
                $mdDialog.show(confirm).then(function () {
                    finishCreation();
                    var itinerario = getRelevantData();
                    svc.addItinerario(userId, itinerario);
                    self.getItinerarios();
                    self.getItinerario(itinerario.id);
                }, function () {
                    alert("Algo anda muy mal!");
                });
            };

        }]);
})(window.angular);
