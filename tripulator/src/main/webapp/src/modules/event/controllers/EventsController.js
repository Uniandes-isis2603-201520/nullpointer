app.controller('EventsController', ['$scope', 'eventsInfoService', '$http', function ($scope, eventsInfoService, $http) {

        $scope.events = eventsInfoService.GetData();
        $scope.eventoActual = {"title": "Seleccione un evento"};
        $scope.commentTemplate = {
            "user": "Default",
            "userPhoto": "http://www.periodicoabc.mx/sites/default/files/anonimoface.png",
            "stars": 0,
            "comment": ""
        };
        $scope.starSelected = false;
        $scope.newComment = angular.copy($scope.commentTemplate);
        if ($scope.events != null) {
            $scope.eventoActual = $scope.events[0];
        }
        $scope.actualizarInfo = function (e) {
            $scope.eventoActual = e;
        }
        $scope.crearEstrellas = function (s) {
            var resp = "";
            var estrellasFaltantes = 5;
            var i = Number(s);
            for (; i > 0; i--) {
                resp += "&#9733";
                estrellasFaltantes--;
            }
            for (; estrellasFaltantes > 0; estrellasFaltantes--) {
                resp += "&#9734";
            }
            var txt = document.createElement("textarea");
            txt.innerHTML = resp;
            return txt.value;
        }
        $scope.comentarioActualStars = function (numb) {
            var i;
            numb = Number(numb);
            var txt = document.createElement("textarea");
            txt.innerHTML = "&#9733";
            var txt2 = document.createElement("textarea");
            txt2.innerHTML = "&#9734";
            for (i = 1; i < numb + 1; i++) {
                document.getElementById(i).innerHTML = txt.value;
            }
            for (; i < 6; i++) {
                document.getElementById(i).innerHTML = txt2.value;
            }
            $scope.starSelected = !$scope.starSelected;
            if ($scope.starSelected) {
                $scope.newComment.stars = numb;
            }else{
                $scope.newComment.stars=0;
            }
        }
        $scope.finComentarioActual = function () {
            $scope.eventoActual.comments.push($scope.newComment);
            $scope.reset();
        }
        $scope.reset = function () {
            $scope.newComment = angular.copy($scope.commentTemplate);
            $scope.starSelected=false;
            document.getElementById("comment").value="";
            $scope.resetPaint();
        }
        $scope.paintStar = function (numb) {
            if (!$scope.starSelected) {
                var i;
                numb = Number(numb);
                var txt = document.createElement("textarea");
                txt.innerHTML = "&#9733";
                var txt2 = document.createElement("textarea");
                txt2.innerHTML = "&#9734";
                for (i = 1; i < numb + 1; i++) {
                    document.getElementById(i).innerHTML = txt.value;
                }
                for (; i < 6; i++) {
                    document.getElementById(i).innerHTML = txt2.value;
                }
            }
        }
        $scope.resetPaint = function () {
            if (!$scope.starSelected) {
                var i = 1;
                var txt2 = document.createElement("textarea");
                txt2.innerHTML = "&#9734";
                for (; i < 6; i++) {
                    document.getElementById(i).innerHTML = txt2.value;
                }
            }
        }
    }]);


