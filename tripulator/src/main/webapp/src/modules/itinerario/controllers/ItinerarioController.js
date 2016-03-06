(function (ng) {
    
    var mod = ng.module("itinerarioModule");
    
    mod.controller('ItinerarioController', ['$scope', '$window', 'itinerarioService', function ($scope, $window, svc) {
            $scope.days;
            $scope.pagenum = 0;
            $scope.showFullMonth = false;
            $scope.showDayInfo = false;
            $scope.daynames = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
            $scope.selectedDay = {};
            var self = this;
            var startDate;
            var endDate;
            var scrollY = 0;

            /**
             * Le pide el día de comienzo y el día de fin del viaje al servicio.
             * @returns {undefined}
             */
            this.getDateBounds = function(){
                svc.getDateBounds().then(function(response){
                    startDate = response[0];
                    endDate = response[1];
                },
                responseError);
            };
            /**
             * Le pide los eventos de un día en especifico al servicio. 
             * @param {type} day
             * @returns {undefined}
             */
            this.getDayEvents = function(day){
                svc.getDayEvents(day).then(function(response){
                    alert(response);
                },responseError);
            };
            /**
             * Hace un update de todos los eventos registrados de un día en particular.
             * @param {type} day
             * @returns {undefined}
             */
            this.updateDayEvents = function(day){
                svc.updateDayEvents(day).then(function(response){
                    alert(response);
                },responseError);
            };
            /**
             * Guarda el estado de todos los días del viaje.
             * @returns {undefined}
             */
            this.saveTrip = function(){
                svc.saveTrip($scope.days).then(function(response){
                    alert(response);
                }, responseError);
            };
            /**
             * Le pide todos los datos de un viaje al servicio.
             * @param id
             * @returns {undefined}
             */
            this.getTrip = function(id, showFullMonth){
                svc.getTrip(id,showFullMonth).then(function(resolve){
                    $scope.days = resolve;
                }, responseError);
            };
            
            
            function responseError(response) {
                self.showError(response.data);
            }
                        

            
            /**
             * Displays next month. If the day view is active, changes to the next day.
             * @returns {undefined}
             */
            $scope.increasePageNum = function () {
                if (!$scope.showDayInfo && $scope.pagenum < $scope.days.length - 1) {
                    $scope.pagenum++;
                    $window.scrollTo($window.scrollX, 0);
                } else if ($scope.showDayInfo) {
                    var index = $scope.selectedDay.itemNum;
                    var pnum = $scope.selectedDay.pageNum;
                    if (index + 1 < $scope.days[pnum].length && $scope.days[pnum][index + 1].valid) {
                        $scope.selectedDay = $scope.days[pnum][index + 1];
                    } else if (pnum + 1 < $scope.days.length) {
                        $scope.pagenum++;
                        for (index = 0; $scope.days[$scope.pagenum][index].invisible; index++)
                            ;
                        $scope.selectedDay = $scope.days[$scope.pagenum][index];
                        scrollY = 0;
                    }
                }
            };
            /**
             * Display previous day. If day view is active, displays previous day.
             * @returns {undefined}
             */
            $scope.decreasePageNum = function () {
                if (!$scope.showDayInfo && $scope.pagenum > 0) {
                    $scope.pagenum--;
                    $window.scrollTo($window.scrollX, 0);
                } else if ($scope.showDayInfo) {
                    var index = $scope.selectedDay.itemNum;
                    var pnum = $scope.selectedDay.pageNum;
                    if (index - 1 >= 0 && $scope.days[pnum][index - 1].valid) {
                        $scope.selectedDay = $scope.days[pnum][index - 1];
                    } else if (pnum - 1 >= 0) {
                        $scope.pagenum--;
                        var index = $scope.days[$scope.pagenum].length - 1;
                        $scope.selectedDay = $scope.days[$scope.pagenum][index];
                        scrollY = 0;
                    }
                }
            };

            /**
             * Sets the square background depending of a series of tests.
             * @param {type} day
             * @param {type} highlight
             * @returns {ItinerarioController_L1.ItinerarioController_L5.$scope.squareBackground.ItinerarioControllerAnonym$7|ItinerarioController_L1.ItinerarioController_L5.$scope.squareBackground.ItinerarioControllerAnonym$8|ItinerarioController_L1.ItinerarioController_L5.$scope.squareBackground.ItinerarioControllerAnonym$9|ItinerarioController_L1.ItinerarioController_L5.$scope.squareBackground.ItinerarioControllerAnonym$5|ItinerarioController_L1.ItinerarioController_L5.$scope.squareBackground.ItinerarioControllerAnonym$6}
             */
            $scope.squareBackground = function (day, highlight) {
                if (day.invisible) {
                    return{
                        "background-color": "rgba(180, 209, 255, 0.1)",
                        "color": "rgba(180, 209, 255, 0)"
                    };
                }

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
            /**
             * Show/hide full month (including days that are not within trip bounds)
             * @returns {undefined}
             */
            $scope.toggleFullMonth = function () {
                $scope.showFullMonth = !$scope.showFullMonth;
                self.getTrip(0,$scope.showFullMonth);
            };
            /**
             * If full month is toggled, change glyphicon.
             * @returns {String}
             */
            $scope.toggleFullMonthArrow = function () {
                if ($scope.showFullMonth)
                    return "glyphicon glyphicon-triangle-top";
                else
                    return  "glyphicon glyphicon-triangle-bottom";
            };
            
            this.getTrip(0, $scope.showFullMonth);
        }]);
})(window.angular);