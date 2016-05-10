(function (ng) {
    var mod = ng.module("viajeroModule");
    mod.service('viajeroS', ['$http','viajeroContext',function ($http, context) {
                        
            this.getItinerarios = function (userId) {
                return $http.get(context + "/" + userId+ "/itinerarios");
            };
            
            this.getItinerario = function(userId, tripId){
                return $http.get(context + "/" + userId+ "/itinerarios/" + tripId + "/");
            };
            
            this.addItinerario = function(userId, trip){
                if (trip.id) {
                    return $http.put(context + "/" + userId + "/itinerarios/" + trip.id, trip);
                } else {
                    return $http.post(context + "/" + userId + "/itinerarios/", trip);
                }
            };
            
            this.deleteItinerario = function (userId, id) {
                return $http.delete(context + "/" + userId + "/itinerarios/" + id);
            };
        }]);
})(window.angular);