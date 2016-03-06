(function (ng) {
    var mod = ng.module("viajeroModule");
    mod.service('viajeroS', [function () {
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
            
            var idCount  = 2;
            
            this.getTrips = function (userId) {
                return new Promise(function (resolve, reject) {
                    if (trips.length !== 0) {
                        resolve(trips);
                    } else {
                        reject("Error occurred");
                    }
                });
            };
            
            this.getTrip = function(userId, tripId){
                return new Promise(function (resolve,reject){
                    if(true){
                        resolve(trips[tripId]);
                    } else {
                        reject("Error occurred");
                    }
                });
            };
            
            this.addTrip = function(trip){
                return new Promise(function (resolve,reject){
                    if(trip.id === null){
                        trips[idCount++] = trip;
                        resolve(true);
                    } else {
                        reject(false);
                    }
                });
            };
        }]);
})(window.angular);