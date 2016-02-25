(function (ng) {
    var mod = ng.module("viajeroModule");
    mod.service('viajeroS', [function () {
            
            
            this.getValue1 = function () {
                return this.myValue1;
            };
            
            this.getValue2 = function () {
                return this.myValue2;
            };

            this.setValue1 = function (newValue) {
                this.myValue1 = newValue;
            };
            
            this.setValue2 = function (newValue) {
                this.myValue2 = newValue;
            };
            
        /**
        this.fetchViajes = function () {
            return $http.get(context);
        };


        this.irViaje = function (id) {
            return $http.get(context + "/" + id);
        };


        this.saveViaje = function (currentRecord) {
            if (currentRecord.id) {
                return $http.put(context + "/" + record.id, record);
            } else {
                return $http.post(context, record);
            }
        };

        this.deleteViaje = function (id) {
            return $http.delete(context + "/" + id);
        };  

        this.getViaje = function (id) {
            return $http.get(context + "/" + id + "/viajes");
        };


        this.removeViaje = function (authorId, bookId) {
            return $http.delete(context + "/viajes/" + viajesId);
        };
        */
        }]);
})(window.angular);