(function (ng) {
    var mod = ng.module("eventoModule");
    mod.service('EventosInfoService', [ "$http", "eventoContext",  function ($http,context) {

            this.fetchEventos = function (dataSvc) {
                var sub = context+dataSvc.userId+"/itinerarios/"+dataSvc.tripId+"/dias/"+dataSvc.dayId;
                var dir=sub+"/eventos/all";
                console.log(dir);
                return $http.get(dir);

            };
            this.fetchEventosCiudadDia = function (dataSvc) {
                var sub = context+dataSvc.userId+"/itinerarios/"+dataSvc.tripId+"/dias/"+dataSvc.dayId;
                var dir=sub+"/eventos";
                console.log(dir);
                return $http.get(dir);
            };
            this.getComments = function (dataSvc,id) {
                var sub = context+dataSvc.userId+"/itinerarios/"+dataSvc.tripId+"/dias/"+dataSvc.dayId;
                var dir=sub+"/eventos/"+id+"/comentarios";
                console.log(dir);
                return $http.get(dir);

            };
            this.saveRecord = function (newComment, id,dataSvc) {
                var sub = context+dataSvc.userId+"/itinerarios/"+dataSvc.tripId+"/dias/"+dataSvc.dayId;
                var dir=sub+"/eventos/"+id+"/comentarios";
                console.log(dir);
                return $http.post(dir,newComment);
            };
            this.addEventoDia = function (idEvento,dataSvc){
                var sub = context+dataSvc.userId+"/itinerarios/"+dataSvc.tripId+"/dias/"+dataSvc.dayId;
                var dir=sub+"/eventos/"+idEvento;
                console.log(dir);
                return $http.post(dir);
            };
        }]);
})(window.angular);

