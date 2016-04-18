// src/modules/person/person.svc.js
// Servicio para el Modulo de personas

(function (ng) {

    var mod = ng.module("inicioSesionModule");

    mod.service("InicioSesionService", ["$http", "inicioContext", function ($http, context) {
            /**
             * Obtener la lista de persons.
             * Hace una petición GET con $http a /persons para obtener la lista
             * @returns {promise} promise para leer la respuesta del servidor}
             * Devuelve una lista de objetos de persons con sus atributos
             */
            this.fetchRecords = function () {
                return $http.get(context);  
            };

            /**
             * Obtener un registro de persons.
             * Hace una petición GET a /persons/:id para obtener
             * los datos de un registro específico de persons
             * @param {number} id del registro a obtener
             * @returns {promise} promise para leer la respuesta del servidor
             * Devuelve un objeto de persons con sus atributos 
             */
            this.fetchRecord = function (id) {
                return $http.get(context + "/" + id);
            };

            /**
             * Guardar un registro de persons.
             * Si currentRecord tiene la propiedad id, hace un PUT a /persons/:id con los
             * nuevos datos de la instancia de persons.
             * Si currentRecord no tiene la propiedad id, se hace un POST a /persons
             * para crear el nuevo registro de persons
             * @param {object} currentRecord instancia de persons a guardar/actualizar
             * @returns {promise} promise para leer la respuesta del servidor
             * Devuelve un objeto de persons con sus datos incluyendo el id
             */
            this.saveRecord = function (currentRecord) {
                if (currentRecord.id) {
                    return $http.put(context + "/" + currentRecord.id, currentRecord);
                } else {
                    return $http.post(context, currentRecord);
                }
            };

            /**
             * Hace una petición DELETE a /persons/:id para eliminar una person
             * @param {number} id identificador de la instancia de person a eliminar
             * @returns {promise} promise para leer la respuesta del servidor
             * No devuelve datos.
             */
            this.deleteRecord = function (id) {
                return $http.delete(context + "/" + id);
            };
        }]);
})(window.angular);