(function (ng) {
    var mod = ng.module("viajeroModule");
    mod.controller('ViajeroController', ['$scope', '$uibModal', 'dayInfoService', function ($scope, dayInfoService) {

            $scope.setValue = function (value) {
                dayInfoService.init += value;
                alert(dayInfoService.init);
            };
            $scope.setInit = function (value) {
                dayInfoService.setValue1(value);
                alert(dayInfoService.getValue());
            };
            $scope.setEnd = function (value) {
                dayInfoService.setValue2(value);
                alert(dayInfoService.getValue2());
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
