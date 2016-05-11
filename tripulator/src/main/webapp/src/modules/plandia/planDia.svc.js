(function (ng) {

    var mod = ng.module("planDiaModule");

    mod.service("planDiaService", ["$http", "diaContext", function ($http, context) {
            
            /**
             * Hace la petición GET necesaria para obtener los dias asociados a un itinerario de un viajero.
             * @param {type} idViajero
             * @param {type} idItinerario
             * @returns {unresolved}
             */
            this.fetchRecords = function (idViajero, idItinerario) {
                return $http.get(context+"/"+idViajero+"/"+idItinerario+"/dias");
            };
            
            /**
             * Hace la petición GET necesaria para obtener un día del itinerario de un viajero.
             * @param {type} idViajero
             * @param {type} idItinerario
             * @param {type} idDia
             * @returns {unresolved}
             */
            this.fetchRecord = function (idViajero, idItinerario, idDia) {
                return $http.get(context+"/"+idViajero+"/itinerarios/"+idItinerario+"/dias/"+idDia);
            };
            
            /**
             * Si se está creando un día sin id, se hace un POST para crear la nueva instancia
             * Si el evento que se está creando ya tiene id, se hace PUT para actualizar ese evento.
             * @param {type} currentRecord
             * @returns {unresolved}
             */
            this.saveRecord = function (idViajero, idItinerario, currentRecord) {
                if (currentRecord.id) {
                    return $http.put(context + "/" + idViajero + "/itinerarios" + idItinerario + "/dias/" + currentRecord.id, currentRecord);
                } else {
                    return $http.post(context + "/" + idViajero + "/itinerarios" + idItinerario + "/dias", currentRecord);
                }
            };
            
            /**
             * Se hace un una petición DELETE para eliminar el día indicado.
             * @param {type} id
             * @returns {unresolved}
             */
            this.deleteRecord = function (idViajero, idItinerario, idDia) {
                return $http.delete(context + "/viajeros/" + idViajero + "/itinerarios/" + idItinerario + "/dias/" + idDia);
            };
            
            /**
             * Se hace GET para obtener los eventos asociados a un día
             * @param {type} idViajero
             * @param {type} idItinerario
             * @param {type} idDia
             * @returns {unresolved}
             */
            this.getEvents = function(idViajero, idItinerario, idDia) {
                return $http.get(context + "/viajeros/" + idViajero + "/itinerarios/" + idItinerario + "/dias/" + idDia + "/eventos");
            };
            
            /**
             * Se hace GET para obtener el evento especificado.
             * @param {type} idViajero
             * @param {type} idItinerario
             * @param {type} idDia
             * @param {type} idEvento
             * @returns {unresolved}
             */
            this.getEvent = function(idViajero, idItinerario, idDia, idEvento){
               return $http.get(context + "/viajeros/" + idViajero + "/itinerarios/" + idItinerario + "/dias/" + idDia + "/eventos/" + idEvento);
            };
            
            /**
             * Se hace PUT para actualizar los eventos de un día.
             * @param {type} idViajero
             * @param {type} idItinerario
             * @param {type} idDia
             * @param {type} eventos
             * @returns {unresolved}
             */
            this.replaceEvents = function(idViajero, idItinerario, idDia, eventos){
                return $http.put(context + "/viajeros/" + idViajero + "/itinerarios/" + idItinerario + "/dias/" + idDia + "/eventos", eventos);
            };
            
            /**
             * Hace DELETE para eliminar un evento de un día.
             * @param {type} idViajero
             * @param {type} idItinerario
             * @param {type} idDia
             * @param {type} idEvento
             * @returns {unresolved}
             */
            this.removeEvent = function(idViajero, idItinerario, idDia, idEvento){
                return $http.delete(context + "/viajeros/" + idViajero + "/itinerarios/" + idItinerario + "/dias/" + idDia + "/eventos" + idEvento);
            };
    }]);

})(window.angular);