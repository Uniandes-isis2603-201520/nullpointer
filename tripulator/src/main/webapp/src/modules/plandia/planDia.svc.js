(function (ng) {

    var mod = ng.module("planDiaModule");

    mod.service("planDiaService", ["$http","planDiaContext",function ($http, context) {

            this.getDias = function (userId, tripId){
                return $http.get(context + "/");
            };
            
            this.getDia = function (userId, tripId, dayId) {
                return $http.get(context + "/" + dayId);
            };
            
            this.updateDia = function(userId, tripId, day){
                return $http.put(context + "/" + day.id, day);
            }
            
           this.saveRecord = function (newEvent, id) {
                return $http.post("http://localhost:8080/tripulator/api/eventos/"+id+"", newEvent);
            };
        }]);


})(window.angular);

