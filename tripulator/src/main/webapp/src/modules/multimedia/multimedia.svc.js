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
        this.fetchRecords = function () {
           
            return $http.get(context);
        };

        /**
         * Obtener una foto.
         * Hace una petición GET a /foto/:id para obtener
         * los datos de un registro específico de fotos
         * @param {number} id del registro a obtener
         * @returns {promise} promise para leer la respuesta del servidor
         * Devuelve un objeto de foto con sus atributos y reviews
         */
        this.fetchRecord = function (id) {
            return $http.get(context + "/" + id);
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
        this.saveRecord = function (currentRecord) {
            
            if (currentRecord.id) {
                return $http.put(context + "/" + currentRecord.id, currentRecord);
            } else {
               
                return $http.post(context, currentRecord);
            }
        };

        /**
         * Hace una petición DELETE a /fotos/:id para eliminar una foto
         * @param {number} id identificador de la instancia de foto a eliminar
         * @returns {promise} promise para leer la respuesta del servidor
         * No devuelve datos.
         */
        this.deleteRecord = function (id) {
            return $http.delete(context + "/" + id);
        };
             
    }]);
})(window.angular);