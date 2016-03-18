(function (ng){
    var mod = ng.module("viajeroModule",["ui.bootstrap"]);
    mod.constant("viajeroContext", "http://localhost:8080/tripulator/api/viajeros");
})(window.angular);