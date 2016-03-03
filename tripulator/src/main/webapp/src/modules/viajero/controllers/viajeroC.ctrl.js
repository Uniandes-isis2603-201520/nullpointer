(function (ng) {
    var mod = ng.module("viajeroModule");
    mod.controller('ViajeroC', ['$scope', '$uibModal', 'viajeroS', function ($scope, viajeroS, $rootScope) {

            $scope.setValue = function (value) {
                viajeroS.init += value;
                alert(viajeroS.init);
            };
            $scope.setInit = function (value) {
                viajeroS.setValue1(value);
                alert(viajeroS.getValue());
            };
            $scope.setEnd = function (value) {
                viajeroS.setValue2(value);
                alert(viajeroS.getValue2());
            };

            $scope.myDate = new Date();

            $scope.viajes = [
                /** {
                 image: 'http://digital.fespa.com/images/Amsterdam.jpg',
                 nombre: 'Amsterdam',
                 Fecha: '17/05/1996'
                 },
                 {
                 image: 'http://media-cdn.tripadvisor.com/media/photo-s/07/bd/a1/8a/downtown-dubai-cityscape.jpg',
                 nombre: 'Dubai',
                 fecha: '11/22/2005'
                 },
                 {
                 image: 'https://i.imgur.com/6EUAQg4.png',
                 nombre: 'PlaceHolder',
                 fecha: 'DatePlaceHolder'
                 },*/
            ];
            
            $scope.new =
                {/*
                 image: 'glyphicon glyphicon-plus-sign',
                 nombre: 'Agregar',
                 Fecha: ''*/
                 };

         /*   function createNew() {
                if (!inProgress)
                {

                }
                else
                {

                }
            }*/
            function validar()
            {
                if (false)
                {
                    alert("Ingrese fechas validas");
                    returnToPreviousPage();
                    return false;
                }
                return true;
            }
        }]);
})

        (window.angular);
