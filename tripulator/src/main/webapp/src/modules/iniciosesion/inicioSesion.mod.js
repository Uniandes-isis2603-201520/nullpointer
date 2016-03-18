(function (ng){
    var mod = ng.module("inicioSesionModule",["ui.bootstrap"]);
      mod.constant("inicioContext", "http://localhost:8080/tripulator/api/viajeros");
})(window.angular); 