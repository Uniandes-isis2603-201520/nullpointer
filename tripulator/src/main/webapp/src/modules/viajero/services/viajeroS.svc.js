(function (ng) {
    var mod = ng.module("viajeroModule");
    mod.service('viajeroS', ['$http','viajeroContext',function ($http, context) {
            var trips = [
                {
                    id: 0,
                    name: "Eurotrip 2014",
                    startDate: new Date("2014-04-10"),
                    endDate: new Date("2014-04-20"),
                    events: [{},{},{},{}]
                },
                {
                    id: 1,
                    name: "Dubai 2015",
                    startDate: new Date("2015-05-01"),
                    endDate: new Date("2015-05-9"),
                    events: [{},{},{},{}]
                }
            ];
                        
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