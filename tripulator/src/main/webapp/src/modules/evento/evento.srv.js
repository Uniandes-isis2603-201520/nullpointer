(function (ng) {
    var mod = ng.module("eventoModule");
    mod.service('EventosInfoService', [ "$http", "eventoContext",  function ($http,context) {

            this.fetchEventos = function () {
                var dir=context+"/eventos";
                console.log(dir);
                return $http.get(dir);

            };
            this.fetchEventosCiudadDia = function (ciudad,dia) {
                var dir=context+"/eventos?ciudad="+ciudad+"&fecha="+dia;
                console.log(dir);
                return $http.get(dir);
            };
            this.getComments = function (id) {
                var dir=context+"/eventos/"+id+"/comentarios";
                console.log(dir);
                return $http.get(dir);

            };
            this.saveRecord = function (newComment, id) {
                var dir=context+"/eventos/"+id+"/comentarios";
                console.log(dir);
                return $http.post(dir,newComment);
            };
        }]);
})(window.angular);

