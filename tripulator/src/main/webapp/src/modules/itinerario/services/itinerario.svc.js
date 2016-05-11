(function (ng) {

    var mod = ng.module("itinerarioModule");

    mod.service("itinerarioService", ["$http","itinerarioContext",function ($http, context) {

            this.getItinerario = function (userId, tripId) {
                return $http.get(context + "/" + userId + "/itinerarios/" + tripId + "/");
            };

            this.updateItinerario = function (userId,tripId, trip) {
                return $http.post(context + "/" + userId + "/itinerarios/" + tripId + "/", trip);
            };
            
            this.getDias = function(userId, tripId){
              return $http.get(context + "/" + userId + "/itinerarios/" + tripId + "/dias/");  
            };
        }]);


})(window.angular);

