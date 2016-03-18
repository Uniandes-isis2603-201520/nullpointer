(function (ng) {

    var mod = ng.module("itinerarioModule");

    mod.service("itinerarioService", ["$http","itinerarioContext",function ($http, context) {

            this.getItinerario = function (userId, tripId) {
                return $http.get(context + "/" + tripId + "/");
            };

            this.updateItinerario = function (userId,tripId, trip) {
                return $http.post(context + "/" + tripId + "/", trip);
            };
        }]);


})(window.angular);

