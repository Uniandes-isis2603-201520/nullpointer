(function (ng) {
    var mod = ng.module("viajeroModule");
    mod.controller('ViajeroC', ['$scope', '$element', 'viajeroS', function ($scope, $element, svc) {
            var userId = 0;
            var self = this;

            $scope.trips = [];
            $scope.currentTrip;
            $scope.today = new Date();
            
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
                },
                        responseError);
            };

            this.getTrips(userId);
            this.getTrip(0);
        }]);
})(window.angular);
