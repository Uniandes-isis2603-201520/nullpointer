(function (ng) {

  // es parte del módulo "personModule"
  var mod = ng.module("inicioSesionModule");

  // crea el controlador con dependencias a $scope y a personService
  mod.controller("inicioSesionCtrl", ["$scope", "InicioSesionService", function ($scope, svc) {


            $scope.buscar = -1;
            var self = this;
            $scope.currentRecord = {
                    id: 0,
                    nombre:'',
                    apellido:'',
                    usuario: '', 
                    email: '',
                    password:''
                };
            $scope.searchRecord = {};
            $scope.records = [];

            //Variables para el controlador
            this.readOnly = false;
            this.editMode = false;
            /*
             * Funcion createRecord emite un evento a los $scope hijos del controlador por medio de la
             * sentencia &broadcast ("nombre del evento", record), esto con el fin cargar la información de modulos hijos
             * al actual modulo.
             * Habilita el modo de edicion. El template de la lista cambia por el formulario.
             *
             */

            
              this.saveRecord = function () {
                  console.log("llegues");
                    return svc.saveRecord($scope.currentRecord).then(function () {
                        self.fetchRecords();
                    });
            };




            /*
             * Funcion fetchRecords consulta el servicio svc.fetchRecords con el fin de consultar
             * todos los registros del modulo book.
             * Guarda los registros en la variable $scope.records
             * Muestra el template de la lista de records.
             */

            this.fetchRecords = function () {
                return svc.fetchRecords().then(function (response) {
                    $scope.records = response.data;
                    $scope.currentRecord = {};
                    return response;
                });
            };
            this.fetchRecord = function () {
                return svc.fetchRecord($scope.buscar).then(function (response) {
                    $scope.searchRecord = response.data;
                    return response;
                });
            };
             this.deleteRecord = function (username) {
                return svc.deleteRecord(username).then(function () {
                    self.fetchRecords();
                });
            };


            this.fetchRecords();

        }]);

})(window.angular);