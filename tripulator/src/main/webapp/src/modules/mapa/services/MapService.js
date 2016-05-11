(function (ng) {
    var mod = ng.module("mapsApp");
    mod.service("mapService", ["$http", function ($http) {
            this.getDias = function (userId, tripId) {
                return $http.get("api/viajeros/" + userId + "/itinerarios/" + tripId + "/dias");
            };
        }]);
})(window.angular);


