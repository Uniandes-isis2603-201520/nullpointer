(function (ng) {
    var mod = ng.module("eventoModule");
    mod.service('EventosInfoService', ["$q", "$http", function ($q,$http) {
            
            this.fetchEventos = function () {
                return $http.get("http://localhost:8080/tripulator/api/eventos");
                
            };
            this.getComments = function (id) {
                return $http.get("http://localhost:8080/tripulator/api/eventos/"+id+"/comentarios");
                
            };
            this.saveRecord = function (newComment, id) {
                return $http.post("http://localhost:8080/tripulator/api/eventos/"+id+"/comentarios",newComment);
            };
        }]);
})(window.angular);

