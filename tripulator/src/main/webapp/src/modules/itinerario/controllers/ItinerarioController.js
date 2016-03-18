(function (ng) {

    var mod = ng.module("itinerarioModule");

    mod.controller('ItinerarioController', ['$scope', '$window', 'itinerarioService', function ($scope, $window, svc) {

            $scope.days;
            $scope.showDayInfo = false;
            $scope.daynames = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
            $scope.selectedDay = {};
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

            /**
             * Le pide los eventos de un día en especifico al servicio. 
             * @param {type} day
             * @returns {undefined}
             */
            this.getDayEvents = function (day) {
                svc.getDayEvents(day).then(function (response) {
                    alert(response);
                }, responseError);
            };
            /**
             * Hace un update de todos los eventos registrados de un día en particular.
             * @param {type} day
             * @returns {undefined}
             */
            this.updateDayEvents = function (day) {
                svc.updateDayEvents(day).then(function (response) {
                    alert(response);
                }, responseError);
            };
            /**
             * Guarda el estado de todos los días del viaje.
             * @returns {undefined}
             */
            this.saveTrip = function () {
                svc.saveTrip($scope.days).then(function (response) {
                    alert(response);
                }, responseError);
            };
            /**
             * Le pide todos los datos de un viaje al servicio.
             * @param id
             * @returns {undefined}
             */
            this.getTrip = function (id) {
                svc.getTrip(id).then(function (resolve) {
                    $scope.days = resolve;
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

            this.getTrip(0);
        }]);
})(window.angular);