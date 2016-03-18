(function (ng){
    var mod =ng.module("multimediaModule");
    
    mod.controller("multimediaCtrl", ["$scope","multimediaService", function ($scope, svc) {
    
        var self=this;

        /*
        $scope.currentRecord = {
            id:0,
            src:""
        };
            */
            
        
        $scope.slides = [];
        
        $scope.splitSlides=[];
        
        $scope.showAll=false;
        
        $scope.changeView=function()
        {
           $scope.showAll=!$scope.showAll;
        };
        
      
        this.splitPhotos = function () {
                       var cont=0;
                       var total=[];
                       var split=[];
                     
                       
                       for(var i=0;i<$scope.slides.length;i++)
                       {
                           var obj=$scope.slides[i];
                           split.push(obj);
                           if(cont===4)
                           {
                               total.push(split);
                               split=[];
                               cont=0;
                           }
                           else
                           {
                               cont++;
                           }
                           
                       }        
                       
                       $scope.splitSlides=total;    
        };

        
        
        
       
        function responseError(response) {
                self.showError(response.data);
        };
        

        $scope.currentIndex = 0;

        $scope.setCurrentSlideIndex = function (index) {
            
            $scope.currentIndex = index;
        };

        $scope.isCurrentSlideIndex = function (index) {
            return $scope.currentIndex === index;
        };

        $scope.prevSlide = function () {
            $scope.currentIndex = ($scope.currentIndex < $scope.slides.length - 1) ? ++$scope.currentIndex : 0;
        };

        $scope.nextSlide = function () {
            $scope.currentIndex = ($scope.currentIndex > 0) ? --$scope.currentIndex : $scope.slides.length - 1;
        };
        
        /*
        $scope.addSlide=function()
        {
            var url = prompt("Ingrese el URL de la imagen.", "");
            if (url !== null) {
                $scope.currentRecord={id:1, src: url};
                 svc.saveRecord(currentRecord).then(function(response){
                 self.fetchRecords();
                 },responseError);
            }

        };
        /*
          /*
             * Funcion createRecord emite un evento a los $scope hijos del controlador por medio de la 
             * sentencia &broadcast ("nombre del evento", record), esto con el fin cargar la información de modulos hijos 
             * al actual modulo.
             * Habilita el modo de edicion. El template de la lista cambia por el formulario.
             * 
             */

            this.createRecord = function () {
                $scope.$broadcast("pre-create", $scope.currentRecord);
                this.editMode = true;
                $scope.currentRecord = {};
                $scope.$broadcast("post-create", $scope.currentRecord);
            };


            /*
             * Funcion fetchRecords consulta el servicio svc.fetchRecords con el fin de consultar 
             * todos los registros del modulo book.
             * Guarda los registros en la variable $scope.records
             * Muestra el template de la lista de records.
             */

            this.fetchRecords = function () {
                return svc.fetchRecords().then(function (response) {
                    $scope.slides = response.data;
                    self.splitPhotos();
                    //$scope.currentRecord = {};
                    return response;
                }, responseError);
            };

            /*
             * Funcion saveRecord hace un llamado al servicio svc.saveRecord con el fin de
             * persistirlo en base de datos.
             * Muestra el template de la lista de records al finalizar la operación saveRecord
             */
            this.saveRecord = function () {
                    return svc.saveRecord($scope.currentRecord).then(function () {
                        self.fetchRecords();
                    }, responseError);                
            };

            /*
             * Funcion deleteRecord hace un llamado al servicio svc.deleteRecord con el fin
             * de eliminar el registro asociado.
             * Muestra el template de la lista de records al finalizar el borrado del registro.
             */
            this.deleteRecord = function (record) {
                return svc.deleteRecord(record.id).then(function () {
                    self.fetchRecords();
                }, responseError);
            };

            /*
             * Funcion fetchRecords consulta todos los registros del módulo book en base de datos
             * para desplegarlo en el template de la lista.
             */
            this.fetchRecords();


    }]);
})(window.angular);




