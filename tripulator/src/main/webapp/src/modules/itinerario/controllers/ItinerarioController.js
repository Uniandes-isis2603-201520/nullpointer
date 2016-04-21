(function (ng) {

    var mod = ng.module("itinerarioModule");

    mod.controller('ItinerarioController', ['$scope', '$window', 'itinerarioService', 'dataSvc',
        function ($scope, $window, svc, dataSvc) {
            alert("Entro al controlador");
            $scope.days = [];
            $scope.showDayInfo = false;
            $scope.daynames = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
            $scope.selectedDay = {};
            $scope.trip;
            $scope.itinerario = {
                id: Number, //número de identificación de un itinerario,  
                nombre: String, // Nombre dado al itinerario.  
                fechaInicio: Date, // Fecha de inicio del itinerario en cuestión.  
                fechaFin: Date, // Fecha de fin del itinerario en cuestión.  
                planDias: [], // Arreglo que contiene todos los id de los días del itinerario.  
                multimedia: [], // Arreglo que contiene todos los id del multidemia asociada al itinerario.  
                mapa: [] // Arreglo que contiene todos los id necesarios para desplegar el mapa.
            };
            var self = this;
            var scrollY = 0;
            var userId = dataSvc.userId;
            /**
             * Simula lo que trae el backend.
             * @param {type} days
             * @param {type} startDate
             * @param {type} endDate
             * @returns {undefined}
             */
            function generateDays(days, startDate, endDate) {
                var i = new Date(startDate);
                while (i.getTime() !== endDate.getTime()) {
                    days.push(
                            {
                                date: i,
                                city: "none",
                                events: [],
                                valid: true
                            }
                    );
                    var j = new Date(i);
                    j.setDate(j.getDate() + 1);
                    i = j;
                }
            }
            /**
             * Le da formato a la entrada recibida por el backend.
             * Crea una matriz donde las filas son meses y las columnas dias de ese mes.
             * Si el mes no comienza en domingo, se le agrega un relleno a la matriz.
             * @param {type} days
             * @returns {undefined}
             */
            function formatDays(days) {
                var formattedDays = [];
                for (var i = 0; i < days.length; ) {
                    var daysInMonth = [];
                    var monthStart = days[i].date;
                    setCalendarOffset(daysInMonth, monthStart);
                    while (i < days.length && days[i].date.getMonth() === monthStart.getMonth()) {
                        daysInMonth.push(days[i]);
                        i++;
                    }
                    formattedDays.push(daysInMonth);
                }
                return formattedDays;
            }
            /**
             * Agrega días invalidos al principio del mes si este no empieza en domingo.
             * @param {type} array
             * @param {type} monthStart
             * @returns {undefined}
             */
            function setCalendarOffset(array, monthStart) {
                for (var i = 0; i < monthStart.getDay(); i++) {
                    var newDate = new Date(monthStart);
                    newDate.setDate(monthStart.getDate() - (monthStart.getDay() - i));
                    array.push({
                        date: newDate,
                        valid: false
                    });
                }
            }

            /**
             * Guarda el estado de todos los días del viaje.
             * @returns {undefined}
             */
            this.updateTrip = function () {
                $scope.trip["planDias"] = $scope.days;
                svc.updateItinerario(userId, $scope.trip.id, $scope.trip).then(function (response) {
                    return response.data;
                }, responseError);
            };
            /**
             * Le pide todos los datos de un viaje al servicio.
             * @param id
             * @returns {undefined}
             */
            this.getItinerario = function (id) {
                svc.getItinerario(userId, id).then(function (resolve) {
                    $scope.trip = resolve.data;
                    generateDays($scope.days, new Date($scope.trip["fechaInicio"])
                            , new Date($scope.trip["fechaFin"]));
                    $scope.days = formatDays($scope.days);
                }, responseError);
            };

            this.showError = function (data) {
                alert(data);
            };

            function responseError(response) {
                self.showError(response);
            }

            /**
             * Sets the square background depending of a series of tests.
             * @param {type} day
             * @param {type} highlight
             * @returns {ItinerarioController_L1.ItinerarioController_L5.$scope.squareBackground.ItinerarioControllerAnonym$7|ItinerarioController_L1.ItinerarioController_L5.$scope.squareBackground.ItinerarioControllerAnonym$8|ItinerarioController_L1.ItinerarioController_L5.$scope.squareBackground.ItinerarioControllerAnonym$9|ItinerarioController_L1.ItinerarioController_L5.$scope.squareBackground.ItinerarioControllerAnonym$5|ItinerarioController_L1.ItinerarioController_L5.$scope.squareBackground.ItinerarioControllerAnonym$6}
             */
            $scope.squareBackground = function (day, highlight) {

                if (!day.valid && !highlight) {
                    return{
                        "background-color": "rgba(180, 209, 255, 0.1)"
                    };
                } else if (!day.valid && highlight) {
                    return{
                        "background-color": "rgba(180, 209, 255, 0.1)"
                    };
                } else if (day.valid && !highlight) {
                    return{
                        "background-color": "rgba(94, 168, 167, 0.5)"
                    };
                } else if (day.valid && highlight) {
                    return{
                        "background-color": "rgba(94, 168, 167, 1)"
                    };
                }
            };
            /**
             * Activates day view.
             * @param {type} day
             * @returns {undefined}
             */
            $scope.toggleDayInfo = function (day) {
                if (!$scope.showDayInfo)
                    scrollY = $window.scrollY;

                $scope.showDayInfo = !day.invisible && day.valid && !$scope.showDayInfo;
                $scope.selectedDay = day;

                if ($scope.showDayInfo)
                    $window.scrollTo($window.scrollX, 0);
                else
                    $window.scrollTo($window.scrollX, scrollY);
            };

            this.getItinerario(dataSvc.tripId);
        }]);
})(window.angular);