(function (ng) {

    // es parte del módulo "personModule"
    var mod = ng.module("inicioSesionModule");

    // crea el controlador con dependencias a $scope y a personService
    mod.controller("inicioSesionCtrl", ["$scope","dataSvc", "InicioSesionService", "$state", "$stateParams", function ($scope,dataSvc ,svc, $state) {







            var encontro = false;
            $scope.buscar = -1;
            var self = this;
            $scope.currentRecord = {
                id: 0,
                nombre: '',
                apellido: '',
                usuario: '',
                email: '',
                password: ''
            };

            $scope.loginRecord = {
                email: '',
                password: ''
            };



            $scope.searchRecord = {};
            $scope.records = [];


            /*
             * Funcion createRecord emite un evento a los $scope hijos del controlador por medio de la
             * sentencia &broadcast ("nombre del evento", record), esto con el fin cargar la información de modulos hijos
             * al actual modulo.
             * Habilita el modo de edicion. El template de la lista cambia por el formulario.
             *
             */

            $scope.link = "/";
            $scope.iniciarSesion = function () {

                for (var i = 0; i < $scope.records.length && !encontro; i++)
                {
                    $scope.currentRecord = $scope.records[i];
                    if (($scope.currentRecord.email === $scope.loginRecord.email)
                            && ($scope.currentRecord.password === $scope.loginRecord.password))
                    {
                        dataSvc.userId = $scope.currentRecord.id ;
                        $state.go("viajero");
                        return;
                    }
                }

                encontro = false;
                alert("Usuario o contraseña incorrectos");
            };

            $scope.validar = function () {
                if (encontro)
                {
                    return "viajero";
                } else
                {
                    return "/";
                }
            };



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
