(function (ng) {
    
    var mod = ng.module("multimediaModule");

    mod.service("multimediaService", ["$http", "multimediaContext", function ($http, context) {
            
        
   /**
         * Obtener la lista de fotos.
         * Hace una petición GET con $http a /f para obtener la lista
         * de fotos
         * @returns {promise} promise para leer la respuesta del servidor}
         * Devuelve una lista de objetos de foto con sus atributos
         */
        this.fetchRecords = function (idViajero, idItinerario) {
           
            return $http.get(context+idViajero+"/itinerarios/"+idItinerario+"/fotos");
        };

        /**
         * Guardar un registro de foto.
         * Si currentRecord tiene la propiedad id, hace un PUT a /fotos/:id con los
         * nuevos datos de la instancia de fotos.
         * Si currentRecord no tiene la propiedad id, se hace un POST a /foto
         * para crear el nuevo registro de fotos
         * @param {object} currentRecord instancia de foto a guardar/actualizar
         * @returns {promise} promise para leer la respuesta del servidor
         * Devuelve un objeto de fotos con sus datos incluyendo el id
         */
        this.saveRecord = function (idViajero, idItinerario, currentRecord) {
            
            return $http.post(context+idViajero+"/itinerarios/"+idItinerario+"/fotos/" + currentRecord.id, currentRecord);
          
        };

        /**
         * Hace una petición DELETE a /fotos/:id para eliminar una foto
         * @param {number} id identificador de la instancia de foto a eliminar
         * @returns {promise} promise para leer la respuesta del servidor
         * No devuelve datos.
         */
        this.deleteRecord = function (idViajero, idItinerario, id) {
            return $http.delete(context+idViajero+"/itinerarios/"+idItinerario+"/fotos/"+id);
        };
             
    }]);
})(window.angular);